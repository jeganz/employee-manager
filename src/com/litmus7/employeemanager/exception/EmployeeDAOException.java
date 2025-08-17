package com.litmus7.employeemanager.exception;

public class EmployeeDAOException extends EmployeeAppException {

	private static final long serialVersionUID = 1L;
	public EmployeeDAOException(int statusCode, String message, Throwable cause) {
		super(statusCode, message, cause);
	}

	public EmployeeDAOException(int statusCode, String message) {
		super(statusCode, message);
	}
}
