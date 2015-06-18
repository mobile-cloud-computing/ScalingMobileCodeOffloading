/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/


package cs.mc.ut.ee.cloud.core;

import cs.mc.ut.ee.cloud.data.DeploymentResources;


/**
 * author Huber Flores
 */

public class Cloner {

	static DeploymentResources fileConfig = DeploymentResources.getInstance();
	
	
	public Cloner(){
		
	}
	
	
	
	
	public static void main(String[] args){	
		/*if(args.length > 0){
			System.out.println("Parameters do no have any effect");
		} else {
			new Cloner();
		}*/
		
		CloudCommands.configureKeys(fileConfig.getAccessKey(), fileConfig.getCert());
		CloudCommands.runInstance(fileConfig, 1);
		
	}
	

}
