package cs.mc.ut.ee.cloud.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import cs.mc.ut.ee.cloud.data.DeploymentResources;
import cs.mc.ut.ee.cloud.data.Instance;

public class CloudCommands {
	
	
	public static void configureKeys(String private_key, String certificate_key) {
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
	
	public static void fetchRunningInstances(DeploymentResources config){
		List<Instance> instances = new ArrayList<Instance>();
		String[] payload = ExecuteCommand.executeCommand(
				"ec2-describe-instances --region eu-west-1 -K " + config.getAccessKey() + " -C " + config.getCert()
			).split("\n");
		
		for( Instance tmp : instances ){
			tmp.listed = false;
		}
		
		for( String line : payload ){
			/** try to read INSTANCE information */
			Instance tmp = new Instance(line);
			/** only select the ones, which have state changed to running */
			if(tmp.state.equals("running") && tmp.key.equals(config.getKeyName())){
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

	}
		
	
	public static void runInstance(DeploymentResources config, int numberOfInstances){
		
		String output = ExecuteCommand.executeCommand(
				"ec2-run-instances "+ config.getImageId() + " -n " + numberOfInstances + " -k " + config.getAccessKey() + " -C " + config.getCert() +
				" --instance-type " +  config.getInstanceType() + " --region eu-west-1c"
			);
		
		System.out.println("Command result: " + output);
	}

	

}
