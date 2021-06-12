package com.fresco.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.bean.Student;
import com.fresco.service.StudentServiceFactory;
import com.fresco.service.StudentServiceImpl;
import com.fresco.utils.StudentNotFoundException;


@RestController
@RequestMapping("/")
public class StudentController {
	
	private StudentServiceFactory studentServiceFactory;
	private StudentServiceImpl studentServiceImpl;
	
	/**
	 * @param studentServiceFactory
	 * @param studentServiceImpl
	 */
	@Autowired
	public StudentController(StudentServiceFactory studentServiceFactory, StudentServiceImpl studentServiceImpl) {
		super();
		this.studentServiceFactory = studentServiceFactory;
		this.studentServiceImpl = studentServiceImpl;
	}

	@RequestMapping(value = "student/{id}", method= RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Student getStudent(@PathVariable("id") long id) throws Exception {
		Student student = studentServiceFactory.getInstance().getStudent(id);
		if(student == null) {
			throw new StudentNotFoundException(String.valueOf(id));
		}
		return student;
    }
	
	@RequestMapping(value = "students", method= RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Page<Student> getAllStudents(Pageable pageable) throws Exception {
		return studentServiceFactory.getInstance().getAllStudents(pageable);
    }
	
	@RequestMapping(value = "student", method= RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Student> addStudent(@Valid @RequestBody Student reqStudent) {
		Student student = studentServiceFactory.getInstance().addStudent(reqStudent);
		return new ResponseEntity<Student>(student, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "student/{studentId}", method= RequestMethod.PUT, produces = "application/json")
	public Student updateStudent(@PathVariable(value="studentId") long studentId, @Valid @RequestBody Student reqStudent) {
		Student student = studentServiceFactory.getInstance().updateStudent(studentId, reqStudent);
		if(student == null) {
			throw new StudentNotFoundException(String.valueOf(studentId));
		}
		return student;
	}
	
	@RequestMapping(value = "student/{studentId}", method= RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> deleteStudent(@PathVariable(value="studentId") long studentId) {
		studentServiceFactory.getInstance().deleteStudent(studentId);
		return new ResponseEntity<String>("Student Deleted", HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "student", method= RequestMethod.GET, produces = "application/json")
	public Page<Student> searchStudent(@RequestParam(value="firstName", required=false) String firstName, 
								@RequestParam(value="lastName", required=false) String lastName,
								@RequestParam(value="migratedDate", required=false) @DateTimeFormat(pattern="dd-MM-yyyy") Date migratedDate,
								Pageable pageable) {
		Page<Student> students = studentServiceFactory.getInstance().searchStudents(firstName, lastName, migratedDate, pageable);
		return students;
	}
	
	@RequestMapping(value = "students", method= RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> deleteAllStudent() {
		studentServiceImpl.deleteAllStudent();
		return new ResponseEntity<String>("All Student Deleted", HttpStatus.NO_CONTENT);
	}
}
