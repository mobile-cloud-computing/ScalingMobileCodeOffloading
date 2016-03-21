package fi.cs.ubicomp.taskpool;

import java.util.Random;

import cs.mc.ut.ee.logic.QuickSort;

public class QuickSortRequest implements Runnable {
	
	private static final String TAG = QuickSortRequest.class.getCanonicalName();
	
	private int[] numbers;
	private int number;


	public void run() {
		// TODO Auto-generated method stub
		int max = 99999;
		Random base = new Random();
		
		int pos = base.nextInt(max);
		
		int[] values = new int[pos];
        Random r = new Random();
        for (int i = 0; i < values.length; i++) {
        	values[i] = r.nextInt(pos);
        }
        numbers = values;
        number = values.length;
        
        QuickSort task = new QuickSort(numbers);
        
        task.quicksort(0, number - 1);
		
    	System.out.println("Executed " + TAG);
		
	}
	
	

	 

}
