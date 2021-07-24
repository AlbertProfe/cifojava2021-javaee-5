package dev.example.employeeCourse.boot.repository;


import org.springframework.data.repository.CrudRepository;

import dev.example.employeeCourse.boot.model.Certificate;
import dev.example.employeeCourse.boot.model.Employee;

	public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
		
		
		public Iterable<Employee> findByAge (int age);

		public Employee findTopByOrderByIdDesc();
		
	}


