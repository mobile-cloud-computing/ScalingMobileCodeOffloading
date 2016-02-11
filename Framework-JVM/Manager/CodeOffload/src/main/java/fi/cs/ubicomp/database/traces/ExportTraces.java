package fi.cs.ubicomp.database.traces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ExportTraces {
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	
	
	public void exportTracesToCSV(String fileOutput){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		    connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/qoe?"
		              + "user=huberuser&password=huber");

		    String sql = "SELECT * from " + TracesCollector.TABLENAME + " INTO OUTFILE '" + fileOutput + "' " +
		    				" FIELDS ENCLOSED BY '' " + 
		    				" TERMINATED BY ',' " + 
		    				" ESCAPED BY '\"' " +
		    				" LINES TERMINATED BY '\r\n';"; 
		    
		    
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
