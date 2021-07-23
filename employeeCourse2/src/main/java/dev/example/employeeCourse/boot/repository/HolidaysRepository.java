package dev.example.employeeCourse.boot.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dev.example.employeeCourse.boot.model.Employee;
import dev.example.employeeCourse.boot.model.Holidays;

public interface HolidaysRepository extends CrudRepository<Holidays, Integer> {

	Optional<Iterable<Holidays>> findAllHolidaysByEmployee(Employee employee);

	Optional<Iterable<Holidays>> findAllExpenseByEmployee(Employee employee);

}
