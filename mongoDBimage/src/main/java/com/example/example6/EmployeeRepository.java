package com.example.example6;


import org.springframework.data.repository.CrudRepository;

	public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
		
		
		public Iterable<Employee> findByAge (int age);
		
	}


