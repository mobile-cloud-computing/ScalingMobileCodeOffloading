package cs.mc.ut.ee.simulator.concurrent;

import cs.mc.ut.ee.logic.CodeOffloadRequest;


/*
 * author Huber Flores
 */

public class LoadGenerator {
	
	
	public void generateLoad(int users){
		
		for (int i = 0; i<users; i++){
			new Thread(new CodeOffloadRequest()).start();
		}
		
	}


}