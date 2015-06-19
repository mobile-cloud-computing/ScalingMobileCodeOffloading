package cs.mc.ut.ee.cloud.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import cs.mc.ut.ee.cloud.data.DeploymentResources;
import cs.mc.ut.ee.cloud.data.InstanceDescriptor;
import cs.mc.ut.ee.cloud.data.InstanceLaunch;

/**
 * 
 * @author Huber Flores
 *
 */

public class CloudCommands {

	/**
	 * Configures environmental variables for Amazon EC2 tools.
	 * @param private_key
	 * @param certificate_key
	 */
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
	
	/**
	 * Retrieves the instances running in a specific region 
	 * @param config
	 * @return
	 */
	public static List<InstanceDescriptor> fetchRunningInstances(DeploymentResources config, String region){
		List<InstanceDescriptor> instances = new ArrayList<InstanceDescriptor>();
		String[] payload = ExecuteCommand.executeCommand(
				"ec2-describe-instances --region eu-west-1 -K " + config.getAccessKey() + " -C " + config.getCert()
			).split("\n");
		
		/*String[] payload = ExecuteCommand.executeCommand(
				"ec2-describe-instances --region "+ region+ " -K " + config.getAccessKey() + " -C " + config.getCert()
			).split("\n");*/
		
		for( InstanceDescriptor tmp : instances ){
			tmp.listed = false;
		}
		
		for( String line : payload ){
			/** try to read INSTANCE information */
			InstanceDescriptor tmp = new InstanceDescriptor(line);
			/** only select the ones, which have state changed to running */
			if(tmp.state.equals("running") /*&& tmp.key.equals(config.getKeyName())*/){
				boolean found = false;
				for( InstanceDescriptor instance : instances ){
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

	/**
	 * Launch new instances and return the information associated to the instances
	 * @param config
	 * @param numberOfInstances
	 * @return
	 */
	public static List<InstanceLaunch> launchInstance(DeploymentResources config, int numberOfInstances){
		List<InstanceLaunch> instances = new ArrayList<InstanceLaunch>();
		String [] output = ExecuteCommand.executeCommand(
				"ec2-run-instances "+ config.getImageId() + " -n " + numberOfInstances + " -K " + config.getAccessKey() + " -C " + config.getCert() +
				" --instance-type " +  config.getInstanceType() + " --region eu-west-1"
			).split("\n");
		
		for( InstanceLaunch tmp : instances ){
			tmp.listed = false;
		}
		
		
		for( String line : output){
			InstanceLaunch tmp = new InstanceLaunch(line);
			if(tmp.state.equals("pending") /*&& tmp.key.equals(config.getKeyName())*/){
				instances.add(tmp);
				//System.out.println("Launched new instance:\n" + tmp);
			}
		}
		
		return instances;
	}
	
	/**
	 * Terminate a specific instance
	 * @param instanceID
	 */
	public static void terminateInstance(String instanceID){
		String output= ExecuteCommand.executeCommand("ec2-terminate-instances "+ instanceID);
		System.out.println("Command result: " + output);
		 
	}
	
	/**
	 * Start an instance in the default region
	 * @param instanceID
	 */
	public static void startInstance(String instanceID){
		
		String output= ExecuteCommand.executeCommand("ec2-start-instances "+ instanceID);
	
		System.out.println("Command result: " + output);
	}
	
	/**
	 * Start an instance in a specific region
	 * @param region
	 */
    public static void startInstance(String instanceID, String region){
		String output= ExecuteCommand.executeCommand("ec2-start-instances "+ instanceID + " --region " + region);
		System.out.println("Command result: " + output);
	}
	
	
	/**
	 * Stop instances in the default region
	 */
	public static void stopInstances(String instanceID){
		String output= ExecuteCommand.executeCommand("ec2-stop-instances "+ instanceID);
		System.out.println("Command result: " + output);
	
	}
	
	/**
	 * Stop instances in a specific region
	 * @param region, e.g., eu-west-1 (Ireland)
	 */
	public static void stopInstances(String instanceID, String region){
		String output= ExecuteCommand.executeCommand("ec2-stop-instances "+ instanceID + " --region " + region );
		System.out.println("Command result: " + output);
	
	}

}
