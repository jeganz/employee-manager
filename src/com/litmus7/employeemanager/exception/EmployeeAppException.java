package com.litmus7.employeemanager.exception;

public class EmployeeAppException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int statusCode;

	public EmployeeAppException(int statusCode,String message, Throwable cause) {
		super(message, cause);
		this.statusCode=statusCode;
	}

	public EmployeeAppException(int statusCode,String message) {
		super(message);
		this.statusCode=statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
