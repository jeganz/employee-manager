package com.litmus7.employeemanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.DBConnectionUtil;

public class EmployeeDAO {

	public int createEmployee(Employee emp) {
		try (Connection connection = DBConnectionUtil.getConnection()) {

			PreparedStatement insertQuery = connection.prepareStatement(
					"INSERT INTO employee(emp_id,first_name,last_name,mobile_no,email_id,joining_date,active_status) VALUES (?,?,?,?,?,?,?);");
			insertQuery.setInt(1, emp.getId());
			insertQuery.setString(2, emp.getFirstName());
			insertQuery.setString(3, emp.getLastName());
			insertQuery.setString(4, emp.getMobileNumber());
			insertQuery.setString(5, emp.getEmail());
			insertQuery.setString(6, emp.getJoiningDate().toString());
			insertQuery.setBoolean(7, emp.isActiveStatus());

			return insertQuery.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

	public List<Employee> getAllEmployees() {
		try (Connection connection = DBConnectionUtil.getConnection()) {
			PreparedStatement selectQuery = connection.prepareStatement(
					"SELECT emp_id,first_name,last_name,mobile_no,email_id,joining_date,active_status FROM employee;");
			ResultSet resultSet = selectQuery.executeQuery();
			List<Employee> employees = new ArrayList<Employee>();
			while (resultSet.next()) {
				employees.add(new Employee(resultSet.getInt("emp_id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("mobile_no"),
						resultSet.getString("email_id"), resultSet.getDate("joining_date").toString(),
						resultSet.getBoolean("active_status")));
			}
			return employees;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public Employee getEmployeeById(int empId) {
		try (Connection connection = DBConnectionUtil.getConnection()) {
			PreparedStatement selectQuery = connection.prepareStatement(
					"SELECT emp_id,first_name,last_name,mobile_no,email_id,joining_date,active_status FROM employee WHERE emp_id=?;");
			selectQuery.setInt(1, empId);
			ResultSet resultSet = selectQuery.executeQuery();
			while (resultSet.next()) {
				return new Employee(resultSet.getInt("emp_id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("mobile_no"),
						resultSet.getString("email_id"), resultSet.getDate("joining_date").toString(),
						resultSet.getBoolean("active_status"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return null;
	}

	public int deleteEmployee(int empId) {
		try (Connection connection = DBConnectionUtil.getConnection()) {
			PreparedStatement deleteQuery = connection.prepareStatement("DELETE FROM employee WHERE emp_id=?;");
			deleteQuery.setInt(1, empId);
			return deleteQuery.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

	public int updateEmployee(Employee emp) {
		try (Connection connection = DBConnectionUtil.getConnection()) {
			PreparedStatement updateQuery = connection
					.prepareStatement("UPDATE employee SET " + "first_name=?,last_name=?,mobile_no=?,email_id=?,"
							+ "joining_date=?,active_status=? WHERE emp_id=?;");
			updateQuery.setInt(7, emp.getId());
			updateQuery.setString(1, emp.getFirstName());
			updateQuery.setString(2, emp.getLastName());
			updateQuery.setString(3, emp.getMobileNumber());
			updateQuery.setString(4, emp.getEmail());
			updateQuery.setString(5, emp.getJoiningDate().toString());
			updateQuery.setBoolean(6, emp.isActiveStatus());

			return updateQuery.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

}
