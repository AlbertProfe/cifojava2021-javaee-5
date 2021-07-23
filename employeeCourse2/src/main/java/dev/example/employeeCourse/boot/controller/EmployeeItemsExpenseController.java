
package dev.example.employeeCourse.boot.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.example.employeeCourse.boot.model.Course;
import dev.example.employeeCourse.boot.model.Employee;
import dev.example.employeeCourse.boot.model.Enrollment;
import dev.example.employeeCourse.boot.model.Expense;
import dev.example.employeeCourse.boot.repository.CertificateRepository;
import dev.example.employeeCourse.boot.repository.CourseRepository;
import dev.example.employeeCourse.boot.repository.EmployeeImageRepository;
import dev.example.employeeCourse.boot.repository.EmployeeRepository;
import dev.example.employeeCourse.boot.repository.EnrollmentRepository;
import dev.example.employeeCourse.boot.repository.ExpenseRepository;

@Controller

@RequestMapping("/employee/items/expense/")
public class EmployeeItemsExpenseController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	CertificateRepository certificateRepository;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Autowired
	EmployeeImageRepository employeeImageRepository;

	@Autowired
	ExpenseRepository expenseRepository;

	// ------------------------------------------------------------------------------------
	// ----------------------- add expense with Employee----------------------------------
	// ------------------------------------------------------------------------------------
	@RequestMapping("/addExpense")
	public String addExpenseEmployee(int id, Model model) {

		Optional<Employee> employeeFound = findOneEmployeeById(id);

		if (employeeFound.isPresent()) {

			model.addAttribute("employeefromController", employeeFound.get());

			Optional<Iterable<Expense>> expenseFound = expenseRepository.findAllExpenseByEmployee(employeeFound.get());

			model.addAttribute("expenses", expenseFound.get());

			return "employeeitems/addexpenseemployee";
		}

		else
			return "home/notfound.html";
	}

	@RequestMapping(value = "/insertExpense", method = RequestMethod.POST)
	public String insertExpense(@Validated Expense expense, @RequestParam("employeeId") int id,
			RedirectAttributes redirectAttributes) {
		
		
		System.out.println(id + " " + expense);
		Optional<Employee> foundEmployee = employeeRepository.findById(id);

		if (foundEmployee.isPresent()) {

			expense.setEmployee(foundEmployee.get());

			// System.out.println(expense);
			Expense expenseSaved = expenseRepository.save(expense);

			redirectAttributes.addFlashAttribute("message",
					"You successfully create an expense, id: " + expenseSaved.getId() + " - " + expenseSaved.getName()
							+ " - " + expenseSaved.getValue() + " for " + foundEmployee.get().getName() + " "
							+ foundEmployee.get().getSurname() + "!");

			return "redirect:/employee/items/expense/addExpense?id=" + id;

		} else
			return "home/notfound.html";

	}

	// ------------------------------------------------------------------
	// ----------------------- delete expense with Employee -------------
	// ------------------------------------------------------------------
	@RequestMapping("/deleteExpense")
	public String removeExpense(int id, Model boxToView, RedirectAttributes redirectAttributes) {

		Optional<Expense> expenseFound = findOneExpenseById(id);

		if (expenseFound.isPresent()) {

			expenseRepository.deleteById(expenseFound.get().getId());

			redirectAttributes.addFlashAttribute("message",
					"You successfully deleted an expense, id: " + expenseFound.get().getId() + " - "
							+ expenseFound.get().getName() + " - " + expenseFound.get().getValue() + " for "
							+ expenseFound.get().getEmployee().getName() + " "
							+ expenseFound.get().getEmployee().getSurname() + "!");

			return "redirect:/employee/items/detailEmployee?id=" + expenseFound.get().getEmployee().getId();

		} else
			return "home/notfound.html";
	}

	// ---------------------------------------------------------------------------------------
	// ----------------------- update expense with Employee
	// ----------------------------------
	// ---------------------------------------------------------------------------------------
	@RequestMapping("/updateExpense")
	public String updateExpenseEmployee(int id, Model model) {

		// System.out.println(id);

		Optional<Expense> expenseFound = findOneExpenseById(id);

		if (expenseFound.isPresent()) {

			model.addAttribute("employeefromController", expenseFound.get().getEmployee());
			model.addAttribute("expense", expenseFound.get());

			return "employeeitems/updateexpenseemployee";
		}

		else
			return "home/notfound.html";
	}

	@PostMapping("/insertExpense/{idExpense}")
	public String replaceCourseEmployee(@PathVariable("idExpense") int id, Expense expense,
			RedirectAttributes redirectAttributes, Model model) {

		//System.out.println(id+ "- "+ expense);

		Optional<Expense> expenseFound = findOneExpenseById(id);

		if (expenseFound.isPresent()) {
			
			model.addAttribute("employeefromController", expenseFound.get().getEmployee());
			
			if (!expenseFound.get().getName().equals(expense.getName()))
				expenseFound.get().setName(expense.getName());
			
			if (expenseFound.get().getValue() != expense.getValue())
				expenseFound.get().setValue(expense.getValue());
			
			if (expense.getDate() != null )
				expenseFound.get().setDate(expense.getDate());
			
		
			expenseRepository.save(expenseFound.get());

			redirectAttributes.addFlashAttribute("message", "You successfully updated that expense! "
					+ expenseFound.get().getName() +" - " +  expenseFound.get().getDate() +" - " +  expenseFound.get().getValue());

			return "redirect:/employee/items/expense/updateExpense?id=" + id;

		} else
			return "home/notfound.html";

	}

	// -------------------------------------------------------------------------------
	// ------------------------- service to controller--------------------------------
	// -------------------------------------------------------------------------------

	public Optional<Employee> findOneEmployeeById(int id) {

		Optional<Employee> employeeFound = employeeRepository.findById(id);

		return employeeFound;
	}

	public Optional<Course> findOneCourseById(int id) {

		Optional<Course> courseFound = courseRepository.findById(id);

		return courseFound;
	}

	public Optional<Enrollment> findOneEnrollmentById(int id) {

		Optional<Enrollment> enrollmentFound = enrollmentRepository.findById(id);

		return enrollmentFound;
	}

	public Optional<Expense> findOneExpenseById(int id) {

		Optional<Expense> expenseFound = expenseRepository.findById(id);

		return expenseFound;
	}

}
