package dev.example.employeeCourse.boot.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dev.example.employeeCourse.boot.model.Employee;
import dev.example.employeeCourse.boot.model.Expense;

public interface ExpenseRepository extends CrudRepository<Expense,Integer> {

	Optional<Iterable<Expense>> findAllExpenseByEmployee(Employee employee);

}
