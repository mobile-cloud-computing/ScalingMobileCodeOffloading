package cs.mc.ut.ee.logic;

import java.lang.reflect.Method;
import java.util.Vector;

import edu.ut.mobile.network.CloudRemotable;

public class Fibonacci extends CloudRemotable {
	
	public int localcalculate(int n){

         int limit = n;

         long[] series = new long[limit];

         series[0] = 0;
         series[1] = 1;

         for(int i=2; i < limit; i++){
                 series[i] = series[i-1] + series[i-2];
         }
         
         return 0;
		
	}
	
	public void calculate(int n){
		 Method toExecute;
		   Class<?>[] paramTypes = {int.class};
		   Object[] paramValues = {n};
		   int result;
		   
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
    	Fibonacci localstate = (Fibonacci) state;
    	
   }

}
