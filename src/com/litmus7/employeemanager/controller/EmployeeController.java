package com.litmus7.employeemanager.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.employeemanager.constant.ApplicationStatusConstant;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.exception.EmployeeServiceException;
import com.litmus7.employeemanager.exception.InvalidEmployeeException;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.util.ValidationUtil;

public class EmployeeController {
	// DB based controller functions
	private EmployeeService employeeServices = new EmployeeService();
	private static Logger controllerLogger = LogManager.getLogger(EmployeeController.class);

	public Response<Boolean> createNewEmployee(Employee employee) {
		controllerLogger.info("Request to create new employee: {}", employee != null ? employee.getId() : "null");
		try {

			isValidEmployee(employee);
			int rowsInserted = employeeServices.createEmployee(employee);
			if (rowsInserted == 1) {
				controllerLogger.info("Successfully created employee: {}", employee.getId());
				return new Response<Boolean>(ApplicationStatusConstant.SUCCESS, true,
						ApplicationStatusConstant.MSG_SUCCESS_CREATE);
			} else {
				controllerLogger.warn("Failed to insert employee: {}", employee.getId());
				return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
						ApplicationStatusConstant.MSG_ERROR_CREATE);
			}
		} catch (EmployeeServiceException e) {
			if (e.getStatusCode() == ApplicationStatusConstant.DUPLICATE_ENTRY) {
				controllerLogger.warn("Employee already exists: {}", employee.getId(), e);
				return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, e.getMessage());
			} else {
				controllerLogger.error("Service error while creating employee: {}", employee.getId(), e);
				return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
						"Service error: " + e.getMessage());
			}
		} catch (InvalidEmployeeException e) {
			controllerLogger.warn("Invalid employee data: {}", employee, e);
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, e.getMessage());
		} catch (Exception e) {
			controllerLogger.error("System error while creating employee: {}",
					employee != null ? employee.getId() : "null", e);
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
					"System error: " + e.getMessage());
		}
	}

	// get all
	public Response<List<Employee>> getAllEmployees() {
		controllerLogger.info("Request to fetch all employees");
		try {
			List<Employee> employees = employeeServices.getAllEmployees();

			if (employees == null || employees.isEmpty()) {
				controllerLogger.warn("No employees found in database");
				return new Response<List<Employee>>(ApplicationStatusConstant.SERVER_ERROR, null,
						ApplicationStatusConstant.MSG_ERROR_SELECT_ALL);
			}
			controllerLogger.info("Fetched employees - Count: {}", employees.size());
			return new Response<List<Employee>>(ApplicationStatusConstant.SUCCESS, employees,
					ApplicationStatusConstant.MSG_SUCCESS_SELECT_ALL);
		} catch (EmployeeServiceException e) {
			controllerLogger.error("Service error while fetching employees", e);
			return new Response<List<Employee>>(ApplicationStatusConstant.SERVER_ERROR, null,
					"Service error: " + e.getMessage());
		} catch (Exception e) {
			controllerLogger.error("System error while fetching employees", e);
			return new Response<List<Employee>>(ApplicationStatusConstant.SERVER_ERROR, null,
					"System error: " + e.getMessage());
		}
	}

	// get one
	public Response<Employee> getEmployeeById(int employeeId) {
		controllerLogger.info("Request to fetch employee with ID: {}", employeeId);
		if (employeeId <= 0) {
			controllerLogger.warn("Invalid employee ID: {}", employeeId);
			return new Response<Employee>(ApplicationStatusConstant.BAD_REQUEST, null, "Invalid Employee id");
		}
		try {
			Employee employee = employeeServices.getEmployeeById(employeeId);

			if (employee == null) {
				controllerLogger.warn("No employee found with ID: {}", employeeId);
				return new Response<Employee>(ApplicationStatusConstant.SERVER_ERROR, employee,
						ApplicationStatusConstant.MSG_ERROR_SELECT_ONE);
			}
			controllerLogger.info("Employee found: {}", employeeId);
			return new Response<Employee>(ApplicationStatusConstant.SUCCESS, employee,
					ApplicationStatusConstant.MSG_SUCCESS_SELECT_ONE);
		} catch (EmployeeServiceException e) {
			if(e.getStatusCode()==ApplicationStatusConstant.NOT_FOUND) {
			controllerLogger.warn("Employee not found: {}", employeeId, e);
			return new Response<Employee>(ApplicationStatusConstant.BAD_REQUEST, null, e.getMessage());
			}else {
			controllerLogger.error("Service error while fetching employee with ID: {}", employeeId, e);
			return new Response<Employee>(ApplicationStatusConstant.SERVER_ERROR, null,
					"Service error: " + e.getMessage());
			}
		} catch (Exception e) {
			controllerLogger.error("System error while fetching employee with ID: {}", employeeId, e);
			return new Response<Employee>(ApplicationStatusConstant.SERVER_ERROR, null,
					"System error: " + e.getMessage());
		}
	}

	// delete
	public Response<Boolean> deleteEmployee(int employeeId) {
		controllerLogger.info("Request to delete employee with ID: {}", employeeId);
		if (employeeId <= 0) {
			controllerLogger.warn("Invalid employee ID: {}", employeeId);
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, "Invalid Employee id");
		}

		try {
			int rowsDeleted = employeeServices.deleteEmployee(employeeId);
			if (rowsDeleted == 0) {
				controllerLogger.warn("No employee deleted for ID: {}", employeeId);
				return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
						ApplicationStatusConstant.MSG_ERROR_DELETE);
			}
			controllerLogger.info("Employee with ID: {} deleted successfully", employeeId);
			return new Response<Boolean>(ApplicationStatusConstant.SUCCESS, true,
					ApplicationStatusConstant.MSG_SUCCESS_DELETE);
		} catch (EmployeeServiceException e) {
			if(e.getStatusCode()==ApplicationStatusConstant.NOT_FOUND) {
			controllerLogger.warn("Attempt to delete non-existent employee with ID: {}", employeeId, e);
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, e.getMessage());
			}else {
			controllerLogger.error("Service error while deleting employee with ID: {}", employeeId, e);
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
					"Service error: " + e.getMessage());
			}
		} catch (Exception e) {
			controllerLogger.error("System error while deleting employee with ID: {}", employeeId, e);
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
					"System error: " + e.getMessage());
		}
	}

	// update
	public Response<Boolean> updateEmployee(Employee employee) {
		controllerLogger.info("Request to update employee: {}", employee != null ? employee.getId() : "null");
		try {
			isValidEmployee(employee);

			int rowsUpdated = employeeServices.updateEmployee(employee);
			if (rowsUpdated == 0) {
				controllerLogger.warn("No records updated for employee: {}", employee.getId());
				return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
						ApplicationStatusConstant.MSG_ERROR_UPDATE);
			}
			controllerLogger.info("Employee updated successfully: {}", employee.getId());
			return new Response<Boolean>(ApplicationStatusConstant.SUCCESS, true,
					ApplicationStatusConstant.MSG_SUCCESS_UPDATE);
		} catch (EmployeeServiceException e) {
			if(e.getStatusCode()==ApplicationStatusConstant.NOT_FOUND) {
				controllerLogger.warn("Attempt to update non-existent employee: {}", employee.getId(), e);
				return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, e.getMessage());
			}else {
			controllerLogger.error("Service error while updating employee: {}", employee.getId(), e);
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false,
					"Service error: " + e.getMessage());
			}
		} catch (InvalidEmployeeException e) {
			controllerLogger.warn("Invalid employee data for update: {}", employee, e);
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, e.getMessage());
		
		} catch (Exception e) {
			controllerLogger.error("System error while updating employee: {}", employee.getId(), e);
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
