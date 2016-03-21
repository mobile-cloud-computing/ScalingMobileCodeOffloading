package cs.mc.ut.ee.logic;

import java.lang.reflect.Method;
import java.util.Vector;

import edu.ut.mobile.network.CloudRemotable;

public class MonteHall extends CloudRemotable {

	   public void localcalculate(int n) { 
	      int N = n;
	      int wins = 0;                       
	      
	      for (int i = 0; i < N; i++) {

	     
	         int prize  = (int) (3 * Math.random());

	         int choice = (int) (3 * Math.random());

	         int reveal;
	         do {
	            reveal = (int) (3 * Math.random());
	         } while ((reveal == choice) || (reveal == prize));

	         
	         int other = 0 + 1 + 2 - reveal - choice;

	         if (other == prize) wins++;
	      } 

	      
	   }
	   
	   public void calculate(int n) {
		   Method toExecute;
		   Class<?>[] paramTypes = {int.class};
		   Object[] paramValues = {n};
		   float[] result = new float[3];
		   
		 	try{ 

        		toExecute = this.getClass().getDeclaredMethod("localcalculate", paramTypes);
        		Vector results = getCloudController().execute(toExecute,paramValues,this,this.getClass());
        		if(results != null){
        			copyState(results.get(1));
                    System.out.println("Executed using external resources");

        		}else{ 
        			localcalculate(n);
        			System.out.println("Executed using local resources");
        		}
    	
    	
		 	} catch (SecurityException se){
    		} catch (NoSuchMethodException ns){
    		}catch (Throwable th){}
		   
	   }
	   
	   void copyState(Object state){
	    	MonteHall localstate = (MonteHall) state;
	    	
	   }

	}