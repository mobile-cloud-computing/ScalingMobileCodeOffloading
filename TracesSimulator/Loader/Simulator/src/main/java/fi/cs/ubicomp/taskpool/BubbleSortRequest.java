package fi.cs.ubicomp.taskpool;

import java.util.Random;

import cs.mc.ut.ee.logic.BubbleSort;

public class BubbleSortRequest implements Runnable {

	private final String TAG = BubbleSortRequest.class.getCanonicalName();
	
	BubbleSort task;
		
	
	public void run() {
		// TODO Auto-generated method stub
		
		
		int max = 10000;
		Random r = new Random();
		
		int size = r.nextInt(max);
		
		task = new BubbleSort(size);
		task.sortFunction();
		
		System.out.println("Executed " + TAG);
		
	}

}
