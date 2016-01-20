/*
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * Please send inquiries to huber AT ut DOT ee
 */

package cs.mc.ut.ee.simulator.interarrival;

public class Controller {

	/**
	 * @author Huber Flores
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int experimentalTime = 0;
		double interarrivalTime = 0;
		
		
		if (args.length>1){
			experimentalTime = Integer.valueOf(args[0]);
			interarrivalTime = Double.parseDouble(args[1]);
		}else{
			System.err.println("Missing parameters");
			System.err.println ("param 1: experimental time, param 2: interarrival time");
			System.exit(0);
		}
		

		new LoadGenerator().generateLoad(experimentalTime, interarrivalTime);
	
	}

}
