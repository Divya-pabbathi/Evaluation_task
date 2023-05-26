package Methods;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataBase {
	
	static Connection connection;

	static DataSource ds;
	public  static DataSource dataSource() {
		
		try {			
			Context initContext = new InitialContext();
			ds=(DataSource) initContext.lookup("java:comp/env/jdbc/employee");

		}catch (Exception e) {
			System.out.println("data base error");
			System.out.println(e);
		}
		return ds;
		
	}
		
// 	public static void dbClose() {
// 		try {
// 			connection.close();
// 		} catch (SQLException e) {
// 			// TODO Auto-generated catch block
// 			e.printStackTrace();
// 		}
// 	}
	public Connection getConnector() {
		try {
			connection=dataSource().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in databaseutil");
		}
		return connection;
	}

}