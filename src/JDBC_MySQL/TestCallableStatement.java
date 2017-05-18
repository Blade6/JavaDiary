package JDBC_MySQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/**
 drop function if exists userFound;
 delimiter //
 create function userFound(userid char(6))
	returns int
 begin
 	declare result int;
 		
  	select count(*) into result
  	from user
  	where user.userid = userid;
  		
  	return result;
 end;
 //
  
 delimiter ;
 */
// ²âÊÔº¯Êý
public class TestCallableStatement {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/wechat", "wechat", "wechat");
			
			// Create a callable statement
			CallableStatement callableStatement = conn.prepareCall(
				"{? = call userFound(?)}");
			
			Scanner input = new Scanner(System.in);
			System.out.print("Enter userid: ");
			String userid = input.nextLine();
			
			callableStatement.setString(2, userid);
			callableStatement.registerOutParameter(1, Types.INTEGER);
			callableStatement.execute();
			
			if (callableStatement.getInt(1) >= 1)
				System.out.println(userid + " is in the database");
			else
				System.out.println(userid + " is not in the database");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
