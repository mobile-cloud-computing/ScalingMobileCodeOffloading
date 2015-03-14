/*
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * Please send inquiries to huber AT ut DOT ee
 */

package cs.mc.ut.ee.allocation;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Huber Flores
 *
 */

public class APKData {
	
	@SerializedName("appName")
	public String appName;
	
	@SerializedName("ports")
	public List<String> ports;
	
	@SerializedName("location")
	public String location;
	
	public List<String> getPorts(){
		return this.ports;
	}

}
