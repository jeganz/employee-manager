package com.litmus7.employeemanager.service;

import java.util.List;

import com.litmus7.employeemanager.dao.EmployeeDAO;
import com.litmus7.employeemanager.dto.Employee;

public class EmployeeService {
	private EmployeeDAO employeeDAO=new EmployeeDAO();
	public int createEmployee(Employee employee) {
		//check if id exists
		if(getEmployeeById(employee.getId())!=null)
			return 0;
		return employeeDAO.createEmployee(employee);
	}

	public List<Employee> getAllEmployees() {
		return employeeDAO.getAllEmployees();
	}

	public Employee getEmployeeById(int employeeId) {
		return employeeDAO.getEmployeeById(employeeId);
	}

	public int deleteEmployee(int employeeId) {
		return employeeDAO.deleteEmployee(employeeId);
	}

	public int updateEmployee(Employee employee) {
		return employeeDAO.updateEmployee(employee);
	}
}
