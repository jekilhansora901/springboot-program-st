package com.fresco.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fresco.SpringbootProblemStApplication;
import com.fresco.bean.AddressInfo;
import com.fresco.bean.ContactInfo;
import com.fresco.bean.Student;
import com.fresco.service.StudentServiceFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootProblemStApplication.class)
@WebAppConfiguration
public class StudentTestController {
		
	   protected MockMvc mvc;
	   
	   String studentBody = "{\"id\":500,\"firstName\":\"First Test\",\"lastName\":\"Last\","
	   		+ "\"contactDetails\":{\"email1\":\"test@email1.com\",\"mobilePhone\":\"789561230\",\"phoneNumber\":\"321654897\",\"email2\":\"test@email2.com\"},"
	   		+ "\"addressDetails\":{\"addressLine1\":\"Address1\",\"addressLine2\":\"Address2\",\"city\":\"City\",\"state\":\"State\",\"zipcode\":\"600000\",\"addressNote\":\"Note\"}}";
	   Student student;
	   @Autowired
	   WebApplicationContext webApplicationContext;

	   @Before
	   public void setUp() {
	      mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	      AddressInfo addressInfo = new AddressInfo("Address1", "Address2", "City", "State", "600000", "Note");
	      ContactInfo contactInfo = new ContactInfo("test@email1.com", "789561230", "321654897", "test@email2.com");
	      student = new Student(500, "First Test", "Last", contactInfo, addressInfo, System.currentTimeMillis(), 0);
	      student.toString();
	   }
	   
	   @Test
	   public void getStudentNotFound() throws Exception {
	      String uri = "/student/20";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(404, status);
	   }
	   
	   @Test
		public void addStudent() throws Exception {
		   	ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			mvc.perform(post("/student").content(studentBody).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isCreated())
							.andExpect(jsonPath("$.id").value(student.getId()))
							.andReturn();
		}
	   
	   @Test
		public void addStudentFirstName() throws Exception {
		   String studentBody1 = "{\"id\":3,\"firstName\":\"Y\",\"lastName\":\"L\",\"contactDetails\":{\"email1\":\"test@fd.com\",\"mobilePhone\":\"7755349730\",\"phoneNumber\":\"1234795623\",\"email2\":\"res@gmail.com\"},\"addressDetails\":{\"addressLine1\":\"7M\",\"addressLine2\":\"res\",\"city\":\"chn\",\"state\":\"red\",\"zipcode\":\"603103\"}}";
			
			mvc.perform(post("/student").content(studentBody1).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isBadRequest())
							.andExpect(jsonPath("$.errors[0]").exists())
							.andExpect(content().string(containsString("firstName")))
							.andReturn();
		}
		
		@Test
		public void addStudentEmailInvalid() throws Exception {
			String studentBody1 = "{\"id\":3,\"firstName\":\"Test\",\"lastName\":\"Hansora\",\"contactDetails\":{\"email1\":\"jekil.hansora.com\",\"mobilePhone\":\"8866248620\",\"phoneNumber\":\"1234795623\",\"email2\":\"jekil@gmail.com\"},\"addressDetails\":{\"addressLine1\":\"7M\",\"addressLine2\":\"Navallur\",\"city\":\"Chennai\",\"state\":\"Tamil nadu\",\"zipcode\":\"603103\"}}";
			
			mvc.perform(post("/student").content(studentBody1).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isBadRequest())
							.andExpect(jsonPath("$.errors[0]").exists())
							.andExpect(content().string(containsString("email1")))
							.andReturn();
		}

		@Test
		public void getStudentData() throws Exception {
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			addStudent();
			String studentBody1 = "{\"id\":3,\"firstName\":\"Test\",\"lastName\":\"Hansora\",\"contactDetails\":{\"email1\":\"jekil.hansora@fd.com\",\"mobilePhone\":\"8866248620\",\"phoneNumber\":\"1234795623\",\"email2\":\"jekil@gmail.com\"},\"addressDetails\":{\"addressLine1\":\"7M\",\"addressLine2\":\"Navallur\",\"city\":\"Chennai\",\"state\":\"Tamil nadu\",\"zipcode\":\"603103\"}}";
			mvc.perform(post("/student").content(studentBody1).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isCreated())
							.andReturn();
			String uri = "/student/3";
		    mvc.perform(MockMvcRequestBuilders.get(uri)
							.accept(MediaType.APPLICATION_JSON_VALUE))
				    		.andDo(print())
							.andExpect(status().isOk())
							.andReturn();
		}
		
