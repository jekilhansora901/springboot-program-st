package com.fresco.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fresco.bean.Student;
import com.fresco.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Page<Student> getAllStudents(Pageable page) {
		return studentRepository.findAll(page);
	}

	@Override
	public Page<Student> searchStudents(String firstName, String lastName, Date migratedDate, Pageable page) {
		if(firstName != null && lastName == null && migratedDate == null)
			return studentRepository.findByFirstNameContainingIgnoreCase(firstName, page);
		else if(firstName == null && lastName != null && migratedDate == null)
			return studentRepository.findByLastNameContainingIgnoreCase(lastName, page);
		else if(firstName == null && lastName == null && migratedDate != null)
			return studentRepository.findByMigratedDateGreaterThan(migratedDate.getTime(), page);
		else
			return studentRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndMigratedDateGreaterThan(firstName, lastName, migratedDate.getTime(), page);
	}

	@Override
	public Student getStudent(long id) {
		Optional<Student> student = studentRepository.findById(id);
		if(student.isPresent()) {
			return student.get();
		}
		return null;
	}

	@Override
	public Student addStudent(Student student) {
		student.setCreatedDate(System.currentTimeMillis());
		return studentRepository.save(student);
	}

	@Override
	public Student updateStudent(long studentId, Student student) {
		
		Optional<Student> studentData = studentRepository.findById(studentId);
		if(!studentData.isPresent()) {
			return null;
		}
		return studentRepository.save(student);
	}

	@Override
	public void deleteStudent(long studentId) {
		studentRepository.deleteById(studentId);
	}
	
	public List<Student> saveAllStudents(List<Student> studentList) {
		return studentRepository.saveAll(studentList);
	}
	
	public void deleteAllStudent() {
		studentRepository.deleteAll();
	}
}
