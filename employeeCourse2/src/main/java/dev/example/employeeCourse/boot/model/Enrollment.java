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
public class Enrollment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date enrollmentDate;
	private int testMark;
	private boolean approved;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_FID")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "COURSE_FID")
	private Course course;
	
	public Enrollment() {
		super();
	}
	
	public Enrollment(Date enrollmentDate, int testMark, boolean approved) {
		super();
		this.enrollmentDate = enrollmentDate;
		this.testMark = testMark;
		this.approved = approved;
	}

	
	public Enrollment(Date enrollmentDate, int testMark, boolean approved, Employee employee, Course course) {
		super();
		this.enrollmentDate = enrollmentDate;
		this.testMark = testMark;
		this.approved = approved;
		this.employee = employee;
		this.course = course;
	}

	
	
	public Enrollment(Date enrollmentDate, int testMark, boolean approved, Employee employee) {
		super();
		this.enrollmentDate = enrollmentDate;
		this.testMark = testMark;
		this.approved = approved;
		this.employee = employee;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}
	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	public int getTestMark() {
		return testMark;
	}

	public void setTestMark(int testMark) {
		this.testMark = testMark;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Enrollment [id=" + id + ", enrollmentDate=" + enrollmentDate + ", testMark=" + testMark + ", approved="
				+ approved + ", employee=" + employee +  "]";
	}
	
}
