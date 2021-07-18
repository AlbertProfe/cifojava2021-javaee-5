package dev.example.employeeCourse.boot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import dev.example.employeeCourse.boot.repository.CertificateRepository;
import dev.example.employeeCourse.boot.repository.CourseRepository;
import dev.example.employeeCourse.boot.repository.EmployeeRepository;
import dev.example.employeeCourse.boot.repository.EnrollmentRepository;
import dev.example.employeeCourse.boot.repository.ExpenseRepository;

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
		int countExpenseid = 1;

		while (count <= qtyToCreate) {

			stringRandom1 = alphabetChars.charAt(createIntRandom(alphabetChars.length()));
			stringRandom2 = alphabetChars.charAt(createIntRandom(alphabetChars.length()));
			stringRandom3 = alphabetChars.charAt(createIntRandom(alphabetChars.length()));
			intRandom = createIntRandom(max);
			intRandom2 = createIntRandom(max * 10);

			/*
			 * boolean randomPublished; if ((intRandom % 2) == 0) { randomPublished = true;
			 * } else { randomPublished = false; }
			 */

			employeeRepository.save(new Employee(faker.name().firstName(), faker.name().lastName(),
					faker.number().numberBetween(16, 65), faker.name().firstName() + "@java.com",
					faker.number().randomDouble(2, 5, 2000),
					String.valueOf((intRandom + 5) * (count + 1) * 6) + stringRandom1 + stringRandom2 + stringRandom3,
					faker.job().title(), faker.job().position(), faker.phoneNumber().cellPhone() ,faker.address().fullAddress()
					));

			certificateRepository
					.save(new Certificate(faker.programmingLanguage().name(), faker.programmingLanguage().creator(),
							faker.number().numberBetween(100, 800), faker.number().numberBetween(1, 8), true));

			courseRepository.save(new Course("room_" + faker.number().numberBetween(45, 108) + "B", false,
					faker.date().birthday(0, 3), faker.date().birthday(0, 3), "mornings",
					faker.number().randomDouble(2, 1550, 20000),
					faker.name().firstName() + " " + faker.name().lastName()));

			

			int countExpense = 0;
			while (countExpense < 10) {
				expenseRepository.save(new Expense(faker.beer().name(), faker.date().birthday(0, 3),
						faker.number().randomDouble(2, 50, 2000)));
				expenseRepository.findById(countExpenseid).get().setEmployee(employeeRepository.findById(count).get());
				countExpense++;
				countExpenseid++;
			}

			certificateRepository.findById(count).get().adCourse(courseRepository.findById(count).get());
			
			count++;

		}

		count = 1;
		while (count <= qtyToCreate) {
			enrollmentRepository.save(
					new Enrollment(faker.date().birthday(0, 3), faker.number().numberBetween(7, 10), true, "FINISHED",
							employeeRepository.findById(count).get(), courseRepository.findById(count).get()));

			enrollmentRepository.save(new Enrollment(faker.date().birthday(0, 3), faker.number().numberBetween(7, 10),
					true, "IN-PROGRESS", employeeRepository.findById(count).get(),
					courseRepository.findById(count + 1).isPresent() ? courseRepository.findById(count + 1).get()
							: null));
			enrollmentRepository.save(new Enrollment(faker.date().birthday(0, 3), faker.number().numberBetween(7, 10),
					true, "TO-START", employeeRepository.findById(count).get(),
					courseRepository.findById(count + 2).isPresent() ? courseRepository.findById(count + 2).get()
							: null));
			count++;

		}
		//we need to delete the last enrollment because the assign course (id) doesn't exist and it will be null
		//enrollmentRepository.deleteById((qtyToCreate-1)*3);
		//we need to delete the last course because the assign certificate (id) doesn't exist and it will be null
		//courseRepository.deleteById(qtyToCreate);

		return "redirect:/employee/allEmployees";
	}

	// -------------------------- error path website ----------------------------
	@RequestMapping({ "*", "*/*", "*/*/*" })
	public String notFound(Model model) {

		String pattern = "yyyy-MM-dd HH:mm:ssZ";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		model.addAttribute("serverTime", simpleDateFormat.format(new Date()));
		model.addAttribute("kingsley_variable", 15445);
		model.addAttribute("borja_test", "lo conseguire, any doubt?");
		model.addAttribute("smoker", true);

		return "home/notFound";
	}

	// -------------------------------------------------------------------------
	// ------------------ service to homeController -----------------------------
	// ---------------------------------------------------------------------------
	public static int createIntRandom(int top) {

		Random rand = new Random();

		// Generate random integers in range 0 to top, if top=10 random 0 to 9
		int intRandom = rand.nextInt(top);
		// System.out.println(intRandom);
		return intRandom;

	}

}
