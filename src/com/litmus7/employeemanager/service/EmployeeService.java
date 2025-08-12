package com.litmus7.employeemanager.service;

import java.util.List;

import com.litmus7.employeemanager.dao.EmployeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.exception.EmployeeAlreadyExistException;
import com.litmus7.employeemanager.exception.EmployeeDAOException;
import com.litmus7.employeemanager.exception.EmployeeNotFoundException;
import com.litmus7.employeemanager.exception.EmployeeServiceException;

public class EmployeeService {
	private EmployeeDAO employeeDAO=new EmployeeDAO();
	public int createEmployee(Employee employee) throws EmployeeAlreadyExistException, EmployeeServiceException {
		try {
			if(getEmployeeById(employee.getId())!=null)
				throw new EmployeeAlreadyExistException("Employee with ID :"+employee.getId()+" already exist");
			return 0;
			
		} catch (EmployeeNotFoundException e) {
			// TODO Auto-generated catch block
			try {
				return employeeDAO.createEmployee(employee);
			} catch (EmployeeDAOException e1) {
				throw new EmployeeServiceException("Service layer failed to create employee ", e);
			}
		} catch (EmployeeServiceException e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

	public List<Employee> getAllEmployees() throws EmployeeServiceException {
		try {
			return employeeDAO.getAllEmployees();
		} catch (EmployeeDAOException e) {
			throw new EmployeeServiceException("Service layer failed to fetch all employee ", e);
		}
	}

	public Employee getEmployeeById(int employeeId) throws EmployeeNotFoundException, EmployeeServiceException {
		try {
			return employeeDAO.getEmployeeById(employeeId);
		} catch (EmployeeDAOException e) {
			throw new EmployeeServiceException("Service layer failed to fetch employee ", e);
		} catch (EmployeeNotFoundException e) {
			throw e;
		}
	}

	public int deleteEmployee(int employeeId) throws EmployeeServiceException, EmployeeNotFoundException {
		try {
			if(getEmployeeById(employeeId)!=null)
				return employeeDAO.deleteEmployee(employeeId);
			else {
				throw new EmployeeNotFoundException("Can't find employee with ID "+employeeId);
			}
		} catch (EmployeeDAOException e) {
			throw new EmployeeServiceException("Service layer failed to delete employee ", e);
		}
	}

	public int updateEmployee(Employee employee) throws EmployeeServiceException, EmployeeNotFoundException {
		try {
			if(getEmployeeById(employee.getId())!=null)
				return employeeDAO.updateEmployee(employee);
			else {
				throw new EmployeeNotFoundException("Can't find employee with ID "+employee.getId());
			}
		} catch (EmployeeDAOException e) {
			throw new EmployeeServiceException("Service layer failed to update employee ", e);
		}
	}
}
