package dev.example.employeeCourse.boot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//refactor course >>> certificate to do n:m with @onetomany @maytoone
@Entity
@Table
public class Certificate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;
	private String author;
	private int hours;
	private int level;
	private boolean active;

	@OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL)
	private List<Course> courses = new ArrayList<Course>();

	public Certificate() {}

	public Certificate(String name, String author, int hours, int level, boolean active) {
		super();
		this.name = name;
		this.author = author;
		this.hours = hours;
		this.level = level;
		this.active = active;
	}

	public List<Course> getCourses() {
		return courses;
	}

	// we should modify this getter to better use
	public void adCourse(Course course) {
		this.courses.add(course);
		course.setCertificate(this);
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", author=" + author + ", hours=" + hours + ", level=" + level
				+ ", active=" + active + "]";
	}

}
