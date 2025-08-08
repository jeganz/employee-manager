package com.litmus7.employeemanager.service;

import java.util.List;

import com.litmus7.employeemanager.dao.EmployeeDAO;
import com.litmus7.employeemanager.dto.Employee;

public class EmployeeService {
	private EmployeeDAO employeeDAO=new EmployeeDAO();
	public int createEmployee(Employee emp) {
		return employeeDAO.createEmployee(emp);
	}

	public List<Employee> getAllEmployees() {
		return employeeDAO.getAllEmployees();
	}

	public Employee getEmployeeById(int empId) {
		return employeeDAO.getEmployeeById(empId);
	}

	public int deleteEmployee(int empId) {
		return employeeDAO.deleteEmployee(empId);
	}

	public int updateEmployee(Employee emp) {
		return employeeDAO.updateEmployee(emp);
	}
}
