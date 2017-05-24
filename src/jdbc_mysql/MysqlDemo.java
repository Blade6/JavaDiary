package jdbc_mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlDemo {

	public static void main(String[] args) {
		// ����������
		String driver = "com.mysql.jdbc.Driver";

		// URLָ��Ҫ���ʵ����ݿ���university
		String url = "jdbc:mysql://127.0.0.1:3306/wechat";

		// MySQL����ʱ���û���
		String user = "wechat"; 

		// MySQL����ʱ������
		String password = "wechat";

		try { 
			// ������������
			Class.forName(driver);

			// �������ݿ�
			Connection conn = DriverManager.getConnection(url, user, password);

			if(!conn.isClosed()) 
				System.out.println("Succeeded connecting to the Database!");

			// statement����ִ��SQL���
			Statement statement = conn.createStatement();

			// Ҫִ�е�SQL���
			String sql = "select userid, username from user";

			// �����
			ResultSet rs = statement.executeQuery(sql);

			System.out.println("-----------------");
			System.out.println("ִ�н��������ʾ:");
			System.out.println("-----------------");
			System.out.println(" �ʺ�" + "\t" + " �û���");
			System.out.println("-----------------");

			while(rs.next()) {
				// ������
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
