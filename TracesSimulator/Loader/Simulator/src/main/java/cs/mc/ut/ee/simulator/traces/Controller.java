package cs.mc.ut.ee.simulator.traces;

import java.util.LinkedList;

import cs.mc.ut.ee.simulator.concurrent.LoadGeneratorConcurrent;
import cs.mc.ut.ee.simulator.interarrival.LoadGeneratorArrival;
import cs.mc.ut.ee.utilities.Commons;
import cs.mc.ut.ee.utilities.SimulatorConfiguration;
import cs.mc.ut.ee.utilities.WorkLoad;

import fi.cs.ubicomp.taskpool.MiniMaxRequest;
import fi.cs.ubicomp.traces.DataTrace;
import fi.cs.ubicomp.traces.Loader;

public class Controller {

	/**
	 * @author Huber Flores
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SimulatorConfiguration operation = new SimulatorConfiguration();
		Commons.mode = operation.getSimulatorMode();
		
		/**
		 * Operation mode is defined by 'simulator.conf'
		 */
		
		switch(Commons.mode){
		
		case 0:
			System.out.println("Operation mode: " + Commons.mode + " - Traces");
			
			String filePath="";
			
			 if (args.length == 0)
			 {
		         System.err.println ("Introduce the path of the traces");
		         System.exit(0);
			 }else{
				filePath = args[0];
			 }
			 
			 if (filePath.length()==0){
				 System.exit(0);
			 }

			
			Loader generator = new Loader(filePath);
			LinkedList<DataTrace> traces = generator.getTraces();
			
			WorkLoad.getInstance().setUsersWorkLoad(traces);
			
			
			for (int i = 0; i<traces.size(); i++){
				new Thread(new MiniMaxRequest()).start();
			}
			
			
			break;
			
		
		case 1:
			
			System.out.println("Operation mode: " + Commons.mode + " - Inter-arrival");
			
			int experimentalTime = 0;
			double interarrivalTime = 0;
			int numberOfUsers = 0;
			int numberOfGroups = 0;
			
			
			if (args.length>1){
				experimentalTime = Integer.valueOf(args[0]);
				interarrivalTime = Double.parseDouble(args[1]);
				numberOfUsers = Integer.valueOf(args[2]);
				numberOfGroups = Integer.valueOf(args[3]);
				
			}else{
				System.err.println("Missing parameters");
				System.err.println ("param 1: experimental time, param 2: interarrival time, param 3: number of users, param 4: number of groups");
				System.exit(0);
			}
			
			WorkLoad.getInstance().setNumberOfUsers(numberOfUsers);
			WorkLoad.getInstance().setAccelerationGroups(numberOfGroups);
			WorkLoad.getInstance().generateSnapShot();

			new LoadGeneratorArrival().generateLoad(experimentalTime, interarrivalTime);
			
			
			
			break;
			
			
		case 2: 
			System.out.println("Operation mode: " + Commons.mode + " - Concurrent");
			
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
			
			break;
		
		
		}
				
	}
}