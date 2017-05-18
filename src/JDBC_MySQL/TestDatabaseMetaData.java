package JDBC_MySQL;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

// 查看数据库元数据信息
public class TestDatabaseMetaData {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");
			
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/wechat", "wechat", "wechat");
			System.out.println("Database connected");
			
			DatabaseMetaData dbMetaData = conn.getMetaData();
			System.out.println("database URL: " + dbMetaData.getURL());
			System.out.println("database username: " + dbMetaData.getUserName());
			System.out.println("database product name: " + dbMetaData.getDatabaseProductName());
			System.out.println("database product version: " + dbMetaData.getDatabaseProductVersion());
			System.out.println("JDBC driver name: " + dbMetaData.getDriverName());
			System.out.println("JDBC driver version: " + dbMetaData.getDriverVersion());
			System.out.println("JDBC driver major verson: " + dbMetaData.getDriverMajorVersion());
			System.out.println("JDBC driver minor version: " + dbMetaData.getDriverMinorVersion());
			System.out.println("Max number of connections: " + dbMetaData.getMaxConnections());
			System.out.println("MaxTableNameLength: " + dbMetaData.getMaxTableNameLength());
			System.out.println("MaxColumnInTable: " + dbMetaData.getMaxColumnsInTable());
			
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
