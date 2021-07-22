
package dev.example.employeeCourse.boot.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import dev.example.employeeCourse.boot.model.Holidays;
import dev.example.employeeCourse.boot.repository.CertificateRepository;
import dev.example.employeeCourse.boot.repository.CourseRepository;
import dev.example.employeeCourse.boot.repository.EmployeeImageRepository;
import dev.example.employeeCourse.boot.repository.EmployeeRepository;
import dev.example.employeeCourse.boot.repository.EnrollmentRepository;
import dev.example.employeeCourse.boot.repository.ExpenseRepository;
import dev.example.employeeCourse.boot.repository.HolidaysRepository;

@Controller

@RequestMapping("/employee/items/holidays/")
public class EmployeeItemsHolidaysController {

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
	
	@Autowired
	HolidaysRepository holidaysRepository;

	
	
	
	//-----------------------------------------------------------------------------
	// ----------------------- add Holidays  ----------------------------------
	// ------------------------------------------------------------- ----------
	@RequestMapping("/addHolidays")
	public String addHolidaysEmployee(int id, Model model) {

		Optional<Holidays> holidaysFound = findOneHolidaysById(id);

		if (holidaysFound.isPresent()) {

			model.addAttribute("employeefromController", holidaysFound.get().getEmployee());
			model.addAttribute("holidays", holidaysFound.get());
			       
			return "employeeitems/addholidaysemployee";
		}

		else
			return "home/notfound.html";
	}
	 
	@PostMapping("/insertOneHolidaysDate")
	public String insertOneHolidaysDate(Model boxToView, @RequestParam String date, @RequestParam int id,
			RedirectAttributes redirectAttributes) throws ParseException {
		
		//System.out.println(id + "  " + date);
		Optional<Holidays> holidaysFound = findOneHolidaysById(id);

		if (holidaysFound.isPresent()) {

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date newdate = format.parse(date);
			
			//System.out.println(newdate);
			holidaysFound.get().addHolidays(newdate);

			
			holidaysRepository.save(holidaysFound.get());

			redirectAttributes.addFlashAttribute("message",
					"You successfully create an hoolidays date! The date added is " + newdate);

			return "redirect:/employee/items/holidays/addHolidays?id=" + id;

		} else
			return "home/notfound.html";

	}
	  
	 
	
	
	// ------------------------------------------------------------------------------------
	// ----------------------- Holidays Update dates --------------------------------------------
	// ------------------------------------------------------------------------------------
	@RequestMapping("/deleteHolidays")
	public String deleteHolidaysEmployee(int id, Model model) {

		Optional<Holidays> holidaysFound = findOneHolidaysById(id);

		if (holidaysFound.isPresent()) {

			model.addAttribute("employeefromController", holidaysFound.get().getEmployee());
			model.addAttribute("holidays", holidaysFound.get());
		
			return "employeeitems/deleteholidaysemployee";
		}

		else
			return "home/notfound.html";
	}
	

	@RequestMapping(value = "/removeOneHolidaysDate", method = RequestMethod.POST)
	public String removeOneHolidaysDate(@Validated Expense expense, Model boxToView, @RequestParam("employeeId") int id,
			RedirectAttributes redirectAttributes) {

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
	
	public Optional<Holidays> findOneHolidaysById(int id) {

		Optional<Holidays> holidaysFound = holidaysRepository.findById(id);

		return holidaysFound;
	}
	

}
