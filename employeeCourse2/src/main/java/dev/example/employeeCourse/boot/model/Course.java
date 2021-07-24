package dev.example.employeeCourse.boot.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String room;
	private boolean online;

	// @ManyToOne
	// @JoinColumn(name = "TEACHER_FID")
	// private Employee teacher;
	private String teacher;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date initDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date finishDate;
	private String calendarCourse;
	private double fee;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<Enrollment> enrollments = new ArrayList<Enrollment>();

	@ManyToOne
	@JoinColumn(name = "CERTIFICATE_FID")
	private Certificate certificate;

	public Course() {
		super();
	}

	public Course(String room, boolean online, Date initDate, Date finishDate, String calendarCourse, double fee,
			String teacher) {
		super();

		this.room = room;
		this.online = online;
		this.initDate = initDate;
		this.finishDate = finishDate;
		this.calendarCourse = calendarCourse;
		this.fee = fee;
		this.teacher = teacher;
	}
	
	

	public Course(String room, boolean online, Date initDate, Date finishDate, String calendarCourse, double fee,
			String teacher, Certificate certificate) {
		super();
		this.room = room;
		this.online = online;
		this.teacher = teacher;
		this.initDate = initDate;
		this.finishDate = finishDate;
		this.calendarCourse = calendarCourse;
		this.fee = fee;
		this.certificate = certificate;
	}

	// we should modify this getter to better use
	public void addEnrollment(Enrollment enrollment) {
		this.enrollments.add(enrollment);
		enrollment.setCourse(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getCalendarCourse() {
		return calendarCourse;
	}

	public void setCalendarCourse(String calendarCourse) {
		this.calendarCourse = calendarCourse;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	/*
	 * public Employee getTeacher() { return teacher; }
	 * 
	 * public void setTeacher(Employee teacher) { this.teacher = teacher; }
	 */

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "EnrollmentCourse [id=" + id + ", room=" + room + ", online=" + online + ", teacher=" + teacher
				+ ", initDate=" + initDate + ", finishDate=" + finishDate + ", calendarCourse=" + calendarCourse
				+ ", fee=" + fee + "]";
	}

}
