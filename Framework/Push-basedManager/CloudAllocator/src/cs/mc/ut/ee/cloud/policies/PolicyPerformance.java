/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/

package cs.mc.ut.ee.cloud.policies;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cs.mc.ut.ee.cloud.core.ExecuteCommand;
import cs.mc.ut.ee.cloud.data.DeploymentResources;

/**
 * 
 * Simple procedures to get CPU and memory usage using Systat (SAR).
 * It is expected that in practice, performance information is
 * obtained by using other specialized tools, such as CollectD, etc.
 *
 */

public class PolicyPerformance extends Policy {

	DeploymentResources fileConfig = DeploymentResources.getInstance();
		
	@Override
	public boolean scaleUp() {
		// TODO Auto-generated method stub
	    boolean scale = false;
		
		String cpuIdle = getCPULevels(2,3);
		if (cpuIdle!=null){
			int cpuIdleValue = Integer.valueOf(cpuIdle);
			int cpuUtilization = 100 - cpuIdleValue;
			
			if (cpuUtilization > fileConfig.getCpuUpper()){
				scale = true;
			}
		}
		
		return scale;
	}

	@Override
	public boolean scaleDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMonitoring(boolean value) {
		// TODO Auto-generated method stub
		this.monitoring = value;
	}

	@Override
	public boolean getMonitoring() {
		// TODO Auto-generated method stub
		return this.monitoring;
	}


	@Override
	public void coolDownTime(long time) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void monitoringTime(long time) {
		// TODO Auto-generated method stub
		
	}
	

	
	/**
	 * 
	 * @param interval is the period of time
	 * @param times is the number of measurements in that interval
	 */
	public String getCPULevels(int interval, int times){
		String [] output = ExecuteCommand.executeCommand("sar -u -o sarfile "+ interval+ " "+ times).split("\n");
		Pattern whitespace = Pattern.compile("\\s+");
		Matcher matcher;
		String newLine = ""; 
		String averageCpuIdle = null;
		
		
		for (String line: output){
			matcher = whitespace.matcher(line);
			while (matcher.find()){
				newLine = matcher.replaceAll("\t");
			}

			
			String[] linePart = newLine.split("\t");
			
			if (linePart[0].endsWith("Average:")){
				averageCpuIdle = linePart[linePart.length-1];
			}
			
		}
		
		return averageCpuIdle;
	}
	
	
	
	
	
	/**
	 * static information about multiple machine features.
	 */
	private static void printUsage() {
		  OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		  for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
		    method.setAccessible(true);
		    if (method.getName().startsWith("get") 
		        && Modifier.isPublic(method.getModifiers())) {
		            Object value;
		        try {
		            value = method.invoke(operatingSystemMXBean);
		        } catch (Exception e) {
		            value = e;
		        } 
		        System.out.println(method.getName() + " = " + value);
		    } 
		  } 
	}

}
