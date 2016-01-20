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

/**
 * author Huber Flores
 */

public class CodeResources {
	
	String codeId;  //portion of code
	
	List<Surrogate> backEnd;
	
	
	public CodeResources(String codeId, List<Surrogate> backEnd){
		setCodeId(codeId);
		setBackEnd(backEnd);
	}
	
	
	public String getCodeId(){
		return this.codeId;
	}
	

	public void setCodeId(String appName){
		this.codeId = appName;
	}
	
	public List<Surrogate> getBackEnd(){
		return this.backEnd;
	}
	
	public void setBackEnd(List<Surrogate> backEnd){
		this.backEnd = backEnd;
	}
	

}
