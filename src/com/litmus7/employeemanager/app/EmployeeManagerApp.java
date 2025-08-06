package com.litmus7.employeemanager.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.Response;

public class EmployeeManagerApp {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

			EmployeeController controller = new EmployeeController();
			while (true) {
				System.out.println("\nEnter Choice :\n1.Add a new employee details"
						+ "\n2.View all employee details" + "\n3.Search employee by id"
						+ "\n4.Delete an employee"
						+ "\n5.Update an employee detail"
						+ "\n6.Exit");
				String choice = bufferedReader.readLine();

				switch (choice) {
				case "1":
					String[] employeeDetails=EmployeeManagerApp.getEmployeeFromUser(bufferedReader);
					Response<Boolean> response = controller.createNewEmployee(employeeDetails);
					System.out.println(response.getMessage());
					break;
				case "2":
					Response<List<Employee>> getAllEmployeeResponse=controller.getAllEmployees();
					if (getAllEmployeeResponse.getStatus() == 200) {
						System.out.println("Employee Details : ");
						System.out.printf("%-5s| %-10s | %-10s| %-10s| %-30s | %-10s| %-10s\n", "ID", "First Name",
								"Last Name", "Mobile", "Email", "Join Date", "Active Status");
						System.out.println(
								"------------------------------------------------------------------------------------------------");
						for (Employee e : getAllEmployeeResponse.getData()) {
							System.out.printf("%-5s| %-10s | %-10s| %-10s| %-30s | %-10s| %-10s\n", e.getId() + " ",
									e.getFirstName(), e.getLastName(), e.getMobileNumber(), e.getEmail(),
									e.getJoiningDate() + " ", e.isActiveStatus() + "");
						}
					} else {
						System.out.println(getAllEmployeeResponse.getMessage());
					}
					break;
				case "3":
					System.out.println("Enter id of required employee: ");
					Integer employeeId=Integer.parseInt(bufferedReader.readLine());
					Response<Employee> getEmployeeResponse = controller.getEmployeeById(employeeId);
					if (getEmployeeResponse.getStatus()==200) {
						Employee employee=getEmployeeResponse.getData();
						System.out.printf("%-5s| %-10s | %-10s| %-10s| %-30s | %-10s| %-10s\n", employee.getId() + " ",
								employee.getFirstName(), employee.getLastName(), employee.getMobileNumber(), employee.getEmail(),
								employee.getJoiningDate() + " ", employee.isActiveStatus() + "");
					}else {
						System.out.println(getEmployeeResponse.getMessage());
					}
					
					break;
				case "4":
					System.out.println("Enter id of required employee: ");
					employeeId=Integer.parseInt(bufferedReader.readLine());
					response=controller.deleteEmployee(employeeId);
					System.out.println(response.getMessage());
					break;
				case "5":
					employeeDetails=EmployeeManagerApp.getEmployeeFromUser(bufferedReader);
					response=controller.updateEmployee(employeeDetails);
					System.out.println(response.getMessage());
					break;
				default:
					System.out.println("Terminating program..");
					return;
				}

			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error in reading Input: "+e1.getMessage());
		}
	}
	private static String[] getEmployeeFromUser(BufferedReader bufferedReader) throws IOException {
		String[] employeeDetails=new String[7];
		System.out.println("Enter Details of employee:");
		System.out.println("Id:");
		employeeDetails[0] = bufferedReader.readLine();
		System.out.println("First Name:");
		employeeDetails[1] = bufferedReader.readLine();
		System.out.println("Last Name:");
		employeeDetails[2] = bufferedReader.readLine();
		System.out.println("Mobile Number:");
		employeeDetails[3] = bufferedReader.readLine();
		System.out.println("Email id:");
		employeeDetails[4] = bufferedReader.readLine();
		System.out.println("Joining Date(YYYY-MM-DD):");
		employeeDetails[5] = bufferedReader.readLine();
		System.out.println("Active status (true/false):");
		employeeDetails[6] = bufferedReader.readLine();
		return employeeDetails;
		
	}

}
