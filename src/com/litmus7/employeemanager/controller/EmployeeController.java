package com.litmus7.employeemanager.controller;

import java.util.List;

import com.litmus7.employeemanager.constant.ApplicationStatusConstant;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.exception.EmployeeAlreadyExistException;
import com.litmus7.employeemanager.exception.EmployeeNotFoundException;
import com.litmus7.employeemanager.exception.EmployeeServiceException;
import com.litmus7.employeemanager.exception.InvalidEmployeeException;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.util.ValidationUtil;

public class EmployeeController {
	// DB based controller functions
	private EmployeeService employeeServices = new EmployeeService();

	public Response<Boolean> createNewEmployee(Employee employee) {

		try {

			isValidEmployee(employee);
			int rowsInserted = employeeServices.createEmployee(employee);
			if (rowsInserted == 1)
				return new Response<Boolean>(ApplicationStatusConstant.SUCCESS, true,
						ApplicationStatusConstant.MSG_SUCCESS_CREATE);
			else
				return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
						ApplicationStatusConstant.MSG_ERROR_CREATE);
		} catch (EmployeeAlreadyExistException e) {
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, e.getMessage());
		} catch (EmployeeServiceException e) {
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
					"Service error: " + e.getMessage());
		} catch (InvalidEmployeeException e) {
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, e.getMessage());
		} catch (Exception e) {
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
					"System error: " + e.getMessage());
		}
	}

	// get all
	public Response<List<Employee>> getAllEmployees() {

		try {
			List<Employee> employees = employeeServices.getAllEmployees();

			if (employees == null || employees.isEmpty())
				return new Response<List<Employee>>(ApplicationStatusConstant.SERVER_ERROR, null,
						ApplicationStatusConstant.MSG_ERROR_SELECT_ALL);

			return new Response<List<Employee>>(ApplicationStatusConstant.SUCCESS, employees,
					ApplicationStatusConstant.MSG_SUCCESS_SELECT_ALL);
		} catch (EmployeeServiceException e) {
			return new Response<List<Employee>>(ApplicationStatusConstant.SERVER_ERROR, null,
					"Service error: " + e.getMessage());
		} catch (Exception e) {
			return new Response<List<Employee>>(ApplicationStatusConstant.SERVER_ERROR, null,
					"System error: " + e.getMessage());
		}
	}

	// get one
	public Response<Employee> getEmployeeById(int employeeId) {

		if (employeeId <= 0)
			return new Response<Employee>(ApplicationStatusConstant.BAD_REQUEST, null, "Invalid Employee id");

		try {
			Employee employee = employeeServices.getEmployeeById(employeeId);

			if (employee == null)
				return new Response<Employee>(ApplicationStatusConstant.SERVER_ERROR, employee,
						ApplicationStatusConstant.MSG_ERROR_SELECT_ONE);
			return new Response<Employee>(ApplicationStatusConstant.SUCCESS, employee,
					ApplicationStatusConstant.MSG_SUCCESS_SELECT_ONE);
		} catch (EmployeeNotFoundException e) {
			return new Response<Employee>(ApplicationStatusConstant.BAD_REQUEST, null, e.getMessage());
		} catch (EmployeeServiceException e) {
			return new Response<Employee>(ApplicationStatusConstant.SERVER_ERROR, null,
					"Service error: " + e.getMessage());
		} catch (Exception e) {
			return new Response<Employee>(ApplicationStatusConstant.SERVER_ERROR, null,
					"System error: " + e.getMessage());
		}
	}

	// delete
	public Response<Boolean> deleteEmployee(int employeeId) {

		if (employeeId <= 0)
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, "Invalid Employee id");

		try {
			int rowsDeleted = employeeServices.deleteEmployee(employeeId);
			if (rowsDeleted == 0)
				return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
						ApplicationStatusConstant.MSG_ERROR_DELETE);
			return new Response<Boolean>(ApplicationStatusConstant.SUCCESS, true,
					ApplicationStatusConstant.MSG_SUCCESS_DELETE);
		} catch (EmployeeServiceException e) {
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
					"Service error: " + e.getMessage());
		} catch (EmployeeNotFoundException e) {
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, e.getMessage());
		}catch (Exception e) {
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
					"System error: " + e.getMessage());
		}
	}

	// update
	public Response<Boolean> updateEmployee(Employee employee) {
		try {
			isValidEmployee(employee);

			int rowsUpdated = employeeServices.updateEmployee(employee);
			if (rowsUpdated == 0)
				return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
						ApplicationStatusConstant.MSG_ERROR_UPDATE);

			return new Response<Boolean>(ApplicationStatusConstant.SUCCESS, true,
					ApplicationStatusConstant.MSG_SUCCESS_UPDATE);
		} catch (EmployeeServiceException e) {
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
					"Service error: " + e.getMessage());
		}catch (InvalidEmployeeException e) {
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, e.getMessage());
		} catch (EmployeeNotFoundException e) {
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, e.getMessage());
		} catch (Exception e) {
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
					"System error: " + e.getMessage());
		}
	}

	private boolean isValidEmployee(Employee employee) throws InvalidEmployeeException {
		if (employee == null) {
			throw new InvalidEmployeeException("Employee object cannot be null");
		}
		if (employee.getId() <= 0) {
			throw new InvalidEmployeeException("Employee ID must be greater than 0");
		}
		if (!ValidationUtil.isName(employee.getFirstName())) {
			throw new InvalidEmployeeException("Invalid first name: " + employee.getFirstName());
		}
		if (!ValidationUtil.isName(employee.getLastName())) {
			throw new InvalidEmployeeException("Invalid last name: " + employee.getLastName());
		}
		if (!ValidationUtil.isMobileNumber(employee.getMobileNumber())) {
			throw new InvalidEmployeeException("Invalid mobile number: " + employee.getMobileNumber());
		}
		if (!ValidationUtil.isEmail(employee.getEmail())) {
			throw new InvalidEmployeeException("Invalid email: " + employee.getEmail());
		}
		if (!ValidationUtil.isPastDate(employee.getJoiningDate().toString())) {
			throw new InvalidEmployeeException("Joining date must be in the past: " + employee.getJoiningDate());
		}
		return true;
	}
}
