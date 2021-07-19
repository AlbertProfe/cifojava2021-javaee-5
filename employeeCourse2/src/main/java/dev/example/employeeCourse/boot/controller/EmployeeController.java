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
import dev.example.employeeCourse.boot.model.Expense;
import dev.example.employeeCourse.boot.repository.CertificateRepository;
import dev.example.employeeCourse.boot.repository.CourseRepository;
import dev.example.employeeCourse.boot.repository.EmployeeImageRepository;
import dev.example.employeeCourse.boot.repository.EmployeeRepository;
import dev.example.employeeCourse.boot.repository.EnrollmentRepository;
import dev.example.employeeCourse.boot.repository.ExpenseRepository;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

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

	@RequestMapping("/allEmployees")
	public String getAllEmployees(Model boxToView) {

		boxToView.addAttribute("employeeListfromControllerAndDB", employeeRepository.findAll());

		return "employee/employees";
	}

	// -----------------------delete----------------------------------
	@RequestMapping("/deleteEmployee")
	public String removeEmployee(int id, Model model) {

		// System.out.println("inside removeEmployee" + id);
		Optional<Employee> employeeFound = findOneEmployeeById(id);

		// System.out.println("find inside removeEmployee" + employeeFound.get());

		if (employeeFound.isPresent()) {

			employeeRepository.deleteById(id);
			model.addAttribute("message", "done");
			model.addAttribute("employeeDeleted", employeeFound.get());
		}

		else {
			model.addAttribute("message", "error");
		}

		// System.out.println("finishing removeEmployee" + id);
		return "employee/deletedemployee.html";
	}

	@RequestMapping("/deleteAllEmployees")
	public String deleteAllEmployees() {

		employeeRepository.deleteAll();

		return "redirect:/employee/allEmployees";

	}

	// -----------------------add----------------------------------
	@RequestMapping("/newEmployee")
	public String newEmpoyee() {

		return "employee/newemployee.html";
	}

	@RequestMapping("/addEmployee")
	public String inserEmployee(Employee employee) {

		employeeRepository.save(employee);

		return "redirect:/employee/allEmployees";
	}

	// -----------------------update----------------------------------
	@RequestMapping("/updateEmployee")
	public String updateEmpoyee(int id, Model model) {

		Optional<Employee> employeeFound = findOneEmployeeById(id);

		if (employeeFound.isPresent()) {

			model.addAttribute("employeefromController", employeeFound.get());
			return "employee/updateemployee";
		}

		else
			return "home/notfound.html";
	}

	@PostMapping("/replaceEmployee/{idFromView}")
	public String replaceEmployee(@PathVariable("idFromView") int id, Employee employee) {

		Optional<Employee> employeeFound = findOneEmployeeById(id);

		if (employeeFound.isPresent()) {

			if (employee.getName() != null)
				employeeFound.get().setName(employee.getName());
			if (employee.getSurname() != null)
				employeeFound.get().setSurname(employee.getSurname());
			if (employee.getPassword() != null)
				employeeFound.get().setPassword(employee.getPassword());
			if (employee.getEmail() != null)
				employeeFound.get().setEmail(employee.getEmail());
			if (employee.getAge() != 0)
				employeeFound.get().setAge(employee.getAge());
			if (employee.getMonthSalary() != 0.0)
				employeeFound.get().setMonthSalary(employee.getMonthSalary());

			employeeRepository.save(employeeFound.get());
			return "redirect:/employee/allEmployees";

		} else
			return "home/notfound.html";

	}

	// -----------------------detail----------------------------------
	@RequestMapping("/detailEmployee")
	public String detailEmployee(int id, Model model) {

		Optional<Employee> employeeFound = findOneEmployeeById(id);

		if (employeeFound.isPresent()) {

			model.addAttribute("employeefromController", employeeFound.get());

			Optional<Iterable<Enrollment>> enrollmentFound = enrollmentRepository
					.findAllEnrollmentByEmployee(employeeRepository.findById(id).get());

			model.addAttribute("enrollmentfromController", enrollmentFound.get());

			Optional<Iterable<Expense>> expenseFound = expenseRepository
					.findAllExpenseByEmployee(employeeRepository.findById(id).get());

			model.addAttribute("expensefromController", expenseFound.get());

			return "employee/detailemployee";
		}

		else
			return "home/notfound.html";
	}

	// ----------------------- enrollment ----------------------------------
	@RequestMapping("/addCourse")
	public String addCourseEmployee(int id, Model model) {

		Optional<Employee> employeeFound = findOneEmployeeById(id);

		if (employeeFound.isPresent()) {

			model.addAttribute("employeefromController", employeeFound.get());
			model.addAttribute("coursesfromController", courseRepository.findAll());
			model.addAttribute("certificatesfromController", certificateRepository.findAll());

			return "employee/addcourseemployee";
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

			return "redirect:/employee/addCourse?id=" + id;
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
			

			return "employee/updatecourseemployee";
		}

		else
			return "home/notfound.html";
		

	}
	
	
	@PostMapping("/replaceEmployeeCourse/{idEnrollment}")
	public String replaceCourseEmployee(@PathVariable("idEnrollment") int id, Enrollment enrollment, RedirectAttributes redirectAttributes) {

		System.out.println( id);
		
		Optional<Enrollment> enrollmentFound = findOneEnrollmentById(id);

		if (enrollmentFound.isPresent()) {

			enrollmentFound.get().setStatus(enrollment.getStatus());
			enrollmentFound.get().setApproved(enrollment.isApproved());

			enrollmentRepository.save(enrollmentFound.get());
			
			redirectAttributes.addFlashAttribute("message",
					"You successfully updated that enrollment !");

			return "redirect:/employee/updateEmployeeCourse?id=" + id;
			

		} else
			return "home/notfound.html";

	}

	// ----------------------- add image employee ---------------------------------
	@RequestMapping("/addImageEmployee")
	public String addImageEmployee(int id, Model model) {

		Optional<Employee> employeeFound = findOneEmployeeById(id);

		if (employeeFound.isPresent()) {

			model.addAttribute("employeefromController", employeeFound.get());

			return "employee/addimage";
		}

		else
			return "home/notfound.html";
	}

	@PostMapping("/insertEmployeeImage")
	public String insertEmployeeImage(@RequestParam String name, @RequestParam int employeeId,
			@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

		// System.out.println("Image name: " + name);

		EmployeeImage employeeImage = new EmployeeImage();
		employeeImage.setName(name);
		employeeImage.setEmployeeId(employeeId);
		employeeImage.setImage(new Binary(file.getBytes()));

		employeeImageRepository.save(employeeImage);

		Optional<Employee> employeeFound = findOneEmployeeById(employeeId);

		if (employeeFound.isPresent()) {

			employeeFound.get().setEmployeeImageId(employeeImage.getId());
			employeeRepository.save(employeeFound.get());
		}

		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/employee/addImageEmployee?id=" + employeeId;
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
