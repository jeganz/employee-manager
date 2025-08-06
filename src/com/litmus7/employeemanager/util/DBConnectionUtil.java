package com.litmus7.employeemanager.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionUtil {
	private static String connection_url;
	private static String username;
	private static String password;
	private static final String PROPERTIE_FILE="connection.properties";
	static {
		Properties properties=new Properties();
		try {
			properties.load(new FileInputStream(PROPERTIE_FILE));
			connection_url=properties.getProperty("connection_url");
			username=properties.getProperty("username");
			password=properties.getProperty("password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connection_url, username, password);
	}
}
