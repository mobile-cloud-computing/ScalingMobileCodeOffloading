/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/

package cs.mc.ut.ee.cloud.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import cs.mc.ut.ee.cloud.commons.Utilities;

/**
 * author Huber Flores
 */

public class DeploymentResources {
	
	public static DeploymentResources instance;
	
	private Properties props = new Properties();
	
	private String imageId;
	private String key;
	private String cert;
	private String keyName;
	private String instanceType;
	private int minServers;
	private int maxServers;
	private double cpuLower;
	private double cpuUpper;
	
	
	private DeploymentResources(){ 
		setup();
	}
	
	
	public static synchronized DeploymentResources getInstance(){
		if (instance==null){
			instance = new DeploymentResources();
			return instance;
		}
		return instance;
	}
	
	
	public void setup(){
		try {
			props.load(new FileInputStream(Utilities.deployment));
			imageId = props.getProperty("imageId");
			key = props.getProperty("key");
			cert = props.getProperty("cert");
			keyName = props.getProperty("keyName");
			instanceType = props.getProperty("instanceType");
			minServers = Integer.valueOf(props.getProperty("minServers"));
			maxServers = Integer.valueOf(props.getProperty("maxServers"));
			cpuLower =  Double.valueOf(props.getProperty("cpuLower"));
			cpuUpper =  Double.valueOf(props.getProperty("cpuUpper"));
						
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public String getImageId(){
		return imageId;
	}
	
	public String getAccessKey(){
		return key;
	}
	
	public String getCert(){
		return cert;
	}
	
	public String getKeyName(){
		return keyName;
	}
	
	public String getInstanceType(){
		return instanceType;
	}
	
	public void setInstanceType(String type){
		instanceType = type;
	}
	
	
	public int getMinServers(){
		return minServers;
	}
	
	public void setServers(int min){
		minServers = min;
	}
	
	public int getMaxServers(){
		return maxServers;
	}
	
	public void setMaxServers(int max){
		maxServers = max;
	}
	
	public double getCpuLower(){
		return cpuLower;
	}
	
	public void setCpuLower(double lower){
		cpuLower = lower;
	}
	
	public double getCpuUpper(){
		return cpuUpper;
	}
    
	public void setCpuUpper(double upper){
		cpuUpper = upper;
	}

}



