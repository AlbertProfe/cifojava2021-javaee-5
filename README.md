Welcome to the cifojava2021-javaee-5 wiki!

# cifojava2021-javaee-5
JPA @Entity Employee and Images uploading/downloading
- Spring Boot JPA @RestController without View and Thymeleaf
- Rest Controller with image `public class EmployeeImageController` and Swagger
- Application.properties
- Classes: JPA @Entity Employee and CrudRepository Interface
- Database: H2 (local or in Memory) and ddl.auto **for data**
- MongoDB connection and db **for images**

## employeeCourse (java-EE spring)

- employeeCourse: 
   - Application.properties: mongoDB and H2
   - Java classes JPA
      - n:m TA as two @Entity and 1:n n:1 1:n
      - Employee <> Expense
      - Employee <> Holidays
      - Employee <> Enrollment <> Course <> Certificate  


## mongoDBimage (java-EE spring)

- mongoDBimage: MongoDB connection
   - Application.properties:
        - spring.data.mongodb.uri=mongodb+srv://cifo:XXXX@clustertest.XXXX.mongodb.net/employeeDB?retryWrites=true
   - Swagger: 
        - http://localhost:8086/swagger-ui.html#/ 
   - @RestController for images of Employee with MongoDB & JPA
        - `public EmployeeImage addEmployeeImage(@RequestParam String name, @RequestParam MultipartFile file) throws IOException`
        - `public ResponseEntity<byte[]> getEmployeeImage(@RequestParam String id) throws SQLException`
   - @RestController for Employee @Entity data with H2 local
   - Get images to our HTML with `<a href="http://localhost:8086/getEmployeeImage?id=60e803f1a8ba7a1256b36fac">`
  



`Upload an image wiht API Rest and Swagger:`
<a href="https://github.com/AlbertProfe/images/blob/main/cifospring2021/swaggerRest_image.png"><img src="https://github.com/AlbertProfe/images/blob/main/cifospring2021/swaggerRest_image.png" width="600"></a>


`Upload an image wiht API Rest and Swagger:`
<a href="https://github.com/AlbertProfe/images/blob/main/cifospring2021/swaggerRest_image2.png"><img src="https://github.com/AlbertProfe/images/blob/main/cifospring2021/swaggerRest_image2.png" width="600"></a>


`Download an image wiht API Rest and Swagger:`
<a href="https://github.com/AlbertProfe/images/blob/main/cifospring2021/swaggerRest_image3.png"><img src="https://github.com/AlbertProfe/images/blob/main/cifospring2021/swaggerRest_image3.png" width="600"></a>



`Download an image wiht API Rest and Swagger:`
<a href="https://github.com/AlbertProfe/images/blob/main/cifospring2021/swaggerRest_image4.png"><img src="https://github.com/AlbertProfe/images/blob/main/cifospring2021/swaggerRest_image4.png" width="600"></a>


