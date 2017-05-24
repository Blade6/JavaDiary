package jdbc_mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlDemo {

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

			// statement用来执行SQL语句
			Statement statement = conn.createStatement();

			// 要执行的SQL语句
			String sql = "select userid, username from user";

			// 结果集
			ResultSet rs = statement.executeQuery(sql);

			System.out.println("-----------------");
			System.out.println("执行结果如下所示:");
			System.out.println("-----------------");
			System.out.println(" 帐号" + "\t" + " 用户名");
			System.out.println("-----------------");

			while(rs.next()) {
				// 输出结果
				System.out.println(rs.getString("userid") + "\t" + rs.getString("username"));
			}
							
			rs.close();
			statement.close();
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
