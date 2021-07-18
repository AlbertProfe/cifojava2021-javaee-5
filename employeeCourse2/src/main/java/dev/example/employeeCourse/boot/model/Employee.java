package dev.example.employeeCourse.boot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	public String name;
	public String surname;
	public int age;
	public String email;
	public double monthSalary;
	public String password;
	public String job;
	public String position;
	public String phoneNumber;
	public String address;

	public String employeeImageId;

	// we describe the relationship btw two tables in this case
	// it is a 1:n relationship btw Employee and Expense,
	// that is, one employee will have one or more expenses.
	// In Java we express that with an List
	// and the annotation @OneToMany in the part 1 of 1:n
	// and in the part n of 1:n we will use @ManyToOne.
	// We must use MAPPEDBY cause we need to declare which field is the link
	// they must share a field, there must be a common field, this field
	// we mark it with MAPPEDBY in employee class and @JoinColumn in expense class
	// the n part gets the foreign id from employee thanks to @JoinColumn
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Expense> expenses = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Enrollment> enrollments = new ArrayList<Enrollment>();

	public Employee() {
		super();
	}

	public Employee(String name, String surname, int age, String email, double monthSalary, String password) {
		super();

		this.name = name;
		this.surname = surname;
		this.age = age;
		this.email = email;
		this.monthSalary = monthSalary;
		this.password = password;
	}
	
	public Employee(String name, String surname, int age, String email, double monthSalary, String password, String job,
			String position, String phoneNumber, String address) {
		super();
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.email = email;
		this.monthSalary = monthSalary;
		this.password = password;
		this.job = job;
		this.position = position;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public List<Enrollment> getEnrollments() {
		return enrollments;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	// we should modify this getter to better use
	public void addExpense(Expense expense) {
		this.expenses.add(expense);
		expense.setEmployee(this);
	}

	// we should modify this getter to better use
	public void addEnrollment(Enrollment enrollment) {
		this.enrollments.add(enrollment);
		enrollment.setEmployee(this);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmployeeImageId() {
		return employeeImageId;
	}
	

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getAddress() {
		return address;
	}

	
	public void setEmployeeImageId(String employeeImageId) {
		this.employeeImageId = employeeImageId;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getMonthSalary() {
		return monthSalary;
	}

	public void setMonthSalary(double monthSalary) {
		this.monthSalary = monthSalary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", surname=" + surname + ", age=" + age + ", email=" + email
				+ ", monthSalary=" + monthSalary + ", password=" + password + ", job=" + job + ", position=" + position
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + ", employeeImageId=" + employeeImageId
				+ "]";
	}

	

	

}
