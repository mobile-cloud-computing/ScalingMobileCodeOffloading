package cs.mc.ut.ee.logic;


import java.lang.reflect.Method;
import java.util.*;

import edu.ut.mobile.network.CloudRemotable;

public class MergerSort extends CloudRemotable{
	
	public void localcalculate(int x){
		
		Integer[] num = new Integer[x];
        Random r = new Random();
        for (int i = 0; i < num.length; i++) {
                num[i] = r.nextInt(x);
        } 
		
		mergeSort(num);
		
		
	}
	
	public void mergeSort(Comparable [ ] a)
	{
		Comparable[] tmp = new Comparable[a.length];
		mergeSort(a, tmp,  0,  a.length - 1);
	}


	private void mergeSort(Comparable [ ] a, Comparable [ ] tmp, int left, int right)
	{
		if( left < right )
		{
			int center = (left + right) / 2;
			mergeSort(a, tmp, left, center);
			mergeSort(a, tmp, center + 1, right);
			merge(a, tmp, left, center + 1, right);
		}
	}


    private void merge(Comparable[ ] a, Comparable[ ] tmp, int left, int right, int rightEnd )
    {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while(left <= leftEnd && right <= rightEnd)
            if(a[left].compareTo(a[right]) <= 0)
                tmp[k++] = a[left++];
            else
                tmp[k++] = a[right++];

        while(left <= leftEnd)    // Copy rest of first half
            tmp[k++] = a[left++];

        while(right <= rightEnd)  // Copy rest of right half
            tmp[k++] = a[right++];

        // Copy tmp back
        for(int i = 0; i < num; i++, rightEnd--)
            a[rightEnd] = tmp[rightEnd];
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
	 	MergerSort localstate = (MergerSort) state;
	    	
	}
 }