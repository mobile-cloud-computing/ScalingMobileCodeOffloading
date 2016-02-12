/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/

package edu.ut.mobile.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cs.mc.ut.ee.utilities.Commons;
import cs.mc.ut.ee.utilities.ParametersSimulator;
import cs.mc.ut.ee.utilities.WorkLoad;


public class Pack implements Serializable{
    private static final long serialVersionUID = 1;
    String functionName = null;
    Class stateType = null;
    Object state = null;
    Object[] paramValues = null;
    Class[] paramTypes = null;
    
    
    //Timestamps
    List<String> timestamps = new ArrayList<String>();
    
    int accGroup = 0;
    double RTT = 0;
    double energy = 0;
    String userId = "";
    

    public Pack(String functionName, Class stateType, Object state, Object[] paramValues, Class[] FuncDTypes) {
        this.functionName = functionName;
        this.stateType = stateType;
        this.state = state;
        this.paramValues = paramValues;
        this.paramTypes = FuncDTypes;
        timestamps.add(System.currentTimeMillis()+",client1");
        
        
        this.RTT = ParametersSimulator.GenerateDouble(0, 1000);
        this.energy = ParametersSimulator.GenerateDouble(0, 100);
        
        
        if (Commons.mode==0){
        	String[] device = ParametersSimulator.getUser();
        
        	if ((device[0]!=null) & (device[1]!=null)){
        		this.accGroup = Integer.parseInt(device[0]);
        		this.userId = device[1];
        	}else{
        		this.accGroup = 0;
        		this.userId = "-";
        	}
        }else{
        	if (Commons.mode==1) {
        		String[] device = ParametersSimulator.getSnapShotUser();
        		int promote = ParametersSimulator.getRandomNumber(5); //probability to promote 1/5
        		
        		if (promote==1){ //promotion
        			this.accGroup = Integer.parseInt(device[1]);
            		this.userId = device[2];
        			
        			if (this.accGroup<=WorkLoad.getInstance().getNumberOfGroups()){
        				this.accGroup = accGroup + 1;
        				
        				int index = WorkLoad.getInstance().getSnapShotUserIndex(device[0]);
        				System.out.println("old: " + device[0] + "," + device[1] + "," + device[2]);
        				System.out.println("new: " + index +  "," + device[0] + "," +  accGroup + "," + device[2]);
        				
        				WorkLoad.getInstance().promoteSnapShotUser(index, device[0], userId, accGroup+"");
        				
        			}
        		}else{ //no promotion

        			this.accGroup = Integer.parseInt(device[1]);
            		this.userId = device[2];
        			
        		}
        		
        	}else{
        		this.accGroup = 0;
        		this.userId = "-";
        	}
        }
        
    }

    public String getfunctionName(){
        return functionName;
    }
    
    public Class getstateType(){
        return stateType;
    }

    public Object[] getparamValues(){
        return paramValues;
    }

    public Class[] getparamTypes(){
        return paramTypes;
    }

    public Object getstate(){
        return state;
    }
    
    public List<String> getTimeStamps(){
    	return timestamps;
    }
    
    public int getDeviceAccGroup(){
    	return accGroup;
    }
    
    public double getDeviceRTT(){
    	return RTT;
    }
    
    public double getDeviceEnergy(){
    	return energy;
    }
    
    public String getDeviceId(){
    	return userId;
    }
    
}
