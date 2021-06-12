package com.fresco.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fresco.bean.Student;
import com.fresco.utils.ServiceNotImplementedException;

@Service
@Primary
public class StudentLegacyServiceImpl implements StudentService {

	@Autowired
	private StudentServiceImpl studentServiceImpl;
	
	@Value("${legacy-refresh-interval}")
	private long refreshInterval;
	
	@Override
	public Page<Student> getAllStudents(Pageable page) {
		try {
			byte[] jsonData = Files.readAllBytes(Paths.get("data.json"));
			ObjectMapper objectMapper = new ObjectMapper();
			
			//convert json string to object
			List<Student> student = objectMapper.readValue(jsonData, new TypeReference<List<Student>>(){});
			Page<Student> pageResp = new PageImpl<Student>(student);
			return pageResp;
		} catch(IOException e) {
			return null;
		}
	}

	@Override
	public Page<Student> searchStudents(String firstName, String lastName, Date migratedDate, Pageable page) {
		throw new ServiceNotImplementedException();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Student getStudent(long id) {
		
		Student student = studentServiceImpl.getStudent(id);
		if(student != null) {
			if((System.currentTimeMillis() - student.getMigratedDate()) <= refreshInterval) {
				return student;
			}
		}
		
		Page<Student> students = getAllStudents(new PageRequest(0, 1000));
		if(students == null) {
			return null;
		}
		Optional<Student> legacyStudent = students.getContent().stream().filter(u -> u.getId() == id).findFirst();
		if(legacyStudent.isPresent()) {
			if(student != null) {
				return studentServiceImpl.updateStudent(student.getId(), legacyStudent.get());
			}
			return studentServiceImpl.addStudent(legacyStudent.get());
		}
		return null;
	}

	@Override
	public Student addStudent(Student student) {
		throw new ServiceNotImplementedException();
	}

	@Override
	public Student updateStudent(long studentId, Student student) {
		throw new ServiceNotImplementedException();
	}

	@Override
	public void deleteStudent(long studentId) {
		throw new ServiceNotImplementedException();
	}
	
	public List<Student> migrateAllStudent() {
		Page<Student> students = getAllStudents(null);
		if(students.getTotalElements() > 0) {
			List<Student> studentList = students.getContent().stream().map(student -> {
				Student st = student;
				st.setCreatedDate(System.currentTimeMillis());
				st.setMigratedDate(System.currentTimeMillis());
				return st;
			}).collect(Collectors.toList());
			return studentServiceImpl.saveAllStudents(studentList);
		}
		return null;
	}

}
