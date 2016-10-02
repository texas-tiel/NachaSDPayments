/**
 * 
 */
package rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author srivenperumal
 *
 */
public class PostgreSQLConnectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Attempting to connect to Database: \n");
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "123456";
		Connection c = null;
		Statement stmt = null;
		try{
			Class.forName("org.postgresql.Driver");
			c = DriverManager
		            .getConnection(url,user,password); 	
		
		System.out.println("Opened Database Successfully \n");

		
		/*Creating table - > 
		
		stmt = c.createStatement();
		String sql = "CREATE TABLE TRANSACTION " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " DATE        CHAR(50), " +
                " AMOUNT         REAL)";

		 stmt.executeUpdate(sql);
         stmt.close();
         c.close();
 */
		}
		
		catch (Exception e) 
		{
			e.printStackTrace(); // can enable this to see full stack
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
	 	}
	}
}

