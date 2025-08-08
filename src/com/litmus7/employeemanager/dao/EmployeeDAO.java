package com.litmus7.employeemanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.employeemanager.constant.SqlConstants;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.DBConnectionUtil;

public class EmployeeDAO {

	public int createEmployee(Employee emp) {
		try (Connection connection = DBConnectionUtil.getConnection()) {

			PreparedStatement insertQueryStatement = connection.prepareStatement(SqlConstants.INSERT_QUERY);
			insertQueryStatement.setInt(1, emp.getId());
			insertQueryStatement.setString(2, emp.getFirstName());
			insertQueryStatement.setString(3, emp.getLastName());
			insertQueryStatement.setString(4, emp.getMobileNumber());
			insertQueryStatement.setString(5, emp.getEmail());
			insertQueryStatement.setString(6, emp.getJoiningDate().toString());
			insertQueryStatement.setBoolean(7, emp.isActiveStatus());

			return insertQueryStatement.executeUpdate();

		} catch (SQLException e) {
			return 0;
		}
	}

	public List<Employee> getAllEmployees() {
		try (Connection connection = DBConnectionUtil.getConnection()) {
			PreparedStatement selectQuery = connection.prepareStatement(SqlConstants.SELECT_ALL_QUERY);
			ResultSet resultSet = selectQuery.executeQuery();
			List<Employee> employees = new ArrayList<Employee>();
			while (resultSet.next()) {
				employees.add(new Employee(resultSet.getInt(SqlConstants.EMP_ID_COLUMN_NAME), resultSet.getString(SqlConstants.FIRST_NAME_COLUMN_NAME),
						resultSet.getString(SqlConstants.LAST_NAME_COLUMN_NAME), resultSet.getString(SqlConstants.MOBILE_NO_COLUMN_NAME),
						resultSet.getString(SqlConstants.EMAIL_ID_COLUMN_NAME), resultSet.getDate(SqlConstants.JOINING_DATE_COLUMN_NAME).toString(),
						resultSet.getBoolean(SqlConstants.ACTIVE_STATUS_COLUMN_NAME)));
			}
			return employees;
		} catch (SQLException e) {
			return null;
		}
	}

	public Employee getEmployeeById(int empId) {
		try (Connection connection = DBConnectionUtil.getConnection()) {
			PreparedStatement selectQuery = connection.prepareStatement(SqlConstants.SELECT_WITH_ID_QUERY);
			selectQuery.setInt(1, empId);
			ResultSet resultSet = selectQuery.executeQuery();
			while (resultSet.next()) {
				return new Employee(resultSet.getInt(SqlConstants.EMP_ID_COLUMN_NAME), resultSet.getString(SqlConstants.FIRST_NAME_COLUMN_NAME),
						resultSet.getString(SqlConstants.LAST_NAME_COLUMN_NAME), resultSet.getString(SqlConstants.MOBILE_NO_COLUMN_NAME),
						resultSet.getString(SqlConstants.EMAIL_ID_COLUMN_NAME), resultSet.getDate(SqlConstants.JOINING_DATE_COLUMN_NAME).toString(),
						resultSet.getBoolean(SqlConstants.ACTIVE_STATUS_COLUMN_NAME));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return null;
	}

	public int deleteEmployee(int empId) {
		try (Connection connection = DBConnectionUtil.getConnection()) {
			PreparedStatement deleteQuery = connection.prepareStatement(SqlConstants.DELETE_WITH_ID_QUERY);
			deleteQuery.setInt(1, empId);
			return deleteQuery.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

	public int updateEmployee(Employee emp) {
		try (Connection connection = DBConnectionUtil.getConnection()) {
			PreparedStatement updateQuery = connection.prepareStatement(SqlConstants.UPDATE_WITH_ID_QUERY);
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
