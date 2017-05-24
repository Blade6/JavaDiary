package jdbc_mysql;

import java.sql.*;

public class SQLServerDemo {

	public static void main(String [] args) {
		String dbName = "gwedu";
		String userName="sa";
		String userPwd="blade";
		
		String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=" + dbName;
		try {
			Class.forName(driverName);
			Connection dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
			System.out.println("连接数据库成功");
		} catch(Exception e) {
			e.printStackTrace();
			System.out.print("连接失败");
		}    
	}
	
}
