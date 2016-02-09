package fi.cs.ubicomp.database.traces;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
public class TracesCollector {
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	
	private String TABLENAME = "loadtraces";	
	
	public TracesCollector(){
		

	}
	
	
	public void saveTrace(double timestamp, String device, int accgroup, double rtt, double energy, double responsetime){
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			
		    connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/qoe?"
		              + "user=huberuser&password=huber");

		    String sql = "INSERT INTO " + TABLENAME + "(timestamp, device, acceleration, rtt, energy, responsetime) " +
	                   "VALUES ("+ timestamp + "," + "\"" + device + "\"" + "," + accgroup + "," + rtt + "," + energy  + "," + responsetime + ");"; 
		    
		    
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
