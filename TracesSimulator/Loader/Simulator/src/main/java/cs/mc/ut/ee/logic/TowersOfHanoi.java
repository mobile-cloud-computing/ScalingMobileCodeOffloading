package cs.mc.ut.ee.logic;

import java.lang.reflect.Method;
import java.util.Vector;

import edu.ut.mobile.network.CloudRemotable;

public class TowersOfHanoi extends CloudRemotable{
	
	  public void localmove(int n, int startPole, int endPole) {
	    if (n== 0){
	      return;
	    }
	    int intermediatePole = 6 - startPole - endPole;
	    localmove(n-1, startPole, intermediatePole);
	    localmove(n-1, intermediatePole, endPole);
	  }
	  
	  
	  public void move(int n, int startPole, int endPole) {
		  Method toExecute;
		  Class<?>[] paramTypes = {int.class, int.class, int.class};
		  Object[] paramValues = {n, startPole, endPole};
		  int result;
		   
		 try{ 

   		 toExecute = this.getClass().getDeclaredMethod("localmove", paramTypes);
   		 Vector results = getCloudController().execute(toExecute,paramValues,this,this.getClass());
   		 if(results != null){
   		 	copyState(results.get(1));
             System.out.println("Executed using external resources");

   		 }else{ 
   			localmove(n, startPole, endPole);
   			System.out.println("Executed using local resources");
   		 }
	
	
		 	} catch (SecurityException se){
		} catch (NoSuchMethodException ns){
		}catch (Throwable th){}

		  
	  }
	  
	  
	  void copyState(Object state){
			TowersOfHanoi localstate = (TowersOfHanoi) state;
	  }
	  
} 