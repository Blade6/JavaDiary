package JDBC_MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

// 结果集元数据
public class TestResultSetMetaData {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/wechat", "wechat", "wechat");
			
			Statement statement = conn.createStatement();
			
			ResultSet resultSet = statement.executeQuery(
				"select * from user");
			
			ResultSetMetaData rsMetaData = resultSet.getMetaData();
			for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
				System.out.printf("%-12s\t", rsMetaData.getColumnName(i));
			System.out.println();
			
			while (resultSet.next()) {
				for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
					System.out.printf("%-12s\t", resultSet.getObject(i));
				System.out.println();
			}
			
			conn.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
