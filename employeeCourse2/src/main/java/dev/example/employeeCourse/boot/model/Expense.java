package dev.example.employeeCourse.boot.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table
public class Expense {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	private double value;
	
	
	//very important: we dont save an object or entity in our db
	//so we are going to save the foreign id from employee
	//thank to object employee from which we get its id
	//@JoinColumn then is the annotation that besides giving the
	//name to the column and create it, it defines the relationship
	//btw 1:n, btw employee and expense cause it has the foreign id: the shared field.
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_FID")
	private Employee employee;
		
	public Expense() {
		super();
	}
	
	public Expense(String name, Date date, double value) {
		super();
	
		this.name = name;
		this.date = date;
		this.value = value;
	}
	
	public Expense(String name, Date date, double value, Employee employee) {
		super();
	
		this.name = name;
		this.date = date;
		this.value = value;
		this.employee = employee;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", name=" + name + ", date=" + date + ", value=" + value + "]";
	}
	

}
