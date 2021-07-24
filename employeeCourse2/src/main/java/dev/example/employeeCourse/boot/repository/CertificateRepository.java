package dev.example.employeeCourse.boot.repository;

import org.springframework.data.repository.CrudRepository;

import dev.example.employeeCourse.boot.model.Certificate;

public interface CertificateRepository extends CrudRepository<Certificate, Integer> {
	
	Certificate findTopByOrderByIdDesc();

}
