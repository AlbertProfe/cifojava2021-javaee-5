
package dev.example.employeeCourse.boot.controller;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import dev.example.employeeCourse.boot.model.Course;
import dev.example.employeeCourse.boot.model.Employee;
import dev.example.employeeCourse.boot.model.Enrollment;
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


	// ----------------------- expense  ----------------------------------

	@RequestMapping("/addExpense")
	public String addExpenseEmployee(int id, Model model) {

		Optional<Employee> employeeFound = findOneEmployeeById(id);

		if (employeeFound.isPresent()) {

			model.addAttribute("employeefromController", employeeFound.get());
			//model.addAttribute("expensesfromController", expenseRepository.findAll());
			

			return "employeeitems/addexpenseemployee";
		}

		else
			return "home/notfound.html";
	}

	/*
	 * @RequestMapping(value = "/insertCourse", method = RequestMethod.POST) public
	 * String insertCourseEmployee(int id, Employee
	 * employee, @RequestParam("idCourse") int idCourse, RedirectAttributes
	 * redirectAttributes) {
	 * 
	 * System.out.println(" (1) id course: " + idCourse + " id employee: " + id);
	 * 
	 * Optional<Employee> employeeFound = findOneEmployeeById(id); Optional<Course>
	 * courseFound = findOneCourseById(idCourse);
	 * 
	 * if (employeeFound.isPresent() && courseFound.isPresent()) {
	 * System.out.println(" (2) id course: " + idCourse + " id employee: " + id);
	 * Enrollment enrollment = new Enrollment();
	 * 
	 * enrollment.setCourse(courseFound.get());
	 * enrollment.setEmployee(employeeFound.get());
	 * 
	 * enrollmentRepository.save(enrollment);
	 * 
	 * redirectAttributes.addFlashAttribute("message", "You successfully enrolled "
	 * + employeeFound.get().getName() + " " + employeeFound.get().getSurname() +
	 * " in " + courseFound.get().getCertificate().getName() + "!");
	 * 
	 * return "redirect:/employee/addCourse?id=" + id; }
	 * 
	 * else return "home/notfound.html"; }
	 */

	/*
	 * @RequestMapping("/updateEmployeeCourse") public String
	 * updateCourseEmployee(int id, Model model) {
	 * 
	 * Optional<Enrollment> enrollmentFound = findOneEnrollmentById(id);
	 * 
	 * if (enrollmentFound.isPresent()) {
	 * 
	 * model.addAttribute("employeefromController",
	 * enrollmentFound.get().getEmployee()); model.addAttribute("enrollment",
	 * enrollmentFound.get());
	 * 
	 * return "employee/updatecourseemployee"; }
	 * 
	 * else return "home/notfound.html";
	 * 
	 * }
	 */

	/*
	 * @PostMapping("/replaceEmployeeCourse/{idEnrollment}") public String
	 * replaceCourseEmployee(@PathVariable("idEnrollment") int id, Enrollment
	 * enrollment, RedirectAttributes redirectAttributes) {
	 * 
	 * System.out.println(id);
	 * 
	 * Optional<Enrollment> enrollmentFound = findOneEnrollmentById(id);
	 * 
	 * if (enrollmentFound.isPresent()) {
	 * 
	 * enrollmentFound.get().setStatus(enrollment.getStatus());
	 * enrollmentFound.get().setApproved(enrollment.isApproved());
	 * 
	 * enrollmentRepository.save(enrollmentFound.get());
	 * 
	 * redirectAttributes.addFlashAttribute("message",
	 * "You successfully updated that enrollment !");
	 * 
	 * return "redirect:/employee/updateEmployeeCourse?id=" + id;
	 * 
	 * } else return "home/notfound.html";
	 * 
	 * }
	 */

	// -------------------------------------------------------------------------------
	// ------------------------- service to controller
	// --------------------------------
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

}
