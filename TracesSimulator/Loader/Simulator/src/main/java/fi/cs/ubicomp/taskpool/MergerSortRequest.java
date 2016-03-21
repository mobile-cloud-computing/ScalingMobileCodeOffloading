package fi.cs.ubicomp.taskpool;


import java.util.Random;

import cs.mc.ut.ee.logic.MergerSort;

public class MergerSortRequest implements Runnable {
	
	private static final String TAG = MergerSortRequest.class.getCanonicalName();
	

	public void run() {
		// TODO Auto-generated method stub
		
		int max = 100000;
		Random r = new Random();
		
		int pos = r.nextInt(max);
		
		
		MergerSort task = new MergerSort();
		
		task.calculate(pos);
		
		System.out.println("Executed " + TAG);
		
		
	}

}
