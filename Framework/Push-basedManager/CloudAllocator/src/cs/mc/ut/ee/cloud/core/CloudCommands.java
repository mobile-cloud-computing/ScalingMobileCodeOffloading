package cs.mc.ut.ee.cloud.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import cs.mc.ut.ee.cloud.data.DeploymentResources;
import cs.mc.ut.ee.cloud.data.Instance;

/**
 * 
 * @author Huber Flores
 *
 */


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
	
	public static List<Instance> fetchRunningInstances(DeploymentResources config){
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
			if(tmp.state.equals("running") /*&& tmp.key.equals(config.getKeyName())*/){
				boolean found = false;
				for( Instance instance : instances ){
					if(instance.id.equals(tmp.id)){
						instance.listed = true;
						found = true;
					}
				}
				if(!found){
					instances.add(tmp);
					//System.out.println("Found new instance:\n" + tmp);
				}
			}
		}
		
		return instances;
	}

	/**RESERVATION	r-c14d2325	728681021428	default
	INSTANCE	i-9ff34135	ami-c42fd9b3			pending		0		m1.medium	2015-06-18T09:01:14+0000	eu-west-1a	aki-71665e05			monitoring-disabled					ebs					paravirtual	xen		sg-d7fad0a3	default	false*/
	public static void launchInstance(DeploymentResources config, int numberOfInstances){
		
		String output = ExecuteCommand.executeCommand(
				"ec2-run-instances "+ config.getImageId() + " -n " + numberOfInstances + " -K " + config.getAccessKey() + " -C " + config.getCert() +
				" --instance-type " +  config.getInstanceType() + " --region eu-west-1"
			);
		
		System.out.println("Command result: " + output);
	}
	
	
	public static void terminateInstance(String instanceID){
		
		String output= ExecuteCommand.executeCommand("ec2-terminate-instances "+ instanceID);
		
		System.out.println("Command result: " + output);
		 
	}
	
	
	public static void startInstance(String instanceID){
		
		String output= ExecuteCommand.executeCommand("ec2-start-instances "+ instanceID);
	
		System.out.println("Command result: " + output);
	}
	
	
	/**
	 * stop instances in the default region
	 */
	public static void stopInstances(String instanceID){
	
		String output= ExecuteCommand.executeCommand("ec2-stop-instances "+ instanceID);
		
		System.out.println("Command result: " + output);
	
	}
	
	/**
	 * stops instances in a specific region
	 * @param region, e.g., eu-west-1 (Ireland)
	 */
	public static void stopInstances(String instanceID, String region){
		
		String output= ExecuteCommand.executeCommand("ec2-stop-instances "+ instanceID + " --region " + region );
		
		System.out.println("Command result: " + output);
	
	}

}
