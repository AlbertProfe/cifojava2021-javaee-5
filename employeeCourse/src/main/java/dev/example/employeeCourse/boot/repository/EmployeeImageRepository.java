package dev.example.employeeCourse.boot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.example.employeeCourse.boot.model.EmployeeImage;

public interface EmployeeImageRepository extends MongoRepository<EmployeeImage, String> {

	}
	

