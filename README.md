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

### employeeCourse: 
   - Application.properties: mongoDB and H2
   - Java classes JPA
       n:m TA as two @Entity and `1:n` `n:1` `1:n`
      - Employee <> Expense (@Entity H2) `1:n` bidirectional
      - Employee <> Holidays (@Entity H2) `1:n` unidirectional
      - Employee <> Enrollment <> Course <> Certificate (@Entity H2)  `n:m`
      - Employee <> EmployeeImage (@Document MongoDB) `1:1` birectional
  - Rest Controller (Employee and EmployeeImage) 



`Project Tree:`

| <a href="https://github.com/AlbertProfe/images/blob/main/cifospring2021/2021-07-MongoDB-000041.png"><img src="https://github.com/AlbertProfe/images/blob/main/cifospring2021/2021-07-MongoDB-000041.png" width="250"></a>  | <a href="https://github.com/AlbertProfe/images/blob/main/cifospring2021/2021-07-MongoDB-000040.png"><img src="https://github.com/AlbertProfe/images/blob/main/cifospring2021/2021-07-MongoDB-000040.png" width="250"></a>  | <a href="https://github.com/AlbertProfe/images/blob/main/cifospring2021/2021-07-MongoDB-000042.png"><img src="https://github.com/AlbertProfe/images/blob/main/cifospring2021/2021-07-MongoDB-000042.png" width="250"></a> |


### employeeCourse2: 
   - Application.properties: mongoDB and H2
   - Layout and fragment
      - layout.html
      - layout_login.html
   - Java classes JPA
      - n:m TA as two @Entity and `1:n` `n:1` `1:n`
      - Employee <> Expense (@Entity H2) `1:n` bidirectional
      - Employee <> Holidays (@Entity H2) `1:n` unidirectional
      - Employee <> Enrollment <> Course <> Certificate (@Entity H2)  `n:m`
      - Employee <> EmployeeImage (@Document MongoDB) `1:1` birectional
  - Rest Controller (Employee and EmployeeImage)
  - CommandLineRunner
       - HomeController Fill in entities in H2 and assign them
  - Employee (Items detail)
      - Courses
        - Add couse to employee
        - Update status
      - Expenses
        - Create expense
        - Delete expense
        - Update expense
      - Holidays
        - Add holidays date
        - Delete  holidays dates
        - Create holidays (rejects to create an existing year)
      - Upload Image
         - Rest Controller via Postman
      - Add Image:
        - upload image option (EmployeImage to mongoDB)
        -  `1:1 - bidirectional`  (EmployeImage <> Employee)


screenshoots from app: https://github.com/AlbertProfe/images/tree/main/cifospring2021/mockup

`Employee JPA relationships:`

<a href="https://github.com/AlbertProfe/images/blob/main/cifospring2021/employeeCourse.png"><img src="https://github.com/AlbertProfe/images/blob/main/cifospring2021/employeeCourse.png" width="500"></a>

### employeeCourse3: 

Code on repo: https://github.com/AlbertProfe/employeecourse

Deployed on `Heroku`: https://employeecourse.herokuapp.com/

   - Application.properties: mongoDB and H2
   - Layout and fragment
      - layout.html
      - layout_login.html
      - layout_detail.html
   - Java classes JPA
      - n:m TA as two @Entity and `1:n` `n:1` `1:n`
      - Employee <> Expense (@Entity H2) `1:n` bidirectional
      - Employee <> Holidays (@Entity H2) `1:n` unidirectional
      - Employee <> Enrollment <> Course <> Certificate (@Entity H2)  `n:m`
      - Employee <> EmployeeImage (@Document MongoDB) `1:1` birectional
  - Rest Controller (Employee and EmployeeImage)
  - CommandLineRunner
       - HomeController Fill in entities in H2 and assign them
       - Employee @entity
         - `setEmployeeImageId` with `employeeImageId` string id from mongodb with  *to-do*
  - Employee (Items detail)
      - Empployee Detail
        - Path (th:scr) to mognodb image from `getEmployeeImageId`  via rest controller *to-do*
      - Courses
        - Add couse to employee (insert date and status *to-do*)
        - Update status
      - Expenses
        - Create expense
        - Delete expense (afer deleting return to Expenses or crete new detail *to-do*)
        - Update expense
      - Holidays
        - Add holidays date
        - Delete  holidays dates
        - Create holidays (rejects to create an existing year)
      - Upload Image
         - Rest Controller via Postman
      - Add Image:
        - upload image option (EmployeImage to mongoDB)
        -  `1:1 - bidirectional`  (EmployeImage <> Employee)
  - Enrollments *to-do*
  - Courses and filters *to-do*
  - Thymeleaf pagination *to-do*
  - Thymeleaf validation *to-do*
  - Abstract class and inhrence with audit and DTO *to-do*
  - Java 8 and H2 in memory to `DEPLOY: upload to Heroku`


`How layout works:`

<a href="https://github.com/AlbertProfe/images/blob/main/cifospring2021/layout/layout_0.png"><img src="https://github.com/AlbertProfe/images/blob/main/cifospring2021/layout/layout_0.png" width="500"></a>
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


