package JDBC_MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Statement接口用于执行不含参数的静态SQL语句。
// PreparedStatement接口是由Statement接口扩展而来，
// 用于执行含有或不含参数的预编译的SQL语句
// 由于SQL语句是预编译的，所以重复执行时效率较高
public class PreparedStarementDemo {
	public static void main(String[] args) {
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名university
		String url = "jdbc:mysql://127.0.0.1:3306/wechat";

		// MySQL配置时的用户名
		String user = "wechat"; 

		// MySQL配置时的密码
		String password = "wechat";

		try { 
			// 加载驱动程序
			Class.forName(driver);

			// 连接数据库
			Connection conn = DriverManager.getConnection(url, user, password);

			if(!conn.isClosed()) 
				System.out.println("Succeeded connecting to the Database!");
			
			// 要执行的SQL语句,注意user和where之间有空格的，这点容易忘记导致报语法错误
			String queryString = "select userid, username from user " +
				"where username = ?";
			
			// Create a statement
			PreparedStatement preparedStatement = conn.prepareStatement(queryString);
			
			String username = "blade";
			preparedStatement.setString(1, username);
			
			// 结果集
			ResultSet rs = preparedStatement.executeQuery();

			System.out.println("-----------------");
			System.out.println("执行结果如下所示:");
			System.out.println("-----------------");
			System.out.println(" 帐号" + "\t" + " 用户名");
			System.out.println("-----------------");

			if (rs.next()) {
				// 输出结果
				System.out.println(rs.getString("userid") + "\t" + rs.getString("username"));
			}
										
			rs.close();
			preparedStatement.close();
			conn.close();
		} catch(ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!"); 
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
