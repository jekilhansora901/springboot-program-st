package com.fresco.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fresco.bean.Student;

public interface StudentService {
	
	public Page<Student> getAllStudents(Pageable page);

	public Page<Student> searchStudents(String firstName, String lastName, Date migratedDate, Pageable page);

	public Student getStudent(long id);

	public Student addStudent(Student student);

	public Student updateStudent(long studentId, Student student);

	public void deleteStudent(long studentId);
	
}