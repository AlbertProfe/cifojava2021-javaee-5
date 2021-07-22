package dev.example.employeeCourse.boot.controller;

import java.io.IOException;
import java.util.Optional;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dev.example.employeeCourse.boot.model.Course;
import dev.example.employeeCourse.boot.model.Employee;
import dev.example.employeeCourse.boot.model.EmployeeImage;
import dev.example.employeeCourse.boot.model.Enrollment;
import dev.example.employeeCourse.boot.repository.CertificateRepository;
import dev.example.employeeCourse.boot.repository.CourseRepository;
import dev.example.employeeCourse.boot.repository.EmployeeImageRepository;
import dev.example.employeeCourse.boot.repository.EmployeeRepository;
import dev.example.employeeCourse.boot.repository.EnrollmentRepository;
import dev.example.employeeCourse.boot.repository.ExpenseRepository;

@Controller
@RequestMapping("/employee/items/course/")
public class EmployeeItemsCourseController {

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

	
	// ----------------------- enrollment ----------------------------------
	@RequestMapping("/addCourse")
	public String addCourseEmployee(int id, Model model) {

		Optional<Employee> employeeFound = findOneEmployeeById(id);

		if (employeeFound.isPresent()) {

			model.addAttribute("employeefromController", employeeFound.get());
			model.addAttribute("coursesfromController", courseRepository.findAll());
			model.addAttribute("certificatesfromController", certificateRepository.findAll());

			return "employeeitems/addcourseemployee";
		}

		else
			return "home/notfound.html";
	}

	@RequestMapping(value = "/insertCourse", method = RequestMethod.POST)
	public String insertCourseEmployee(int id, Employee employee, @RequestParam("idCourse") int idCourse,
			RedirectAttributes redirectAttributes) {

		System.out.println(" (1) id course: " + idCourse + " id employee: " + id);

		Optional<Employee> employeeFound = findOneEmployeeById(id);
		Optional<Course> courseFound = findOneCourseById(idCourse);

		if (employeeFound.isPresent() && courseFound.isPresent()) {
			System.out.println(" (2) id course: " + idCourse + " id employee: " + id);
			Enrollment enrollment = new Enrollment();

			enrollment.setCourse(courseFound.get());
			enrollment.setEmployee(employeeFound.get());

			enrollmentRepository.save(enrollment);

			redirectAttributes.addFlashAttribute("message",
					"You successfully enrolled " + employeeFound.get().getName() + " "
							+ employeeFound.get().getSurname() + " in " + courseFound.get().getCertificate().getName()
							+ "!");

			return "redirect:/employee/items/course/addCourse?id=" + id;
		}

		else
			return "home/notfound.html";
	}
	
	@RequestMapping("/updateEmployeeCourse")
	public String updateCourseEmployee(int id, Model model) {

		Optional<Enrollment> enrollmentFound = findOneEnrollmentById(id);

		if (enrollmentFound.isPresent()) {
			
		
			model.addAttribute("employeefromController", enrollmentFound.get().getEmployee());
			model.addAttribute("enrollment", enrollmentFound.get());
			

			return "employeeitems/updatecourseemployee";
		}

		else
			return "home/notfound.html";
		

	}
		
	@PostMapping("/replaceEmployeeCourse/{idEnrollment}")
	public String replaceCourseEmployee(@PathVariable("idEnrollment") int id, Enrollment enrollment, RedirectAttributes redirectAttributes) {

		//System.out.println( id);
		
		Optional<Enrollment> enrollmentFound = findOneEnrollmentById(id);

		if (enrollmentFound.isPresent()) {
			
			if (!enrollmentFound.get().getStatus().equals(enrollment.getStatus()))
				enrollmentFound.get().setStatus(enrollment.getStatus());
			
			if (enrollmentFound.get().isApproved() != enrollment.isApproved())
				enrollmentFound.get().setApproved(enrollment.isApproved());
			
			enrollmentRepository.save(enrollmentFound.get());
			
			redirectAttributes.addFlashAttribute("message",
					"You successfully updated that enrollment! id: " + enrollmentFound.get().getId() +
					", status: " + enrollmentFound.get().getStatus() + ", approved: " + enrollmentFound.get().isApproved());

			return "redirect:/employee/items/course/updateEmployeeCourse?id=" + id;
			

		} else
			return "home/notfound.html";

	}

	
	
//--------------------------------------------------------------------------------
//------------------------- service to controller --------------------------------
//--------------------------------------------------------------------------------

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
