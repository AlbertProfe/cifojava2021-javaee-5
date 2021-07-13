package dev.example.employeeCourse.boot.repository;

import org.springframework.data.repository.CrudRepository;

import dev.example.employeeCourse.boot.model.Expense;

public interface ExpenseRepository extends CrudRepository<Expense,Integer> {

}