		@Test
		public void getAllStudentData() throws Exception {
			deleteAllStudentData();
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			addStudent();
			String uri = "/students";
		    mvc.perform(MockMvcRequestBuilders.get(uri)
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.content[0].id").value(student.getId()))
		    		.andExpect(jsonPath("$.numberOfElements").value("1"))
		    		.andReturn();
		}
		
		@Test
		public void deleteStudentData() throws Exception {
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			addStudent();
			String uri = "/student/"+student.getId();
		    mvc.perform(MockMvcRequestBuilders.get(uri)
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.id").value(student.getId()))
		    		.andReturn();
		    mvc.perform(delete(uri)
					.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
					.andExpect(status().isNoContent())
					.andExpect(jsonPath("$").value("Student Deleted"))
					.andReturn();
		    mvc.perform(MockMvcRequestBuilders.get(uri)
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isNotFound())
		    		.andReturn();
		    
		}
		
		@Test
		public void updateStudentData() throws Exception {
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			addStudent();
			String uri = "/student/"+student.getId();
		    mvc.perform(MockMvcRequestBuilders.get(uri)
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.id").value(student.getId()))
		    		.andExpect(jsonPath("$.contactDetails.email1").value(student.getContactDetails().getEmail1()))
		    		.andReturn();
		    String studentBody1 = "{\"id\":"+student.getId()+",\"firstName\":\"Test\",\"lastName\":\"Hansora\",\"contactDetails\":{\"email1\":\"test2@email1.com\",\"mobilePhone\":\"8866248620\",\"phoneNumber\":\"1234795623\",\"email2\":\"jekil@gmail.com\"},\"addressDetails\":{\"addressLine1\":\"7M\",\"addressLine2\":\"Navallur\",\"city\":\"Chennai\",\"state\":\"Tamil nadu\",\"zipcode\":\"603103\"}}";
			mvc.perform(put(uri)
					.accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(studentBody1))
		    		.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.id").value(student.getId()))
					.andExpect(jsonPath("$.contactDetails.email1").value("test2@email1.com"))
		    		.andReturn();		    
		}
		
		@Test
		public void updateStudentDataNotFound() throws Exception {
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			addStudent();
			String uri = "/student/"+student.getId();
		    mvc.perform(MockMvcRequestBuilders.get(uri)
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.id").value(student.getId()))
		    		.andExpect(jsonPath("$.contactDetails.email1").value(student.getContactDetails().getEmail1()))
		    		.andReturn();
		    String studentBody1 = "{\"id\":"+student.getId()+",\"firstName\":\"Test\",\"lastName\":\"Hansora\",\"contactDetails\":{\"email1\":\"test@email1.com\",\"mobilePhone\":\"8866248620\",\"phoneNumber\":\"1234795623\",\"email2\":\"jekil@gmail.com\"},\"addressDetails\":{\"addressLine1\":\"7M\",\"addressLine2\":\"Navallur\",\"city\":\"Chennai\",\"state\":\"Tamil nadu\",\"zipcode\":\"603103\"}}";
			uri = "/student/25252";
			mvc.perform(put(uri)
					.accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(studentBody1))
		    		.andDo(print())
					.andExpect(status().isNotFound())
					.andReturn();		    
		}
		
		
		@Test
		public void migrateAllStudentDataLegacy() throws Exception {
			deleteAllStudentData();
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", true);
			String uri = "/legacy/migrateStudents";
		    mvc.perform(MockMvcRequestBuilders.get(uri)
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isCreated())
		    		.andReturn();
		    ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
		    String url = "/students";
		    mvc.perform(MockMvcRequestBuilders.get(url)
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.numberOfElements").value("3"))
		    		.andReturn();
		}
		
		@Test
		public void deleteAllStudentData() throws Exception {
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			addStudent();
			String uri = "/students";
		    mvc.perform(delete(uri)
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isNoContent())
		    		.andExpect(jsonPath("$").value("All Student Deleted"))
		    		.andReturn();		    
		}
		
		@Test
		public void searchStudentDataFirstName() throws Exception {
			migrateAllStudentDataLegacy();
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			String uri = "/student";
		    mvc.perform(MockMvcRequestBuilders.get(uri).param("firstName", "John")
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.content[0].firstName").value("John"))
		    		.andExpect(jsonPath("$.numberOfElements").value("1"))
		    		.andReturn();
		}
		
		@Test
		public void searchStudentDataLastName() throws Exception {
			migrateAllStudentDataLegacy();
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			String uri = "/student";
		    mvc.perform(MockMvcRequestBuilders.get(uri).param("lastName", "Jackson")
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.content[0].lastName").value("Jackson"))
		    		.andExpect(jsonPath("$.numberOfElements").value("2"))
		    		.andReturn();
		}
		
		@Test
		public void searchStudentDataMigratedDate() throws Exception {
			migrateAllStudentDataLegacy();
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			String uri = "/student";
		    mvc.perform(MockMvcRequestBuilders.get(uri).param("migratedDate", "03-03-2019")
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.numberOfElements").value("3"))
		    		.andReturn();
		}
		
		@Test
		public void searchStudentDataWithAll() throws Exception {
			migrateAllStudentDataLegacy();
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", false);
			String uri = "/student";
		    mvc.perform(MockMvcRequestBuilders.get(uri)
		    		.param("migratedDate", "03-03-2019")
		    		.param("firstName", "John")
		    		.param("lastName", "Jackson")
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.numberOfElements").value("0"))
		    		.andReturn();
		}
		
		/**
		 * Legacy Controller Starts here
		 * @throws Exception
		 */
		@Test
		public void addStudentLegacy() throws Exception {
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", true);
			mvc.perform(post("/student").content(studentBody).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isServiceUnavailable())
							.andReturn();
		}
		
		@Test
		public void getStudentLegacy() throws Exception {
			mvc.perform(get("/legacy/student/101").accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.id").value("101"))							
							.andReturn();
		}
		
		@Test
		public void getStudentLegacyAll() throws Exception {
			migrateAllStudentDataLegacy();
			mvc.perform(get("/legacy/student/103").accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isOk())
							.andExpect(jsonPath("$.id").value("103"))							
							.andReturn();
		}
		
		@Test
		public void getStudentLegacyNotFound() throws Exception {
			mvc.perform(get("/legacy/student/1111").accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isNotFound())
							.andExpect(jsonPath("$.message").value("Student not found: 1111"))
							.andReturn();
		}
		
		@Test
		public void getAllStudentDataLegacy() throws Exception {
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", true);
			String uri = "/students";
		    mvc.perform(MockMvcRequestBuilders.get(uri)
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.numberOfElements").value("3"))
		    		.andReturn();
		}
		
		@Test
		public void getAllStudentDataLegacyController() throws Exception {
			String uri = "/legacy/students";
		    mvc.perform(MockMvcRequestBuilders.get(uri)
		    		.accept(MediaType.APPLICATION_JSON_VALUE))
		    		.andDo(print())
		    		.andExpect(status().isOk())
		    		.andExpect(jsonPath("$.numberOfElements").value("3"))
		    		.andReturn();
		}
		

		@Test
		public void searchStudentLegacy() throws Exception {
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", true);
			mvc.perform(get("/student").accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isServiceUnavailable())
							.andReturn();
		}
		
		@Test
		public void updateStudentLegacy() throws Exception {
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", true);
			mvc.perform(put("/student/1").content(studentBody).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isServiceUnavailable())
							.andReturn();
		}
		
		@Test
		public void deleteStudentLegacy() throws Exception {
			ReflectionTestUtils.setField(webApplicationContext.getBean(StudentServiceFactory.class), "featureToggle", true);
			mvc.perform(delete("/student/1").accept(MediaType.APPLICATION_JSON_VALUE))
							.andDo(print())
							.andExpect(status().isServiceUnavailable())
							.andReturn();
		}
}
