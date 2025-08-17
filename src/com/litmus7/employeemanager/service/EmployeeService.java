package com.litmus7.employeemanager.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.employeemanager.constant.ApplicationStatusConstant;
import com.litmus7.employeemanager.dao.EmployeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exception.EmployeeDAOException;
import com.litmus7.employeemanager.exception.EmployeeServiceException;

public class EmployeeService {
	private static final Logger serviceLogger = LogManager.getLogger(EmployeeService.class);
	private EmployeeDAO employeeDAO = new EmployeeDAO();

	public int createEmployee(Employee employee) throws EmployeeServiceException {
		serviceLogger.info("Service: Creating employee with ID: {}", employee != null ? employee.getId() : "null");

		try {
			if (getEmployeeById(employee.getId()) != null) {
				serviceLogger.warn("Employee with ID {} already exists", employee.getId());
				throw new EmployeeServiceException(ApplicationStatusConstant.DUPLICATE_ENTRY,
						"Employee with ID :" + employee.getId() + " already exists");
			}
			return 0;

		} catch (EmployeeServiceException e) {
			if (e.getStatusCode() == ApplicationStatusConstant.NOT_FOUND) {
				serviceLogger.debug("Employee ID {} not found, proceeding with creation", employee.getId());
				try {
					int rows = employeeDAO.createEmployee(employee);
					serviceLogger.info("Employee created successfully: {}", employee.getId());
					return rows;
				} catch (EmployeeDAOException e1) {
					serviceLogger.error("DAO error while creating employee: {}", employee.getId(), e1);
					throw new EmployeeServiceException(ApplicationStatusConstant.SERVER_ERROR,
							"Service layer failed to create employee ", e1);
				}
			}
			throw e;
		}
	}

	public List<Employee> getAllEmployees() throws EmployeeServiceException {
		serviceLogger.info("Service: Fetching all employees");
		try {
			List<Employee> employees = employeeDAO.getAllEmployees();
			serviceLogger.debug("Fetched {} employees from DAO", employees != null ? employees.size() : 0);
			return employees;
		} catch (EmployeeDAOException e) {
			serviceLogger.error("DAO error while fetching all employees", e);
			throw new EmployeeServiceException(ApplicationStatusConstant.SERVER_ERROR,
					"Service layer failed to fetch all employees", e);
		}
	}

	public Employee getEmployeeById(int employeeId) throws EmployeeServiceException {
		serviceLogger.info("Service: Fetching employee by ID: {}", employeeId);
		try {
			Employee employee = employeeDAO.getEmployeeById(employeeId);
			serviceLogger.debug("Employee found: {}", employeeId);
			return employee;
		} catch (EmployeeDAOException e) {
			serviceLogger.error("DAO error while fetching employee: {}", employeeId, e);
			throw new EmployeeServiceException(e.getStatusCode(), "Service failure:" + e.getMessage(), e);
		}
	}

	public int deleteEmployee(int employeeId) throws EmployeeServiceException {
		serviceLogger.info("Service: Deleting employee with ID: {}", employeeId);
		try {
			if (getEmployeeById(employeeId) != null) {
				int rows = employeeDAO.deleteEmployee(employeeId);
				serviceLogger.info("Employee deleted successfully: {}", employeeId);
				return rows;
			} else {
				serviceLogger.warn("Cannot delete, employee not found: {}", employeeId);
				throw new EmployeeServiceException(ApplicationStatusConstant.NOT_FOUND,
						"Can't find employee with ID " + employeeId);
			}
		} catch (EmployeeDAOException e) {
			serviceLogger.error("DAO error while deleting employee: {}", employeeId, e);
			throw new EmployeeServiceException(ApplicationStatusConstant.SERVER_ERROR,
					"Service layer failed to delete employee", e);
		}
	}

	public int updateEmployee(Employee employee) throws EmployeeServiceException {
		serviceLogger.info("Service: Updating employee with ID: {}", employee != null ? employee.getId() : "null");
		try {
			if (getEmployeeById(employee.getId()) != null) {
				int rows = employeeDAO.updateEmployee(employee);
				serviceLogger.info("Employee updated successfully: {}", employee.getId());
				return rows;
			} else {
				serviceLogger.warn("Cannot update, employee not found: {}", employee.getId());
				throw new EmployeeServiceException(ApplicationStatusConstant.NOT_FOUND,
						"Can't find employee with ID " + employee.getId());
			}
		} catch (EmployeeDAOException e) {
			serviceLogger.error("DAO error while updating employee: {}", employee.getId(), e);
			throw new EmployeeServiceException(ApplicationStatusConstant.SERVER_ERROR,"Service layer failed to update employee", e);
		}
	}
}
