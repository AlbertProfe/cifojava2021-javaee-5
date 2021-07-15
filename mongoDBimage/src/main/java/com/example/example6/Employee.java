package com.example.example6;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	public String fid_employeeImage;


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


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
				+ ", monthSalary=" + monthSalary + "]\n";
	}

}
