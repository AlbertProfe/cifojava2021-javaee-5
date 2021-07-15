package dev.example.employeeCourse.boot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javafaker.Faker;

import dev.example.employeeCourse.boot.model.Employee;
import dev.example.employeeCourse.boot.model.Expense;
import dev.example.employeeCourse.boot.repository.EmployeeRepository;
import dev.example.employeeCourse.boot.repository.ExpenseRepository;

@Controller
public class HomeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ExpenseRepository expenseRepository;

	// ------------------------------home ---------------------------
	@RequestMapping({ "/home", "/" })
	public String home() {
		return "home/home";
	}
	
	/*
	 * https://github.com/mafor/swagger-ui-port
	 * @RequestMapping("/swagger") public String swaggerRedirect() { return
	 * "redirect:/swagger-ui.html"; }
	 */

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
		int count = 0;
		int intRandom;
		int intRandom2;
		while (count < qtyToCreate) {

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
					String.valueOf((intRandom + 5) * (count + 1) * 6) + stringRandom1 + stringRandom2 + stringRandom3));

			expenseRepository.save(new Expense(faker.beer().name(), faker.date().birthday(0, 3),
					faker.number().randomDouble(2, 50, 2000)));

			count++;
		}

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
