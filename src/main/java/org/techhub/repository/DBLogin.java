package org.techhub.repository;
import java.sql.*;

public class DBLogin {

		DBConfig db = DBConfig.getInstance();//here we variable initalized and object is created
		protected Connection conn = DBConfig.getConnection();
		protected ResultSet rs = DBConfig.getResult();
		protected PreparedStatement stmt = DBConfig.getStatement();
		protected CallableStatement cstmt = DBConfig.getCallStatement();

	}



