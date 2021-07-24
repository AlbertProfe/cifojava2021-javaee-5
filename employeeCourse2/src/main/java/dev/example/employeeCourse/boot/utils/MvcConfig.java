
  package dev.example.employeeCourse.boot.utils;
  
  import org.springframework.context.annotation.Configuration; import
  org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
  
  @Configuration public class MvcConfig implements WebMvcConfigurer {
  
  public void addViewControllers(ViewControllerRegistry registry) {
  registry.addViewController("/").setViewName("home");
  registry.addViewController("/home").setViewName("home");
  registry.addViewController("/login").setViewName("home");
  registry.addViewController("/employee/*").setViewName("employee/employee");
  registry.addViewController("/expense/*").setViewName("expense/expense");
  registry.addViewController("/fillin/*").setViewName("employee/fillin"); }
  
  }
 