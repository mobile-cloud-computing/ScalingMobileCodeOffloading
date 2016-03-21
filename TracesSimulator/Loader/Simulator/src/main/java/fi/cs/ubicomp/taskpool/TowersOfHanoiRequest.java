package fi.cs.ubicomp.taskpool;

import java.util.Random;

import cs.mc.ut.ee.logic.TowersOfHanoi;

public class TowersOfHanoiRequest implements Runnable{
	
	private static final String TAG = TowersOfHanoiRequest.class.getCanonicalName();
	

	public void run() {
		// TODO Auto-generated method stub
		int max = 30;
		Random r1 = new Random();
		Random r2 = new Random();
		
		
		TowersOfHanoi task = new TowersOfHanoi();
		
		task.move(r1.nextInt(max), 1, r2.nextInt(max));
		
		
		System.out.println("Executed " + TAG);
		
	}

}
