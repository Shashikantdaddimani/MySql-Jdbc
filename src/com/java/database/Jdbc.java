package com.java.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class Jdbc {
	public static void main(String[] args) {
		System.out.println("Welcomr to Payroll_Service Jdbc program");

		String url = "jdbc:mysql://localhost:3306/payroll_services?useSSL=false";
		String username = "root";
		String password = "root";
		Connection connection;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded!!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("cannot find Driver in classpath... " + e);
		}
		listDrivers();
		try {
			System.out.println("Connection url: " + url);
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Connection Successfull!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void listDrivers() {
		Enumeration<java.sql.Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println(" " + driverClass.getClass().getName());
		}
	}
}
