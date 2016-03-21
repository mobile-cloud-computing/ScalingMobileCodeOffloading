package fi.cs.ubicomp.taskpool;

import java.util.Random;

import cs.mc.ut.ee.logic.MonteHall;

public class MonteHallRequest implements Runnable {
	
	private static final String TAG = MonteHallRequest.class.getCanonicalName();

	public void run() {
		
		int max = 1000000;
		Random r = new Random();
		
		MonteHall task = new MonteHall();
		
		task.calculate(r.nextInt(max));
		
		System.out.println("Executed " + TAG);
		
	}
	
	

}
