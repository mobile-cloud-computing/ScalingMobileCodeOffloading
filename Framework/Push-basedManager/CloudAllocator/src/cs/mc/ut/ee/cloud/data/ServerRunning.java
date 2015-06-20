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
 * 
 * @author Huber Flores
 *
 */

public class ServerRunning {
	
	private String id;
	private String ami;
	private String type;
	private String region;
	private String date;
	private String ippublic;
	private String ipprivate;
	private String dnspublic;
	private String dnsprivate;
	
	
	public ServerRunning(String id, String ami, String type, String region, String date, String ippublic, String ipprivate, String dnspublic, String dnsprivate){
		this.id = id;
		this.ami = ami;
		this.type = type;
		this.region = region;
		this.date = date;
		this.ippublic = ippublic;
		this.ipprivate = ipprivate;
		this.dnspublic = dnspublic;
		this.dnsprivate = dnsprivate;
				
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getAmi(){
		return this.ami;
	}
	
	public String getType(){
		return this.type;
	}
	
	public String getRegion(){
		return this.region;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public String getIppublic(){
		return this.ippublic;
	}
	
	public String getIpprivate(){
		return this.ipprivate;
	}
	
	public String getDnspublic(){
		return this.dnspublic;
	}
	
	public String getDnsprivate(){
		return this.dnsprivate;
	}
	
	

}
