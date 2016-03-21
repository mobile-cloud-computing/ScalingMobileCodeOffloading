package cs.mc.ut.ee.logic;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.Vector;

import edu.ut.mobile.network.CloudRemotable;


public class QuickSort extends CloudRemotable{

	private int[] numbers;
	
	public QuickSort(int[] numbers){
		this.numbers = numbers;
	}


	public void localquicksort(int low, int high) {
		int i = low, j = high;
		int pivot = numbers[low + (high - low) / 2];


		while (i <= j) {
				while (numbers[i] < pivot) {
					i++;
				}
				while (numbers[j] > pivot) {
					j--;
				}

				if (i <= j) {
					exchange(i, j);
					i++;
					j--;
				}
		}

		if (low < j)
			localquicksort(low, j);
		if (i < high)
			localquicksort(i, high);

	}

	private void exchange(int i, int j) {
		int temp = numbers[i];
		numbers[i] = numbers[j];
		numbers[j] = temp;
	}
	
	public void quicksort(int low, int high) {
		   Method toExecute;
		   Class<?>[] paramTypes = {int.class, int.class};
		   Object[] paramValues = {low, high};
		   int result;
		   
		 	try{ 

    		toExecute = this.getClass().getDeclaredMethod("localquicksort", paramTypes);
    		Vector results = getCloudController().execute(toExecute,paramValues,this,this.getClass());
    		if(results != null){
    			copyState(results.get(1));
              System.out.println("Executed using external resources");

    		}else{ 
    			localquicksort(low, high);
    			System.out.println("Executed using local resources");
    		}
	
	
		 	} catch (SecurityException se){
		} catch (NoSuchMethodException ns){
		}catch (Throwable th){}
		
	}
	
	void copyState(Object state){
		QuickSort localstate = (QuickSort) state;
	}
	
	

}



