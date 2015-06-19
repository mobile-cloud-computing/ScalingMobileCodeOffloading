/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/


package cs.mc.ut.ee.cloud.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Huber Flores
 *
 */

public class InstanceLaunch {
	
	public String id = "",
			ami = "",
			state = "",
			code = "",
			type = "",	
			date = "",
			region = "",
			aki = "", 
			monitor = "",
			hardware = "";
    public boolean configured = false,
		listed = true;


 	public InstanceLaunch(String line){
 		String[] col = line.split("\t");
 		List<String> values = new ArrayList<String>();
 		
		if(col[0].equals("INSTANCE")){
			
			for (String value: col){
				if ((value.trim().length()>0) & (!value.equals("INSTANCE"))){
					values.add(value);
					//System.out.println(value);
					
				}
			}
			
			id = values.get(0);
			ami = values.get(1);
			state = values.get(2);
			code = values.get(3);
			type = values.get(4);
			date = values.get(5);
			region = values.get(6);
			aki = values.get(7);
			monitor = values.get(8);
			hardware = values.get(9);
		
 	    }
 	}

 	
 	public String toString(){
		return "INSTANCE\t" + id + "\t" + ami + "\t" + state + "\t" + code + "\t" + type + "\t" +
				date + "\t" + region + "\t" + aki + "\t" + monitor + "\t" + hardware;
	}

		
   /**
   * Instance parameters from Amazon EC2 Console (Launch an instance)
   * 
   * RESERVATION	r-c14d2325	728681021428	default
   INSTANCE	i-9ff34135	ami-c42fd9b3			pending		0		m1.medium	2015-06-18T09:01:14+0000	eu-west-1a	aki-71665e05			monitoring-disabled					ebs					paravirtual	xen		sg-d7fad0a3	default	false
   */

}
