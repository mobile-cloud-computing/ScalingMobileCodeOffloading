/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/


package cs.mc.ut.ee.cloud.commons;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import cs.mc.ut.ee.cloud.core.ExecuteCommand;
import cs.mc.ut.ee.cloud.data.DeploymentResources;
import cs.mc.ut.ee.cloud.data.Instance;


/**
 * author Huber Flores
 */

public class Monitor {

	DeploymentResources cloudScale = DeploymentResources.getInstance();
	
	List<Instance> instances = new ArrayList<Instance>();
	
	
	public Monitor(){
		fetchInstances();
		
		System.out.println(instances.size());
	}
	
	
	public void initiateServices() {
		ExecuteCommand.executeCommand("sudo chmod 777 /etc/collectd/collectd.conf");
	}
	
	
	public void configureKeys(String private_key, String certificate_key) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("amazon"));
			writer.write(
					"EC2_KEY_DIR=$(dirname $(readlink -f ${BASH_SOURCE}))\n" +
					"export EC2_PRIVATE_KEY=${EC2_KEY_DIR}/" + private_key + "\n" +
					"export EC2_CERT=${EC2_KEY_DIR}/" + certificate_key + "\n"
				);
			writer.close();
		} catch (Exception e) {
			System.err.println("Cannot create keys file");
		}
	}

	
	/**
	 * 	ec2-describe-instances
	 * --region eu-west-1  (specific region - Ireland)
	 */
	
	public void fetchInstances(){
		String[] payload = ExecuteCommand.executeCommand(
				"ec2-describe-instances --region eu-west-1 -K " + cloudScale.getAccessKey() + " -C " + cloudScale.getCert()
			).split("\n");
		
		for( Instance tmp : instances ){
			tmp.listed = false;
		}
		
		for( String line : payload ){
			/** try to read INSTANCE information */
			Instance tmp = new Instance(line);
			/** only select the ones, which have state changed to running */
			if(tmp.state.equals("running") && tmp.key.equals(cloudScale.getKeyName())){
				boolean found = false;
				for( Instance instance : instances ){
					if(instance.id.equals(tmp.id)){
						instance.listed = true;
						found = true;
					}
				}
				if(!found){
					instances.add(tmp);
					System.out.println("Found new instance:\n" + tmp);
				}
			}
		}
		
		
		//configureInstances();
	}

	
	
	public static void main(String[] args){	
		if(args.length > 0){
			System.out.println("Parameters do no have any effect");
		} else {
			new Monitor();
		}	
	}
	

}
