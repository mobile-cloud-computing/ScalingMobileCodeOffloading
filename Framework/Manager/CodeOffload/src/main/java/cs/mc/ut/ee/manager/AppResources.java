/*
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * Please send inquiries to huber AT ut DOT ee
 */
package cs.mc.ut.ee.manager;

import java.util.List;


import cs.mc.ut.ee.allocation.Surrogate;

/*
 * author Huber Flores
 */

public class AppResources {
	
	String appName;  //name of the app
	
	List<Surrogate> backEnd;
	
	
	public AppResources(String appName, List<Surrogate> backEnd){
		setAppName(appName);
		setBackEnd(backEnd);
	}
	
	
	public String getAppName(){
		return this.appName;
	}
	

	public void setAppName(String appName){
		this.appName = appName;
	}
	
	public List<Surrogate> getBackEnd(){
		return this.backEnd;
	}
	
	public void setBackEnd(List<Surrogate> backEnd){
		this.backEnd = backEnd;
	}
	

}
