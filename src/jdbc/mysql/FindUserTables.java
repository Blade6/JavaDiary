package jdbc.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

// 获取数据库中用户创建的表
public class FindUserTables {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/wechat", "wechat", "wechat");
			
			DatabaseMetaData dbMetaData = conn.getMetaData();
			
			ResultSet rsTables = dbMetaData.getTables(null, null, null, 
				new String[]{"TABLE"});
			System.out.println("User tables: ");
			
			while (rsTables.next())
				System.out.println(rsTables.getString("TABLE_NAME"));
			
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
