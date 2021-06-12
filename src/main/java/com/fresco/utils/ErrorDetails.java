package com.fresco.utils;

import java.util.Arrays;
import java.util.List;


public class ErrorDetails {
	private int status;
	private String message;
	private List<String> errors;
	/**
	 * @param status
	 * @param message
	 * @param errors
	 */
	public ErrorDetails(int status, String message, List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}
	
	/**
	 * @param status
	 * @param message
	 * @param errors
	 */
	public ErrorDetails(int status, String message, String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ErrorDetails [status=" + status + ", message=" + message + ", errors=" + errors + "]";
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
	
}
