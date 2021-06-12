package com.fresco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.bean.Student;
import com.fresco.service.StudentLegacyServiceImpl;
import com.fresco.utils.StudentNotFoundException;


@RestController
@RequestMapping("/legacy/")
public class StudentLegacyController {
	@Autowired
	private StudentLegacyServiceImpl studentLegacyServiceImpl;

	
	@RequestMapping(value = "student/{id}", method= RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Student getStudent(@PathVariable("id") long id) throws Exception {
		Student student = studentLegacyServiceImpl.getStudent(id);
		if(student == null) {
			throw new StudentNotFoundException(String.valueOf(id));
		}
		return student;
    }
	
	@RequestMapping(value = "students", method= RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Page<Student> getAllStudents(Pageable pageable) throws Exception {
		return studentLegacyServiceImpl.getAllStudents(pageable);
    }
	

	@RequestMapping(value = "migrateStudents", method= RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Student>> addAllStudent() {
		List<Student> student = studentLegacyServiceImpl.migrateAllStudent();
		return new ResponseEntity<List<Student>>(student, HttpStatus.CREATED);
	}
}
