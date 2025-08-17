package com.litmus7.employeemanager.exception;

public class EmployeeServiceException extends EmployeeAppException {
	private static final long serialVersionUID = 1L;

	public EmployeeServiceException(int statusCode, String message, Throwable cause) {
		super(statusCode, message, cause);
	}

	public EmployeeServiceException(int statusCode, String message) {
		super(statusCode, message);
	}
	

}
