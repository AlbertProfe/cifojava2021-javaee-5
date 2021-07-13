package dev.example.employeeCourse.boot.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.example.employeeCourse.boot.model.Employee;
import dev.example.employeeCourse.boot.repository.EmployeeRepository;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@RequestMapping("/allEmployees")
	public String getAllEmployees(Model boxToView) {

		boxToView.addAttribute("employeeListfromControllerAndDB", 
				employeeRepository.findAll());

		return "employee/employees";
	}

	//-----------------------delete----------------------------------
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
	public String deleteAllEmployees () {

		
		 employeeRepository.deleteAll();
		

		return "redirect:/employee/allEmployees";

	}
	
	//-----------------------add----------------------------------
	@RequestMapping("/newEmployee")
	public String newEmpoyee() {

		return "employee/newemployee.html";
	}

	@RequestMapping("/addEmployee")
	public String inserEmployee(Employee employee) {

		employeeRepository.save(employee);

		return "redirect:/employee/allEmployees";
	}

	//-----------------------update----------------------------------
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
	
	//-----------------------detail----------------------------------
	@RequestMapping("/detailEmployee")
	public String detailEmpoyee(int id, Model model) {

		Optional<Employee> employeeFound = findOneEmployeeById(id);

		if (employeeFound.isPresent()) {

			model.addAttribute("employeefromController", employeeFound.get());
			return "employee/detailemployee";
		}

		else
			return "home/notfound.html";
	}
	
//--------------------------------------------------------------------------------
//------------------------- service to controller --------------------------------
//--------------------------------------------------------------------------------

	public Optional<Employee> findOneEmployeeById(int id) {

		// System.out.println("inside findEmployee" + id);
		Optional<Employee> employeeFound = employeeRepository.findById(id);
		// System.out.println("finishing findEmployee" + id);
		// System.out.println("finishing findEmployee" + employeeFound.get());
		return employeeFound;
	}

}
