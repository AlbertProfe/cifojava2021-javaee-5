package dev.example.employeeCourse.boot.controller;


import java.io.IOException;
import java.util.Optional;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.example.employeeCourse.boot.model.Course;
import dev.example.employeeCourse.boot.model.Employee;
import dev.example.employeeCourse.boot.model.EmployeeImage;
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
@RequestMapping("/employee/items/")
public class EmployeeItemsController {

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
			
			Optional<Iterable<Holidays>> holidaysFound = holidaysRepository
					.findAllHolidaysByEmployee(employeeRepository.findById(id).get());

			model.addAttribute("holidaysfromController", holidaysFound.get());

			return "employeeitems/detailemployee";
		}

		else
			return "home/notfound.html";
	}

	// ----------------------- add image employee ---------------------------------
		@RequestMapping("/addImageEmployee")
		public String addImageEmployee(int id, Model model) {

			Optional<Employee> employeeFound = findOneEmployeeById(id);

			if (employeeFound.isPresent()) {

				model.addAttribute("employeefromController", employeeFound.get());

				return "employeeitems/addimage";
			}

			else
				return "home/notfound.html";
		}

		@PostMapping("/insertEmployeeImage")
		public String insertEmployeeImage(@RequestParam String name, @RequestParam int employeeId,
				@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

			// System.out.println("Image name: " + name);

			//we create the @Document object that NOT comes from the web to set the values ...
			//that is, BECAUSE it doesnt comes from the view we need to create it
			//there is then no ID (Dependency Injection) working here... it so sad ...
			EmployeeImage employeeImage = new EmployeeImage();
			//so new I have the document-object empty therefore I need to set it ...
			employeeImage.setName(name);
			//to set the onetoone relationship I need to do the job ..
			employeeImage.setEmployeeId(employeeId);
			employeeImage.setImage(new Binary(file.getBytes()));

			employeeImageRepository.save(employeeImage);

			Optional<Employee> employeeFound = findOneEmployeeById(employeeId);

			if (employeeFound.isPresent()) {
				
				//now i need to set in @entity object field the id from mongodb document
				//so i have a BIDIRECTIONAL one to one relationship
				//between the montesco world (JPA - entity) and the capuletos (Mongodb - document)
				employeeFound.get().setEmployeeImageId(employeeImage.getId());
				employeeRepository.save(employeeFound.get());
			}

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + file.getOriginalFilename() + "!");

			return "redirect:/employee/items/addImageEmployee?id=" + employeeId;
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
