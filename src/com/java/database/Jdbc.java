package com.java.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

public class Jdbc {
	static Connection connection = null;

	public static void main(String[] args) throws SQLException {
		connection = connected();
		retrieveData(connection);
		updateData(connection);
		System.out.println(reteriveDataByName(connection));
		particularDateRange(connection);
		sumByGroup(connection);
		avgSalary(connection);
		minmumSalary(connection);
		maxSalary(connection);
		count(connection);
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
		PreparedStatement preparedStatement = connection.prepareStatement("select * from employee_payroll where id=?");
		preparedStatement.setInt(1, 1);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			System.out.println(resultSet.getInt("id"));
			System.out.println(resultSet.getString("name"));
		}
	}

	public static void updateData(Connection connection) throws SQLException {
		// Scanner scanner = new Scanner(System.in);
		PreparedStatement preparedStatement = connection
				.prepareStatement("update employee_payroll set salary = ? where id =?;");
		System.out.println("Enter salary to be updated: ");
		// double salary = scanner.nextDouble();
		System.out.println("Enter at which id you want to update salary: ");
		// int id = scanner.nextInt();
		preparedStatement.setDouble(1, 300000.00);
		preparedStatement.setInt(2, 2);
		preparedStatement.executeUpdate();
		System.out.println("Updated Successfully.....!!!");
	}

	public static Double reteriveDataByName(Connection connection) throws SQLException {
		Double salary = null;
		PreparedStatement preparedStatement = connection
				.prepareStatement("select * from employee_payroll where name =?");
		preparedStatement.setString(1, "Terisa");
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			salary = (resultSet.getDouble("salary"));
		}
		return salary;
	}

	public static String particularDateRange(Connection connection) throws SQLException {
		String name = null;
		String query = "select * from employee_payroll where startDate between ? and ? ";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, "2019-01-03");
		preparedStatement.setString(2, "2019-11-13");
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			System.out.println(" ");
			System.out.println(resultSet.getInt(1));
			System.out.println(resultSet.getString(2));
			name = (resultSet.getString("name"));
		}
		return name;
	}

	public static Double sumByGroup(Connection connection) throws SQLException {
		Double salary = null;
		PreparedStatement preparedStatement = connection.prepareStatement(
				"select sum(salary) as salary from employee_payroll where gender = ? group by gender");
		preparedStatement.setString(1, "F");
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			salary = (resultSet.getDouble("salary"));
			System.out.println("Sum of salary by gender(F) is: " + salary);
		}
		return salary;
	}

	public static Double avgSalary(Connection connection) throws SQLException {
		Double avgSalary = null;
		PreparedStatement preparedStatement = connection.prepareStatement(
				"select avg(salary) as salary from employee_payroll where gender = ? group by gender");
		preparedStatement.setString(1, "M");
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			avgSalary = (resultSet.getDouble("salary"));
			System.out.println("Average salary of males is: " + avgSalary);
		}
		return avgSalary;
	}

	public static Double minmumSalary(Connection connection) throws SQLException {
		Double salary = null;
		PreparedStatement preparedStatement = connection.prepareStatement(
				"select min(salary) as salary from employee_payroll where gender = ? group by gender");
		preparedStatement.setString(1, "m");

		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			salary = (resultSet.getDouble("salary"));
			System.out.println("minimum of salary by gender(m) is: " + salary);
		}
		return salary;
	}

	public static Double maxSalary(Connection connection) throws SQLException {
		Double salary = null;
		PreparedStatement preparedStatement = connection.prepareStatement(
				"select max(salary) as salary from employee_payroll where gender = ? group by gender");
		preparedStatement.setString(1, "m");

		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			salary = (resultSet.getDouble("salary"));
			System.out.println("Maximum of salary by gender(m) is: " + salary);
		}
		return salary;
	}

	public static Integer count(Connection connection) throws SQLException {
		Integer gender = null;
		PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(gender) as salary from employee_payroll where gender = ? group by gender");
		preparedStatement.setString(1, "m");

		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			gender = (resultSet.getInt("salary"));
			System.out.println("number of gender mele is : " + gender);
		}
		return gender;
	}

	public static void listDrivers() {
		Enumeration<java.sql.Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println(" " + driverClass.getClass().getName());
		}
	}
}
