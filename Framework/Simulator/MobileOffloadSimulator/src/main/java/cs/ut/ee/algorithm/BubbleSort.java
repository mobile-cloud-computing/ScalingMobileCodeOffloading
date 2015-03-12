package cs.ut.ee.algorithm;


import java.lang.reflect.Method;
import java.util.Random;
import java.util.Vector;

import edu.ut.mobile.network.CloudRemotable;


/**
 * 
 * @author Huber Flores
 *
 */

public class BubbleSort extends CloudRemotable {
	
		
		int x;
	
		public BubbleSort(int N){ 
			x = N;
		}
		
		
		public double localsortFunction(){
			int[] num = new int[50000];
            Random r = new Random();
            for (int i = 0; i < num.length; i++) {
                    num[i] = r.nextInt(1000000);
            } 
            long startTime = System.nanoTime( );
            int j;
            boolean flag = true; 
            int temp; 

            while (flag) {
                    flag = false; 
                    for (j = 0; j < num.length - 1; j++) {
                            if (num[j] < num[j + 1]) 
                            {
                                    temp = num[j]; 
                                    num[j] = num[j + 1];
                                    num[j + 1] = temp;
                                    flag = true; 
                            }
                    }
            }

            return ((System.nanoTime() - startTime)*1.0e-6);

		}
		
		public double sortFunction() {
			Method toExecute;   
	    	Class<?>[] paramTypes = null;
	    	Object[] paramValues = null; 
	    	double result;
				
				try{
					toExecute = this.getClass().getDeclaredMethod("localsortFunction", paramTypes);
					Vector results = getCloudController().execute(toExecute,paramValues,this,this.getClass());
					if(results != null){
						result = (Double) results.get(0);
						copyState(results.get(1));
						System.out.println("Remote result: " + result);
						return result;
					}else{
						result = localsortFunction(); 
						System.out.println("Local result: " + result);
						return result;
					}
				}  catch (SecurityException se){
				} catch (NoSuchMethodException ns){
				}catch (Throwable th){
				}
				return localsortFunction();
				
		}
		
		

		void copyState(Object state){
			BubbleSort localstate = (BubbleSort) state;
			this.x = localstate.x;
		}
}


