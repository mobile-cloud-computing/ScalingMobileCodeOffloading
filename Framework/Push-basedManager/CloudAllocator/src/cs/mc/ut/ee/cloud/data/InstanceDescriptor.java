/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/


package cs.mc.ut.ee.cloud.data;

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
				type = "",
				key = "", 
				region = "";
	public boolean configured = false,
			listed = true;
	
	public InstanceDescriptor(String line){
		String[] col = line.split("\t");
		if(col[0].equals("INSTANCE")){
			id = col[1];
			ami = col[2];
			dnsprivate = col[4];
			dnspublic = col[3];
			state = col[5];
			key = col[6];
			type = col[9];
			region = col[11];
			ippublic = col[16];
			ipprivate = col[17];
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
