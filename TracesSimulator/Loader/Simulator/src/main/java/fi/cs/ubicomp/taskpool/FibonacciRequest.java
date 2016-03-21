package fi.cs.ubicomp.taskpool;

import java.util.Random;

import cs.mc.ut.ee.logic.Fibonacci;

public class FibonacciRequest implements Runnable {
	
	private static final String TAG = FibonacciRequest.class.getCanonicalName();

	public void run() {
		int max = 100000;
		Random r = new Random();
		
		Fibonacci task = new Fibonacci();
		
		task.calculate(r.nextInt(max));
		
		System.out.println("Executed " + TAG);
		
	}

}
