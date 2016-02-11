/*
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * Please send inquiries to huber AT ut DOT ee
 */

package cs.mc.ut.ee.simulator.interarrival;

import cs.mc.ut.ee.utilities.Commons;
import cs.mc.ut.ee.utilities.SimulatorConfiguration;
import cs.mc.ut.ee.utilities.WorkLoad;

public class Controller {

	/**
	 * @author Huber Flores
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimulatorConfiguration operation = new SimulatorConfiguration();
		Commons.mode = operation.getSimulatorMode();
		
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

		new LoadGeneratorArrival().generateLoad(experimentalTime, interarrivalTime);
	
	}

}
