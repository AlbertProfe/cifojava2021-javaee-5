package dev.example.employeeCourse.boot.repository;

import org.springframework.data.repository.CrudRepository;

import dev.example.employeeCourse.boot.model.Holidays;

public interface HolidaysRepository extends CrudRepository<Holidays, Integer> {

}
