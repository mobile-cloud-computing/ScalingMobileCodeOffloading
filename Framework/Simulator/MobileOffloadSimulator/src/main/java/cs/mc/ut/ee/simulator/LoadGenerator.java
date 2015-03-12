package cs.mc.ut.ee.simulator;


/*
 * author Huber Flores
 */

public class LoadGenerator {
	
	double time;
	
	public void generateLoad(int experimentalTime, double interarrivalTime){
		//experimentalTime is minutes
		
		
		time = experimentalTime *60*1000;
		
		double startTime = System.currentTimeMillis();
		while((System.currentTimeMillis() - startTime) < time){
			
			new Thread(new CodeOffloadRequest()).start();
			
			try {
				long waiting = (long) (interarrivalTime * 1000);
				
				Thread.sleep(waiting);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	
		
	}


}
