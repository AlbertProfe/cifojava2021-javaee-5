package dev.example.employeeCourse.boot.repository;
  

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dev.example.employeeCourse.boot.model.Employee;
import dev.example.employeeCourse.boot.model.Enrollment;
  
  public interface EnrollmentRepository extends CrudRepository<Enrollment,Integer> {
	  
	 Optional<Iterable<Enrollment>> findAllEnrollmentByEmployee (Employee employee);
  
  
  
  
  
  }
 