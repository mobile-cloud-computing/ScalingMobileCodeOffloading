package fi.cs.ubicomp.taskpool;

import cs.mc.ut.ee.logic.BubbleSort;

public class BubbleSortRequest implements Runnable {

	private final String TAG = BubbleSortRequest.class.getCanonicalName();
	
	BubbleSort task;
		
	
	public void run() {
		// TODO Auto-generated method stub
		
		task = new BubbleSort(10);
		task.sortFunction();
		
		System.out.println("Executed " + TAG);
		
	}

}
