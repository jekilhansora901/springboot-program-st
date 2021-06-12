package com.fresco.bean;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Embeddable
@JsonInclude(Include.NON_EMPTY)
public class AddressInfo {

	@NotNull
	private String addressLine1;
	private String addressLine2;
	@NotNull
	private String city;
	@NotNull
	private String state;
	@NotNull
	private String zipcode;
	private String addressNote;
	
	/**
	 * 
	 */
	public AddressInfo() {
		super();
	}
	/**
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param state
	 * @param zipcode
	 * @param addressNote
	 */
	public AddressInfo(String addressLine1, String addressLine2, String city, String state, String zipcode,
			String addressNote) {
		super();
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.addressNote = addressNote;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return "AddressInfo [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city
//				+ ", state=" + state + ", zipcode=" + zipcode + ", addressNote=" + addressNote + "]";
//	}
	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}
	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}
	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return the addressNote
	 */
	public String getAddressNote() {
		return addressNote;
	}
	/**
	 * @param addressNote the addressNote to set
	 */
	public void setAddressNote(String addressNote) {
		this.addressNote = addressNote;
	}
	
	
	
}
