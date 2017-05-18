package JDBC_MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Statement�ӿ�����ִ�в��������ľ�̬SQL��䡣
// PreparedStatement�ӿ�����Statement�ӿ���չ������
// ����ִ�к��л򲻺�������Ԥ�����SQL���
// ����SQL�����Ԥ����ģ������ظ�ִ��ʱЧ�ʽϸ�
public class PreparedStarementDemo {
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
			
			// Ҫִ�е�SQL���,ע��user��where֮���пո�ģ�����������ǵ��±��﷨����
			String queryString = "select userid, username from user " +
				"where username = ?";
			
			// Create a statement
			PreparedStatement preparedStatement = conn.prepareStatement(queryString);
			
			String username = "blade";
			preparedStatement.setString(1, username);
			
			// �����
			ResultSet rs = preparedStatement.executeQuery();

			System.out.println("-----------------");
			System.out.println("ִ�н��������ʾ:");
			System.out.println("-----------------");
			System.out.println(" �ʺ�" + "\t" + " �û���");
			System.out.println("-----------------");

			if (rs.next()) {
				// ������
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
