package com.litmus7.employeemanager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;
import com.litmus7.employeemanager.services.EmployeeServices;
import com.litmus7.employeemanager.util.TextFileUtil;
import com.litmus7.employeemanager.util.ValidationUtil;

public class EmployeeController {
	
	public Response<List<Employee>> getEmployeesFromTextFile(String inputFile) {
		// TODO Auto-generated method stub
		if (inputFile != null && !inputFile.isEmpty()) {
			try {
				List<String> employeeData = TextFileUtil.readTextFile(inputFile);
				if (employeeData == null || employeeData.size() == 0)
					return new Response<List<Employee>>(404, null, "Couldn't Retrieve employee Data from file");
				List<Employee> employees = new ArrayList<Employee>();
				for (String employee : employeeData) {
					String[] employeeFields = employee.split("[$]");
					if (employeeFields.length != 7)
						continue;
					if (isValidEmployeeFields(employeeFields)) {
						Employee emp = generateEmployee(employeeFields);
						employees.add(emp);
					} else {
						continue;
					}
				}
				return new Response<List<Employee>>(200, employees, "Succesfully Retrieved employee details");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return new Response<List<Employee>>(500, null, "Error Reading File \n" + e.getMessage());
			}
		}
		return new Response<List<Employee>>(500, null, "Bad Request Check passed parameters");

	}

	public Response<Boolean> writeEmployeesToCSV(String inputFile, String outputFile) {
		// TODO Auto-generated method stub

		if (inputFile != null && !inputFile.isEmpty() && outputFile != null && !outputFile.isEmpty()) {

			Response<List<Employee>> readResponse = getEmployeesFromTextFile(inputFile);

			if (readResponse.getStatus() == 200) {
				List<String> csvStrings = new ArrayList<String>();
				for (Employee e : readResponse.getData()) {
					csvStrings
							.add(e.getId() + "," + e.getFirstName() + "," + e.getLastName() + "," + e.getMobileNumber()
									+ "," + e.getEmail() + "," + e.getJoiningDate() + "," + e.isActiveStatus());
				}
				try {
					TextFileUtil.writeCSVFile(csvStrings, outputFile);
					return new Response<Boolean>(200, true, "CSV file created successfully");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					return new Response<Boolean>(404, false, "Error in file Writing:\n" + e1.getMessage());
				}

			} else {
				return new Response<Boolean>(readResponse.getStatus(), false, readResponse.getMessage());
			}
		} else {
			return new Response<Boolean>(500, false, "Bad Request Check passed parameters");
		}

	}

	public Response<Boolean> addEmployeeToFile(String[] employeeDetails, String outputFile) {
		// TODO Auto-generated method stub
	
		if (outputFile != null && !outputFile.isEmpty() && employeeDetails != null ) {
			if (isValidEmployeeFields(employeeDetails)) {
				Employee employee = generateEmployee(employeeDetails);
				String employeeString = employee.getId() + "," + employee.getFirstName() + "," + employee.getLastName()
						+ "," + employee.getMobileNumber() + "," + employee.getEmail() + "," + employee.getJoiningDate()
						+ "," + employee.isActiveStatus();
				try {
					TextFileUtil.writeDataToCSV(employeeString, outputFile);
					return new Response<Boolean>(200, true, "Employee detail added Succesfully");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					return new Response<Boolean>(404, false, "Error in file Writing:\n" + e.getMessage());
				}
			} else {
				return new Response<Boolean>(500, false, "Invalid Employee Details ");
			} 
		}else {
			return new Response<Boolean>(500, false, "Bad Request Check passed parameters");
		}
	}
	
	//DB based controller functions 
	EmployeeServices employeeServices=new EmployeeServices();
	//create
	public Response<Boolean> createNewEmployee(String[] employeeDetails){
		if(!isValidEmployeeFields(employeeDetails))
			return new Response<Boolean>(500, false,"Invalid Employee Object");
		Employee employee=generateEmployee(employeeDetails);
		int rowsInserted = employeeServices.createEmployee(employee);
		if(rowsInserted==1)
			return new Response<Boolean>(200, true,"Created a new employee");
		else
			return new Response<Boolean>(400, false, "Failed to create employee");
	}
	//get all
	public Response<List<Employee>> getAllEmployees(){
		List<Employee> employees = employeeServices.getAllEmployees();
		if(employees==null||employees.isEmpty())
			return new Response<List<Employee>>(400, null,"Failed to fetch employees");
		return new Response<List<Employee>>(200, employees, "Succesfully fetched employee details");
	}
	//get one
	public Response<Employee> getEmployeeById(int employee_id){
		if(employee_id<=0)
			return new Response<Employee>(500, null, "Invalid Employee id");
		Employee employee=employeeServices.getEmployeeById(employee_id);
		if(employee==null)
			return new Response<Employee>(400, employee, "Failed to fetch employee with employee id= "+employee_id);
		return new Response<Employee>(200, employee, "Succesfully fetched employee");
	}
	
	//delete
	public Response<Boolean> deleteEmployee(int employee_id){
		if(employee_id<=0)
			return new Response<Boolean>(500, false, "Invalid Employee id");
		int rowsDeleted = employeeServices.deleteEmployee(employee_id);
		if(rowsDeleted==0)
			return new Response<Boolean>(400, false, "Failed to delete employee with employee id= "+employee_id);
		return new Response<Boolean>(200, true,"Succesfully deleted employee with employee id= "+employee_id);
	}
	//update
	public Response<Boolean> updateEmployee(String[] employeeDetails){
		if(!isValidEmployeeFields(employeeDetails))
			return new Response<Boolean>(500, false, "Invalid Employee Object");
		Employee employee=generateEmployee(employeeDetails);
		int rowsUpdated = employeeServices.updateEmployee(employee);
		if(rowsUpdated==0)
			return new Response<Boolean>(400, false, "Failed to update employee with employee id= "+employee.getId());
		return new Response<Boolean>(200, true,"Succesfully updated employee with employee id= "+employee.getId());
	}
	
	private boolean isValidEmployeeFields(String[] employeeFields) {
		// TODO Auto-generated method stub
		return (employeeFields.length == 7) && (ValidationUtil.isNumber(employeeFields[0]))
				&& (ValidationUtil.isName(employeeFields[1])) && (ValidationUtil.isName(employeeFields[2]))
				&& (ValidationUtil.isMobileNumber(employeeFields[3])) && (ValidationUtil.isEmail(employeeFields[4]))
				&& (ValidationUtil.isPastDate(employeeFields[5])) && (ValidationUtil.isBoolean(employeeFields[6]));
	}

	private Employee generateEmployee(String[] employeeFields) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(employeeFields[0]);
		String firstName = employeeFields[1];
		String lastName = employeeFields[2];
		String mobileNumber = employeeFields[3];
		String email = employeeFields[4];
		String joiningDate = employeeFields[5];
		boolean activeStatus = Boolean.parseBoolean(employeeFields[6]);
		return new Employee(id, firstName, lastName, mobileNumber, email, joiningDate, activeStatus);
	}
}
