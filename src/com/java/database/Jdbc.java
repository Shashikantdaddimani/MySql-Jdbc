package com.java.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

public class Jdbc {
	static Connection connection = null;

	public static void main(String[] args) throws SQLException {
		connection = connected();
		retrieveData(connection);
	}

	public static Connection connected() {
		String url = "jdbc:mysql://localhost:3306/payroll_services?useSSL=false";
		String username = "root";
		String password = "root";
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		listDrivers();
		try {
			System.out.println("Connecting to database: " + url);
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Connection is Successful - " + connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void retrieveData(Connection connection) throws SQLException {

		// String query = "";
		PreparedStatement preparedStatement = connection.prepareStatement("select * from employee_payroll where id=?");
		preparedStatement.setInt(1, 1);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			System.out.println(resultSet.getInt("id"));
			System.out.println(resultSet.getString("name"));

		}
	}

	public static void listDrivers() {
		Enumeration<java.sql.Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println(" " + driverClass.getClass().getName());
		}
	}
}
