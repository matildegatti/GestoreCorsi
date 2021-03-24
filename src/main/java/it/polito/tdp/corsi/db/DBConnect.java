package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	//contiene solo un mettodo per connettersi al databse
	
	public static Connection getConnection() throws SQLException {
		String jdbcURL="jdbc:mysql://localhost/iscritticorsi?user=root&password=root1";
		return DriverManager.getConnection(jdbcURL);
	}
}
