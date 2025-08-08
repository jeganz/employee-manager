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
					Employee employee=EmployeeManagerApp.getEmployeeFromUser(bufferedReader);
					Response<Boolean> response = controller.createNewEmployee(employee);
					System.out.println(response.getMessage());
					break;
				case "2":
					Response<List<Employee>> employeesResponse=controller.getAllEmployees();
					if (employeesResponse.getStatus() == 200) {
						System.out.println("Employee Details : ");
						System.out.printf("%-5s| %-10s | %-10s| %-10s| %-30s | %-10s| %-10s\n", "ID", "First Name",
								"Last Name", "Mobile", "Email", "Join Date", "Active Status");
						System.out.println(
								"------------------------------------------------------------------------------------------------");
						for (Employee e : employeesResponse.getData()) {
							System.out.printf("%-5s| %-10s | %-10s| %-10s| %-30s | %-10s| %-10s\n", e.getId() + " ",
									e.getFirstName(), e.getLastName(), e.getMobileNumber(), e.getEmail(),
									e.getJoiningDate() + " ", e.isActiveStatus() + "");
						}
					} else {
						System.out.println(employeesResponse.getMessage());
					}
					break;
				case "3":
					System.out.println("Enter id of required employee: ");
					Integer employeeId=Integer.parseInt(bufferedReader.readLine());
					Response<Employee> employeeResponse = controller.getEmployeeById(employeeId);
					if (employeeResponse.getStatus()==200) {
						Employee employee1=employeeResponse.getData();
						System.out.printf("%-5s| %-10s | %-10s| %-10s| %-30s | %-10s| %-10s\n", employee1.getId() + " ",
								employee1.getFirstName(), employee1.getLastName(), employee1.getMobileNumber(), employee1.getEmail(),
								employee1.getJoiningDate() + " ", employee1.isActiveStatus() + "");
					}else {
						System.out.println(employeeResponse.getMessage());
					}
					
					break;
				case "4":
					System.out.println("Enter id of required employee: ");
					employeeId=Integer.parseInt(bufferedReader.readLine());
					response=controller.deleteEmployee(employeeId);
					System.out.println(response.getMessage());
					break;
				case "5":
					employee=EmployeeManagerApp.getEmployeeFromUser(bufferedReader);
					response=controller.updateEmployee(employee);
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
	private static Employee getEmployeeFromUser(BufferedReader bufferedReader) throws IOException {
		 System.out.println("Enter Details of employee:");
	        System.out.print("Id: ");
	        int id = Integer.parseInt(bufferedReader.readLine());

	        System.out.print("First Name: ");
	        String firstName = bufferedReader.readLine();

	        System.out.print("Last Name: ");
	        String lastName = bufferedReader.readLine();

	        System.out.print("Mobile Number: ");
	        String mobileNumber = bufferedReader.readLine();

	        System.out.print("Email: ");
	        String email = bufferedReader.readLine();

	        System.out.print("Joining Date (YYYY-MM-DD): ");
	        String joiningDate = bufferedReader.readLine();

	        System.out.print("Active status (true/false): ");
	        boolean activeStatus = Boolean.parseBoolean(bufferedReader.readLine());

	        return new Employee(id, firstName, lastName, mobileNumber, email, joiningDate, activeStatus);
	    
		
	}

}
