package dev.example.employeeCourse.boot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javafaker.Faker;

import dev.example.employeeCourse.boot.model.Certificate;
import dev.example.employeeCourse.boot.model.Course;
import dev.example.employeeCourse.boot.model.Employee;
import dev.example.employeeCourse.boot.model.Enrollment;
import dev.example.employeeCourse.boot.model.Expense;
import dev.example.employeeCourse.boot.model.Holidays;
import dev.example.employeeCourse.boot.repository.CertificateRepository;
import dev.example.employeeCourse.boot.repository.CourseRepository;
import dev.example.employeeCourse.boot.repository.EmployeeRepository;
import dev.example.employeeCourse.boot.repository.EnrollmentRepository;
import dev.example.employeeCourse.boot.repository.ExpenseRepository;
import dev.example.employeeCourse.boot.repository.HolidaysRepository;

@Controller
public class HomeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private CertificateRepository certificateRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	@Autowired
	private HolidaysRepository holidaysRepository;

	// ------------------------------home ---------------------------
	@RequestMapping({ "/home", "/"})
	public String home(Model model) {
		
		model.addAttribute("employees", employeeRepository.count() );
		model.addAttribute("expenses", expenseRepository.count() );
		model.addAttribute("certificates", certificateRepository.count() );
		model.addAttribute("courses", courseRepository.count() );
		model.addAttribute("enrollments", enrollmentRepository.count() );
		model.addAttribute("holidays", holidaysRepository.count() );
		
		
		return "home/home";
	}

	@RequestMapping("/login")
	public String login() {
		return "home/login";
	}
	
	@RequestMapping("/deleteAll")
	public String deleteAll() {

		
		expenseRepository.deleteAll();
		holidaysRepository.deleteAll();
		certificateRepository.deleteAll();
		courseRepository.deleteAll();
		enrollmentRepository.deleteAll();
		
		employeeRepository.deleteAll();

		return "redirect:/home";

	}

	// -------------------- fill in our DB ---------------------------------------
	@RequestMapping({ "/fillin" })
	public String finInDB() {

		return "employee/fillinemployee.html";
	}

	@RequestMapping({ "/fillinemployee" })
	public String fillInDBEmployee(int qtyToCreate) {

		String alphabetChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!·$%&/()=?¿?=)()/*-+^*Ç¨_:;;:_+/+/";
		char stringRandom1, stringRandom2, stringRandom3;

		Faker faker = new Faker();

		int max = 1525;
		int count = 1;
		int intRandom;

		int year = 2019;
		int employeecount = 1;

		// we create so many entities as qtyToCreate says ....
		while (count <= qtyToCreate) {

			stringRandom1 = alphabetChars.charAt(createIntRandom(alphabetChars.length()));
			stringRandom2 = alphabetChars.charAt(createIntRandom(alphabetChars.length()));
			stringRandom3 = alphabetChars.charAt(createIntRandom(alphabetChars.length()));
			intRandom = createIntRandom(max);

			// first at all we create qtyToCreate EMPLOYEES
			Employee employee = new Employee(faker.name().firstName(), faker.name().lastName(),
					faker.number().numberBetween(16, 65), faker.name().firstName() + "@java.com",
					faker.number().randomDouble(2, 5, 2000),
					String.valueOf((intRandom + 5) * (count + 1) * 6) + stringRandom1 + stringRandom2 + stringRandom3,
					faker.job().title(), faker.job().position(), faker.phoneNumber().cellPhone(),
					faker.address().fullAddress());
			employeeRepository.save(employee);

			// second we create qtyToCreate CERTIFICATES
			Certificate certificate = new Certificate(faker.programmingLanguage().name(),
					faker.programmingLanguage().creator(), faker.number().numberBetween(100, 800),
					faker.number().numberBetween(1, 8), true);
			certificateRepository.save(certificate);

			// third we create qtyToCreate COURSES and add CERTIFICATES
			Course course = new Course("room_" + faker.number().numberBetween(45, 108) + "B", false,
					faker.date().birthday(0, 3), faker.date().birthday(0, 3), "mornings",
					faker.number().randomDouble(2, 1550, 20000),
					faker.name().firstName() + " " + faker.name().lastName(), certificate);
			courseRepository.save(course);

			// fourth we create qtyToCreate EXPENSES *10 and assign them to each EMPLOYEE
			int countExpense = 1;
			while (countExpense <= 10) {

				Expense expense = new Expense(faker.beer().name(), faker.date().birthday(0, 3),
						faker.number().randomDouble(2, 50, 2000), employee);
				expenseRepository.save(expense);
				countExpense++;

			}

			// fifth we create qtyToCreate HOLIDAYS * 3 (year 2019, 2020 and 2021)
			// and assign them to EMPLOYEE, after that
			// and within that year we create between 24 and 54 dates for each year
			int countYear = 1;
			while (countYear <= 3) {
				Holidays holidays = new Holidays(year, faker.number().numberBetween(28, 34), employee);

				int countDates = 1;
				while (countDates < faker.number().numberBetween(24, 54)) {

					holidays.addHolidays(new GregorianCalendar(year, faker.number().numberBetween(01, 12),
							faker.number().numberBetween(01, 31)).getTime());

					countDates++;
				}

				holidaysRepository.save(holidays);
				countYear++;

			}

			count++;
		}

		// sixth we create qtyToCreate ENROLLMENTS * 3 (three courses per employee) and
		// assign them to EMPLOYEE
		int countEnrollment = 1;
		int employeeLastId = employeeRepository.findTopByOrderByIdDesc().getId();
		int firstIdThisSerie = employeeLastId - qtyToCreate  +1;
		//System.out.println(firstIdThisSerie +" " +employeeLastId);
		int countEmployee = firstIdThisSerie;
		while (countEnrollment <= qtyToCreate) {
			
			Optional<Employee> employeeToEnroll = employeeRepository.findById(countEmployee);

			Enrollment enrollment1 = new Enrollment(faker.date().birthday(0, 3), faker.number().numberBetween(7, 10),
					true, "FINISHED", employeeToEnroll.get(), courseRepository.findById(faker.number().numberBetween(firstIdThisSerie, employeeLastId)).get());

			enrollmentRepository.save(enrollment1);

			Enrollment enrollment2 = new Enrollment(faker.date().birthday(0, 3), faker.number().numberBetween(7, 10),
					true, "FINISHED", employeeToEnroll.get(), courseRepository.findById(faker.number().numberBetween(firstIdThisSerie, employeeLastId)).get());

			enrollmentRepository.save(enrollment2);

			Enrollment enrollment3 = new Enrollment(faker.date().birthday(0, 3), faker.number().numberBetween(7, 10),
					true, "FINISHED", employeeToEnroll.get(), courseRepository.findById(faker.number().numberBetween(firstIdThisSerie, employeeLastId)).get());

			enrollmentRepository.save(enrollment3);
			
			countEmployee++;
			countEnrollment++;

		}

		// List<Certificate> certificates = (List<Certificate>)
		// certificateRepository.findAll(); Certificate certicateLast =
		// certificates.get(certificates.size()-1); int certificateCount =
		// certicateLast.getId();
		// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.limit-query-result
		
		

		return "redirect:/employee/allEmployees";
	}

	// -------------------------- error path website ----------------------------
	@RequestMapping({ "*", "*/*", "*/*/*" })
	public String notFound(Model model) {

		String pattern = "yyyy-MM-dd HH:mm:ssZ";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		model.addAttribute("serverTime", simpleDateFormat.format(new Date()));

		return "home/notFound";
	}

	// ------------------ service to homeController -----------------------------
	public static int createIntRandom(int top) {

		Random rand = new Random();

		// Generate random integers in range 0 to top, if top=10 random 0 to 9
		int intRandom = rand.nextInt(top);
		// System.out.println(intRandom);
		return intRandom;

	}

}