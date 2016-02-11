package cs.mc.ut.ee.simulator.concurrent;

import cs.mc.ut.ee.utilities.Commons;
import cs.mc.ut.ee.utilities.SimulatorConfiguration;

public class Controller {

	/**
	 * @author Huber Flores
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SimulatorConfiguration operation = new SimulatorConfiguration();
		Commons.mode = operation.getSimulatorMode();
		
		int users = 0;
		
		 if (args.length == 0)
		 {
	         System.err.println ("Define the number the users to create");
	         System.exit(0);
		 }else{
			users = Integer.valueOf(args[0]);
		 }

		new LoadGeneratorConcurrent().generateLoad(users);
		System.out.println("Generating " + users + " code offload request(s)...");
	}

}