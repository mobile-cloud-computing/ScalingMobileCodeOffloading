package fi.cs.ubicomp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TracesCollector {
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	
	public static String TABLENAME = "requests";
	
	
	public TracesCollector(){
		
	}
	
	public void saveTrace(double timestamp, double client1, double server1, double server2, double client2, double cloudtime, double totaltime){
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			
		    connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/qoe?"
		              + "user=huberuser&password=huber");

		    String sql = "INSERT INTO " + TABLENAME + "(timestamp, client1, server1, server2, client2, cloudtime, totaltime) " +
	                   "VALUES ("+ timestamp + "," +  client1  + "," + server1 + "," + server2 + "," + client2  + "," + cloudtime + "," + totaltime + ");"; 
		    
		    
		    preparedStatement = connect.
		    		prepareStatement(sql);
		    
		    preparedStatement.executeUpdate();
		      
		      
		      
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
			    
		}
	     
		
	}
	
	
	
	private void close() {
	    try {
	      if (statement != null) {
	        statement.close();
	      }

	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }


	
	

}
