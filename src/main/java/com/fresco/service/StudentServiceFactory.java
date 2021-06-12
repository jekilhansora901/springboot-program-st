package com.fresco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceFactory {

	private StudentServiceImpl studentServiceImpl;
	private StudentLegacyServiceImpl studentLegacyServiceImpl;
	private boolean featureToggle;
	/**
	 * @param studentServiceImpl
	 * @param studentLegacyServiceImpl
	 * @param featureToggle
	 */
	@Autowired
	public StudentServiceFactory(@Qualifier("studentServiceImpl")StudentServiceImpl studentServiceImpl, 
							  @Qualifier("studentLegacyServiceImpl")StudentLegacyServiceImpl studentLegacyServiceImpl,
							  @Value("${legacy-toggle}") boolean featureToggle) {
		super();
		this.studentServiceImpl = studentServiceImpl;
		this.studentLegacyServiceImpl = studentLegacyServiceImpl;
		this.featureToggle = featureToggle;
	}
	
	public StudentService getInstance() {
		if(featureToggle) {
			return studentLegacyServiceImpl;
		}
		return studentServiceImpl;
	}
}
