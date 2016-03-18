package cs.mc.ut.ee.simulator.concurrent;

import java.util.List;
import java.util.Random;

import cs.ut.ee.algorithm.CodeOffloadingPool;


/*
 * author Huber Flores
 */

public class LoadGeneratorConcurrent {
	
	List<String> workload = CodeOffloadingPool.getComputationalWorkload();
	
	
	public void generateLoad(int users){
		
		for (int i = 0; i<users; i++){
			try {
				new Thread((Runnable)ClassLoader.getSystemClassLoader().loadClass(getRandomTask()).newInstance()).start();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public String getRandomTask(){
		String task;
		Random r = new Random();
		
		int pos = r.nextInt(workload.size());
		
		
		return workload.get(pos);
		
	}
	

}