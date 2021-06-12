package com.fresco.bean;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Embeddable
@JsonInclude(Include.NON_EMPTY)
public class ContactInfo {

	@NotNull
	@Email
	private String email1;
	private String mobilePhone;
	private String phoneNumber;
	private String email2;
	
	/**
	 * 
	 */
	public ContactInfo() {
		super();
	}
	/**
	 * @param email1
	 * @param mobilePhone
	 * @param phoneNumber
	 * @param email2
	 */
	public ContactInfo(String email1, String mobilePhone, String phoneNumber, String email2) {
		super();
		this.email1 = email1;
		this.mobilePhone = mobilePhone;
		this.phoneNumber = phoneNumber;
		this.email2 = email2;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return "ContactInfo [email1=" + email1 + ", mobilePhone=" + mobilePhone + ", phoneNumber=" + phoneNumber
//				+ ", email2=" + email2 + "]";
//	}
	/**
	 * @return the email1
	 */
	public String getEmail1() {
		return email1;
	}
	/**
	 * @param email1 the email1 to set
	 */
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	/**
	 * @return the mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	/**
	 * @param mobilePhone the mobilePhone to set
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the email2
	 */
	public String getEmail2() {
		return email2;
	}
	/**
	 * @param email2 the email2 to set
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	
	
	
}
