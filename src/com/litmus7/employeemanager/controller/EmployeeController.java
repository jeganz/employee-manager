package com.litmus7.employeemanager.controller;

import java.util.List;

import com.litmus7.employeemanager.constant.ApplicationStatusConstant;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.util.ValidationUtil;

public class EmployeeController {
	//DB based controller functions 
	private EmployeeService employeeServices=new EmployeeService();
	
	public Response<Boolean> createNewEmployee(Employee employee){
		
		if(!isValidEmployee(employee))
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false,"Invalid Employee Object");
		
		
		int rowsInserted = employeeServices.createEmployee(employee);
		if(rowsInserted==1)
			return new Response<Boolean>(ApplicationStatusConstant.SUCCESS, true,ApplicationStatusConstant.MSG_SUCCESS_CREATE);
		else
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false, ApplicationStatusConstant.MSG_ERROR_CREATE);
	}
	//get all
	public Response<List<Employee>> getAllEmployees(){
		
		List<Employee> employees = employeeServices.getAllEmployees();
		
		if(employees==null||employees.isEmpty())
			return new Response<List<Employee>>(ApplicationStatusConstant.SERVER_ERROR, null,ApplicationStatusConstant.MSG_ERROR_SELECT_ALL);
		
		return new Response<List<Employee>>(ApplicationStatusConstant.SUCCESS, employees, ApplicationStatusConstant.MSG_SUCCESS_SELECT_ALL);
	}
	//get one
	public Response<Employee> getEmployeeById(int employeeId){
		
		if(employeeId<=0)
			return new Response<Employee>(ApplicationStatusConstant.BAD_REQUEST, null, "Invalid Employee id");
		
		Employee employee=employeeServices.getEmployeeById(employeeId);
		
		if(employee==null)
			return new Response<Employee>(ApplicationStatusConstant.SERVER_ERROR, employee, ApplicationStatusConstant.MSG_ERROR_SELECT_ONE);
		
		return new Response<Employee>(ApplicationStatusConstant.SUCCESS, employee, ApplicationStatusConstant.MSG_SUCCESS_SELECT_ONE);
	}
	
	//delete
	public Response<Boolean> deleteEmployee(int employeeId){
		
		if(employeeId<=0)
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, "Invalid Employee id");
		
		int rowsDeleted = employeeServices.deleteEmployee(employeeId);
		if(rowsDeleted==0)
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false, ApplicationStatusConstant.MSG_ERROR_DELETE);
		
		return new Response<Boolean>(ApplicationStatusConstant.SUCCESS, true,ApplicationStatusConstant.MSG_SUCCESS_DELETE);
	}
	//update
	public Response<Boolean> updateEmployee(Employee employee){
		
		if(!isValidEmployee(employee))
			return new Response<Boolean>(ApplicationStatusConstant.BAD_REQUEST, false, "Invalid Employee Object");
		
		int rowsUpdated = employeeServices.updateEmployee(employee);
		if(rowsUpdated==0)
			return new Response<Boolean>(ApplicationStatusConstant.SERVER_ERROR, false, ApplicationStatusConstant.MSG_ERROR_UPDATE);
		
		return new Response<Boolean>(ApplicationStatusConstant.SUCCESS, true,ApplicationStatusConstant.MSG_SUCCESS_UPDATE);
	}
	
	private boolean isValidEmployee(Employee employee) {
        return employee != null
                && employee.getId() > 0
                && ValidationUtil.isName(employee.getFirstName())
                && ValidationUtil.isName(employee.getLastName())
                && ValidationUtil.isMobileNumber(employee.getMobileNumber())
                && ValidationUtil.isEmail(employee.getEmail())
                && ValidationUtil.isPastDate(employee.getJoiningDate().toString());
        }
}
