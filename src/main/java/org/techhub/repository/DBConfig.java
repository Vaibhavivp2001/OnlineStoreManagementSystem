package org.techhub.repository;


	
	//database configuration use here we set database connectivity
	//singleton class private const
	//private method
	import java.sql.*;
	import java.util.*;
	import java.io.*;

	public class DBConfig {

		private static DBConfig db;
		private static Connection conn;
		private static PreparedStatement stmt;
		private static ResultSet rs;
		private static CallableStatement cstmt;

		private DBConfig() {
			try {
				File f = new File("");
				String path = f.getAbsolutePath();
				Properties prop = new Properties();
				Class.forName("com.mysql.cj.jdbc.Driver");// loading driver qualified class name
				FileInputStream inputStream = new FileInputStream("src/main/resources/dbconfig.properties");// data from
																											// properties
				prop.load(inputStream);
				String url = prop.getProperty("url");//
				String username = prop.getProperty("username");
				String password = prop.getProperty("password");
				conn = DriverManager.getConnection(url, username, password);

			} catch (Exception ex) {
				System.out.println("Error :" + ex.getMessage());
			}

		}

		public static DBConfig getInstance() {
			if (db == null) {
				db = new DBConfig();
			}
			return db;
		}

		public static Connection getConnection() {
			return conn;// helper class
		}

		public static PreparedStatement getStatement() {
			return stmt;
		}

		public static ResultSet getResult() {
			return rs;
		}

		public static CallableStatement getCallStatement() {
			return cstmt;
		}

	}


