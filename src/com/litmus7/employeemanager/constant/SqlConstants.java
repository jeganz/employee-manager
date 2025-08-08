package com.litmus7.employeemanager.constant;


public class SqlConstants {
	public static final String INSERT_QUERY="INSERT INTO employee(emp_id,first_name,last_name,mobile_no,email_id,joining_date,active_status) VALUES (?,?,?,?,?,?,?);";
	public static final String SELECT_ALL_QUERY="SELECT emp_id,first_name,last_name,mobile_no,email_id,joining_date,active_status FROM employee;";
	public static final String SELECT_WITH_ID_QUERY="SELECT emp_id,first_name,last_name,mobile_no,email_id,joining_date,active_status FROM employee WHERE emp_id=?;";
	public static final String DELETE_WITH_ID_QUERY="DELETE FROM employee WHERE emp_id=?;";
	public static final String UPDATE_WITH_ID_QUERY="UPDATE employee SET first_name=?,last_name=?,mobile_no=?,email_id=?,joining_date=?,active_status=? WHERE emp_id=?;";
	
	
	public static final String EMP_ID_COLUMN_NAME="emp_id";
	public static final String FIRST_NAME_COLUMN_NAME="first_name";
	public static final String LAST_NAME_COLUMN_NAME="last_name";
	public static final String MOBILE_NO_COLUMN_NAME="mobile_no";
	public static final String EMAIL_ID_COLUMN_NAME="email_id";
	public static final String JOINING_DATE_COLUMN_NAME="joining_date";
	public static final String ACTIVE_STATUS_COLUMN_NAME="active_status";
}
