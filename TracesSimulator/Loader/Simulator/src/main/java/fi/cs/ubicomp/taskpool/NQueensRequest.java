package fi.cs.ubicomp.taskpool;

import cs.mc.ut.ee.logic.NQueens;

public class NQueensRequest implements Runnable{
	
	private final String TAG = NQueens.class.getCanonicalName();
	
	NQueens task;

	public void run() {
		// TODO Auto-generated method stub
		
		task = new NQueens(8);
		task.callplaceNqueens();
		
		System.out.println("Executed " + TAG);
	}

}
