
  package dev.example.employeeCourse.boot.repository;
  
  
  import org.springframework.data.repository.CrudRepository;


import dev.example.employeeCourse.boot.model.Course;
  
  public interface CourseRepository extends CrudRepository<Course,Integer> {

	Course findTopByOrderByIdDesc();


  
  
  
  
  
  }
 