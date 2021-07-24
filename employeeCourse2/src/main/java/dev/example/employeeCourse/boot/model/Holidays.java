package dev.example.employeeCourse.boot.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
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
public class Holidays {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int year;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ElementCollection
	private List<Date> daysHolidays; 
	private int daysHolidaysQty;
	private int officialYearHolidaysQty;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_FID")
	private Employee employee;
	
	
	public Holidays() {
		super();
	}
	public Holidays(int year, int officialYearHolidaysQty) {
		super();
		this.year = year;
		this.officialYearHolidaysQty = officialYearHolidaysQty;
		this.daysHolidays = new ArrayList<Date>();
	}
	
	public Holidays(int year, int officialYearHolidaysQty, Employee employee) {
		super();
		this.year = year;
		this.officialYearHolidaysQty = officialYearHolidaysQty;
		this.daysHolidays = new ArrayList<Date>();
		this.employee = employee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public List<Date> getDaysHolidays() {
		return daysHolidays;
	}
	
	public void addHolidays(Date dateToAdd) {
		this.getDaysHolidays().add(dateToAdd);
		this.setDaysHolidaysQty();
		this.getDaysHolidays().sort(null);
	}
	
	public void removeHolidays(Date dateToRemove) {
		this.getDaysHolidays().remove(dateToRemove);
		this.setDaysHolidaysQty();
		this.getDaysHolidays().sort(null);
	}
	
	
	public int getDaysHolidaysQty() {
		return daysHolidaysQty;
	}
	public void setDaysHolidaysQty() {
		this.daysHolidaysQty = this.getDaysHolidays().size();
	}
		
	public int getOfficialYearHolidaysQty() {
		return officialYearHolidaysQty;
	}
	public void setOfficialYearHolidaysQty(int officialYearHolidaysQty) {
		this.officialYearHolidaysQty = officialYearHolidaysQty;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@Override
	public String toString() {
		return "Holidays [id=" + id + ", year=" + year + ", daysHolidays=" + daysHolidays + ", daysHolidaysQty="
				+ daysHolidaysQty + ", employee=" + employee + "]";
	}
	
}
