package fi.cs.ubicomp.r.scripts;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;


public class GenericScript  {
	 
    public static void main(String a[]) {
    	
    	/**
    	 * Prior to execute the commands, Rserver must be running as a daemon
    	 * 
    	 * $ R
    	 * > library(Rserve)
    	 * > Rserve()
    	 */
    	
    	
    	nativeFunction();
    	
    	System.out.println("=====");
    	
    	userDefinedFunction();
    	
    }
    
    public static void nativeFunction(){
    	RConnection connection = null;

        try {
        	//default port 6311
            connection = new RConnection();

            String vector = "c(1,2,3,4)";
            connection.eval("meanVal=mean(" + vector + ")");
            double mean = connection.eval("meanVal").asDouble();
            System.out.println("Calculated mean of the vector= " + mean);
        } catch (RserveException e) {
            e.printStackTrace();
        } catch (REXPMismatchException e) {
            e.printStackTrace();
        }finally{
            connection.close();
        }

    }
    
    public static void userDefinedFunction(){
    	RConnection connection = null;
    	 
        try {
            //default port 6311
             
            connection = new RConnection();

            connection.eval("source('/home/huber/Desktop/source_code/RServe/userDefined.R')");
            
            /**
             * UserDefined.R
             * 
             * 
             * toSum=function(x,y){
    		 *		sum=x+y
    		 *		return(sum)
			 *	}
             * 
             */
            
            
            int num1=10;
            int num2=20;
            int sum=connection.eval("toSum("+num1+","+num2+")").asInteger();
            System.out.println("The sum is= " + sum);
        } catch (RserveException e) {
            e.printStackTrace();
        } catch (REXPMismatchException e) {
            e.printStackTrace();
        }
    }
    
}