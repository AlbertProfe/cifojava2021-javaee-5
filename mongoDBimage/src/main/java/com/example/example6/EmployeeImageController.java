package com.example.example6;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Optional;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EmployeeImageController {

	@Autowired
	private EmployeeImageRepository employeeImageRepository;

	@PostMapping("/employeeImage")
	public EmployeeImage addEmployeeImage(@RequestParam String name, @RequestParam MultipartFile file) throws IOException {
		
		EmployeeImage employeeImage = new EmployeeImage();
		employeeImage.setName(name);
		employeeImage.setImage(new Binary(file.getBytes()));

		return employeeImageRepository.save(employeeImage);
	}

	@GetMapping("/getEmployeeImageData")
	public String getEmployeeImageData(@RequestParam String id) {
		
		
		Optional<EmployeeImage> employeeImage = employeeImageRepository.findById(id);
		Encoder encoder = Base64.getEncoder();

		return encoder.encodeToString(employeeImage.get().getImage().getData());

	}

	@GetMapping("/getEmployeeImage")
	public ResponseEntity<byte[]> getEmployeeImage(@RequestParam String id) throws SQLException {
		
		Optional<EmployeeImage> employeeImage = employeeImageRepository.findById(id);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    return new ResponseEntity<>(employeeImage.get().getImage().getData(), headers, HttpStatus.OK);
		
		
	}

}
