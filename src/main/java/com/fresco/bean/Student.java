package com.fresco.bean;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@JsonInclude(Include.NON_EMPTY)
public class Student {
	 
	@Id
	private long id;
	
	@NotNull
	@Size(min=2, message="First Name should have atleast 2 characters")
	private String firstName;
	
	@NotNull
	@Size(min=2, message="Last Name should have atleast 2 characters")
	private String lastName;
	
	@Embedded
	@NotNull
	@Valid
	private ContactInfo contactDetails;
	
	@Embedded
	@NotNull
	@Valid
	private AddressInfo addressDetails;
	private long createdDate;
	private long migratedDate;
	
	public Student() {
		super();
	}

	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param contactDetails
	 * @param addressDetails
	 * @param createdDate
	 * @param migratedDate
	 */
	public Student(long id, String firstName, String lastName, ContactInfo contactDetails, AddressInfo addressDetails,
			long createdDate, long migratedDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactDetails = contactDetails;
		this.addressDetails = addressDetails;
		this.createdDate = createdDate;
		this.migratedDate = migratedDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", contactDetails="
				+ contactDetails + ", addressDetails=" + addressDetails + ", createdDate=" + createdDate
				+ ", migratedDate=" + migratedDate + "]";
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the contactDetails
	 */
	public ContactInfo getContactDetails() {
		return contactDetails;
	}

	/**
	 * @param contactDetails the contactDetails to set
	 */
	public void setContactDetails(ContactInfo contactDetails) {
		this.contactDetails = contactDetails;
	}

	/**
	 * @return the addressDetails
	 */
	public AddressInfo getAddressDetails() {
		return addressDetails;
	}

	/**
	 * @param addressDetails the addressDetails to set
	 */
	public void setAddressDetails(AddressInfo addressDetails) {
		this.addressDetails = addressDetails;
	}

	/**
	 * @return the createdDate
	 */
	public long getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the migratedDate
	 */
	public long getMigratedDate() {
		return migratedDate;
	}

	/**
	 * @param migratedDate the migratedDate to set
	 */
	public void setMigratedDate(long migratedDate) {
		this.migratedDate = migratedDate;
	}
	
	
}
