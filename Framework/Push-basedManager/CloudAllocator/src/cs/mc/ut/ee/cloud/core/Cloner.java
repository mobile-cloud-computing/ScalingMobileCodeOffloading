/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/


package cs.mc.ut.ee.cloud.core;


import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cs.mc.ut.ee.cloud.commons.Utilities;
import cs.mc.ut.ee.cloud.data.DeploymentResources;
import cs.mc.ut.ee.cloud.data.InstanceDescriptor;
import cs.mc.ut.ee.cloud.data.InstanceLaunch;
import cs.mc.ut.ee.cloud.data.ServerRunning;
import cs.mc.ut.ee.cloud.policies.Policy;

/**
 * author Huber Flores
 */

public class Cloner implements Runnable{
	
	static DeploymentResources fileConfig = DeploymentResources.getInstance();
	private Policy scaling;
	
	List<ServerRunning> servers = new ArrayList<ServerRunning>();
	
	private boolean service = false;
	
	
	public Cloner(Policy scaling){
		this.scaling = scaling;
		
		try {
			ServerRunning current = getLocalInstanceData();
			if (current!=null){
				servers.add(current);
				service = true;
			}else{
				service = false;
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		if (service==true){
			while(!scaling.getMonitoring()){
				
				if (scaling.scaleUp()){
					ServerRunning clon = replicateInstance();
					if (clon!=null){
						servers.add(clon);
					}else{
						System.out.println("Server could not be replicated");
					}
					scaling.coolDownTime(30000);
				}else{
					if (scaling.scaleDown()){
						removeInstance();
						scaling.coolDownTime(30000);
					}
				}			
				
			}	
			
		}else{
			scaling.setMonitoring(true);
			System.out.println("Replication service not available");
		}
			
		
	}
	
	
	public ServerRunning replicateInstance(){
		List<InstanceLaunch> newInstance = CloudCommands.launchInstance(fileConfig, 1);
		String id = newInstance.get(newInstance.size()-1).id;
		
		//Waiting time needed by the instance to pass from pending to running
		delayTime(3000);
		
		List<InstanceDescriptor> instances = CloudCommands.fetchRunningInstances(fileConfig, "");
		
		for (InstanceDescriptor i: instances){
		
			if (id.equals(i.id)){
				ServerRunning server = new ServerRunning(i.id, i.ami, i.type, i.region, i.date, i.ippublic, i.ipprivate, i.dnspublic, i.dnsprivate);
				return server;
			}
		}
		
		return null;
	}
	
	
	public void removeInstance(){
		//Removal must be based on a decision
		//Currently, it removes the clone
		String instanceId = servers.get(servers.size()-1).getId();
		
		CloudCommands.terminateInstance(instanceId);
	}
	
	
	/**
	 * Get the instance id of the server based on IP
	 * @return
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public ServerRunning getLocalInstanceData() throws UnknownHostException, SocketException{
		List<InstanceDescriptor> instances = CloudCommands.fetchRunningInstances(fileConfig, "");
		String myIP = Utilities.getIPFromInterface("lo");
		
		for (InstanceDescriptor i : instances){
			
			if (myIP.equals(i.ipprivate)){
				ServerRunning server = new ServerRunning(i.id, i.ami, i.type, i.region, i.date, i.ippublic, i.ipprivate, i.dnspublic, i.dnsprivate);
				
				return server;
			}
			
			
		}
		return null;
	}
	
	
	public void delayTime(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
