package com.fresco.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fresco.bean.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	Page<Student> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndMigratedDateGreaterThan(String firstName, String lastName, long migratedDate, Pageable page);
	
	Page<Student> findByFirstNameContainingIgnoreCase(String firstName, Pageable page);
	
	Page<Student> findByLastNameContainingIgnoreCase(String lastName, Pageable page);
	
	Page<Student> findByMigratedDateGreaterThan(long migratedDate, Pageable page);

}