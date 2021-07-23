package dev.example.employeeCourse.boot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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
	HolidaysRepository holidaysRepository;

	// ------------------------------home ---------------------------
	@RequestMapping({ "/home", "/" })
	public String home() {
		return "home/home";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	// -------------------- fill in our DB ---------------------------------------
	@RequestMapping({ "/fillin" })
	public String finInDB() {

		return "employee/fillinemployee.html";
	}

	@RequestMapping({ "/fillinemployee" })
	public String fillInDBEmployee(int qtyToCreate) {

		String alphabetChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!·$%&/()=?¿?=)()/*-+^*Ç¨_:;;:_+/+/";

		// int i = 0;
		char stringRandom1, stringRandom2, stringRandom3;
		/*
		 * while (i < 10) { char charRandom =
		 * alphabetChars.charAt(createIntRandom(alphabetChars.length())); stringRandom *
		 * += stringRandom + charRandom;
		 * 
		 * }
		 */

		Faker faker = new Faker();
		// Random rand = new Random();
		int max = 1525;
		int count = 1;
		int intRandom;
		int intRandom2;

		int year = 2019;
		int employeecount = 1;
		int coursecount = 1;

		// we create so many entities as qtyToCreate says ....
		while (count <= qtyToCreate) {

			stringRandom1 = alphabetChars.charAt(createIntRandom(alphabetChars.length()));
			stringRandom2 = alphabetChars.charAt(createIntRandom(alphabetChars.length()));
			stringRandom3 = alphabetChars.charAt(createIntRandom(alphabetChars.length()));
			intRandom = createIntRandom(max);
			intRandom2 = createIntRandom(max * 10);

			// first at all we create qtyToCreate EMPLOYEES
			employeeRepository
					.save(new Employee(faker.name().firstName(), faker.name().lastName(),
							faker.number().numberBetween(16, 65), faker.name().firstName() + "@java.com",
							faker.number().randomDouble(2, 5, 2000),
							String.valueOf((intRandom + 5) * (count + 1) * 6) + stringRandom1 + stringRandom2
									+ stringRandom3,
							faker.job().title(), faker.job().position(), faker.phoneNumber().cellPhone(),
							faker.address().fullAddress()));

			// second we create qtyToCreate CERTIFICATES
			certificateRepository
					.save(new Certificate(faker.programmingLanguage().name(), faker.programmingLanguage().creator(),
							faker.number().numberBetween(100, 800), faker.number().numberBetween(1, 8), true));

			// third we create qtyToCreate COURSES
			courseRepository.save(new Course("room_" + faker.number().numberBetween(45, 108) + "B", false,
					faker.date().birthday(0, 3), faker.date().birthday(0, 3), "mornings",
					faker.number().randomDouble(2, 1550, 20000),
					faker.name().firstName() + " " + faker.name().lastName()));

			certificateRepository.findById(count).get().adCourse(courseRepository.findById(count).get());

			count++;
		}

		// fourth we create qtyToCreate EXPENSES *10 and assign them to each EMPLOYEE
		int countExpense = 1;
		int countExpenseid = 1;
		employeecount = (int) (employeeRepository.count() - qtyToCreate + 1);
		count = 1;
		while (count <= qtyToCreate) {
			countExpense = 1;
			while (countExpense <= 10) {
				expenseRepository.save(new Expense(faker.beer().name(), faker.date().birthday(0, 3),
						faker.number().randomDouble(2, 50, 2000)));
				expenseRepository.findById(countExpenseid).get()
						.setEmployee(employeeRepository.findById(employeecount).get());
				countExpense++;
				countExpenseid++;
				employeecount++;
			}
			count++;
		}

		// fifth we create qtyToCreate HOLIDAYS * 3 (year 2019, 2020 and 2021) and
		// assign them to EMPLOYEE
		// and within the year we create between 24 and 54 dates for each year
		year = 2019;
		int countHolidays = 1;
		int countDatesToAdd = 1;
		int countHolidaysid = 1;
		employeecount = (int) (employeeRepository.count() - qtyToCreate + 1);
		while (countHolidays <= 3) {
			holidaysRepository.save(new Holidays(year, faker.number().numberBetween(28, 34)));
			countDatesToAdd = 1;
			while (countDatesToAdd < faker.number().numberBetween(24, 54)) {
				holidaysRepository.findById(countHolidaysid).get().addHolidays(new GregorianCalendar(year,
						faker.number().numberBetween(01, 12), faker.number().numberBetween(01, 31)).getTime());
				countDatesToAdd++;
			}

			holidaysRepository.findById(countHolidaysid).get()
					.setEmployee(employeeRepository.findById(employeecount).get());
			countHolidays++;
			countHolidaysid++;
			year++;
			employeecount++;
		}

		// sixth we create qtyToCreate ENROLLMENTS * 3 (three courses per employee) and
		// assign them to EMPLOYEE
		count = 1;
		employeecount = (int) (employeeRepository.count() - qtyToCreate + 1);
		coursecount = 1;
		while (count <= qtyToCreate) {
			enrollmentRepository.save(new Enrollment(faker.date().birthday(0, 3), faker.number().numberBetween(7, 10),
					true, "FINISHED", employeeRepository.findById(employeecount).get(),
					courseRepository.findById(coursecount).get()));

			if (count == (qtyToCreate - 1))
				coursecount = 1;
			enrollmentRepository.save(new Enrollment(faker.date().birthday(0, 3), faker.number().numberBetween(7, 10),
					true, "IN-PROGRESS", employeeRepository.findById(employeecount).get(),
					courseRepository.findById(coursecount + 1).isPresent()
							? courseRepository.findById(coursecount + 1).get()
							: null));

			enrollmentRepository.save(new Enrollment(faker.date().birthday(0, 3), faker.number().numberBetween(7, 10),
					true, "TO-START", employeeRepository.findById(employeecount).get(),
					courseRepository.findById(coursecount + 2).isPresent()
							? courseRepository.findById(coursecount + 2).get()
							: null));
			count++;
			coursecount++;
			employeecount++;

		}

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
