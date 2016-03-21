package fi.cs.ubicomp.taskpool;

import java.util.Random;

import cs.mc.ut.ee.logic.NQueens;

public class NQueensRequest implements Runnable{
	
	private final String TAG = NQueens.class.getCanonicalName();
	
	NQueens task;

	public void run() {
		// TODO Auto-generated method stub
		
		int max = 8;
		Random r = new Random();
		int pos = r.nextInt(max);
		
		
		task = new NQueens(pos);
		task.enumerateQueens();
		
		System.out.println("Executed " + TAG);
	}

}
