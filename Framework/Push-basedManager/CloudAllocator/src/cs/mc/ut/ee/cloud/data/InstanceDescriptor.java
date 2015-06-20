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
 *  author Huber Flores
 */

public class InstanceDescriptor {

	public String id = "",
				ami = "",
				ipprivate = "",
				ippublic = "",
				dnsprivate = "",	/** use this as ID */
				dnspublic = "",
				state = "",
				code = "",
				date = "",
				type = "",
				key = "", 
				monitor = "",
				region = "",
				hardware = "";
	public boolean configured = false,
			listed = true;
	
	public InstanceDescriptor(String line){
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
			dnsprivate = values.get(2);
			dnspublic = values.get(3);
			state = values.get(4);
			code = values.get(6);
			type = values.get(7);
			date = values.get(8);
			region = values.get(9);
			key = values.get(10);
			monitor = values.get(11);
			ippublic = values.get(12);
			ipprivate = values.get(13);
			hardware = values.get(14);

			
		}
	}
	
	public String toString(){
		return "INSTANCE\t" + id + "\t" + ami + "\t" + ippublic + "\t" + ipprivate + "\t" + key + "\t" +
				state + "\t" + type + "\t" + region + "\t" + dnspublic + "\t" + dnsprivate;
	}
	
	/**
	 * Instance parameters from Amazon EC2 Console (describe instances)
	 * 
	INSTANCE	i-919133fc	ami-c42fd9b	ec2-xxx-xxx-xxx-xxx.compute-1.amazonaws.com	
	domU-xx-xx-xx-xx-xx-xx.compute-1.internal	running	dalvikx86	0		m1.small	
	2014-01-06T09:42:08+0000	us-east-1b		aki-88aa75e1			
	monitoring-disabled		54.xxx.xxx.xxx		10.xxx.xxx.xxx		ebs
	* 
	*/	
	
}
