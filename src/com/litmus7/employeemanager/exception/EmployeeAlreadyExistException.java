package com.litmus7.employeemanager.exception;

public class EmployeeAlreadyExistException extends Exception{
	private static final long serialVersionUID = 1L;

	public EmployeeAlreadyExistException(String message) {
		super(message);
	}
}
