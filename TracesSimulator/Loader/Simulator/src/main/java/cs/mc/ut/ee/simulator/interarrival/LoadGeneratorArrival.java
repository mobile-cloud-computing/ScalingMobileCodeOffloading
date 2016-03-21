package cs.mc.ut.ee.simulator.interarrival;

import java.util.List;
import java.util.Random;

import cs.ut.ee.algorithm.CodeOffloadingPool;


/*
 * author Huber Flores
 */

public class LoadGeneratorArrival {
	
	double time;
	
	CodeOffloadingPool pool = new CodeOffloadingPool();
	
	List<String> workload = pool.getComputationalWorkload();;
	
	
	public void generateLoad(int experimentalTime, double interarrivalTime){
		//experimentalTime is minutes
		
		
		time = experimentalTime *60*1000;
		
		double startTime = System.currentTimeMillis();
		while((System.currentTimeMillis() - startTime) < time){
			
			try {
				new Thread((Runnable)ClassLoader.getSystemClassLoader().loadClass(getRandomTask()).newInstance()).start();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				long waiting = (long) (interarrivalTime * 1000);
				
				Thread.sleep(waiting);
			} catch (InterruptedException e) {
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
