/*
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * Please send inquiries to huber AT ut DOT ee
 */

package cs.mc.ut.ee.manager;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;


//import cs.mc.ut.ee.allocation.Surrogate;
//import cs.mc.ut.ee.utilities.RemoteCommandActivation;

import edu.ut.mobile.network.NetInfo;
import edu.ut.mobile.network.Pack;
import edu.ut.mobile.network.ResultPack;
import fi.cs.ubicomp.database.traces.DBCollector;


/**
 * author Huber Flores
 */

public class CodeOffloadManager implements Runnable{

    protected Socket proxyConnection = null;
    protected String codeId   = null;
    protected String jar = null;
    protected String serverPort = null;
    protected String ipAddress = null;
        
	CodeRequestHandler jvmProcess;
	 
    Pack request = null;
    ResultPack response = null;
    
    InputStream in = null;
	OutputStream out = null;
	DynamicObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	
	double initialTime;
	
	String surrogateNumber;
	
	DBCollector dbcollector;
	
	//parameters to collect from the request.
	double paramResponseTime;
	int paramAccGroup;
	double paramEnergy;
	double paramRTT;


    public CodeOffloadManager(double time, Socket proxyConnection, Map<String, String> response, DBCollector dbcollector) {
        this.proxyConnection = proxyConnection;
        this.codeId = response.get("codeId");
        this.jar = response.get("jarFile");
        this.serverPort = response.get("serverPort");
        this.ipAddress = response.get("ip");
        this.initialTime = time;
        
        this.surrogateNumber = response.get("number");
        this.dbcollector = dbcollector;
                
    }

    public void run() {
        try {
        	//System.out.println("Handling a code offload request");
        
        	in = proxyConnection.getInputStream();
            out = proxyConnection.getOutputStream();

            oos = new ObjectOutputStream(out);
            ois = new DynamicObjectInputStream(in);
        	
            
            /*
             * Re-construct the object sent from the mobile
             * Classes are loaded as needed from jar files
             * Jar are created in the maven built process
             */
            
            //-TODO-(automate)
            //mobile application must be identified a priori
            
    
            ois.loadClassFromJar(jar);
            request = (Pack) ois.readObject();
            
            paramAccGroup = request.getDeviceAccGroup();
            paramEnergy = request.getDeviceEnergy();
            paramRTT = request.getDeviceRTT();
    
                       
            NetInfo.ipAddress = getIpAddress(ipAddress);
            
            if (NetInfo.ipAddress==null){
            	System.out.println("surrogate indefined");
            }
            
            long cloudProcessing = System.currentTimeMillis();
            jvmProcess = new CodeRequestHandler(getIpAddress(ipAddress), Integer.valueOf(serverPort), jar);
            jvmProcess.setOffloadRequest(request);
            jvmProcess.connect();
            jvmProcess.execute();
                     
            boolean success = true;
            long wait = System.currentTimeMillis();
            boolean invocation = false;
            while (!invocation){
            	
            	if (jvmProcess.getResultPack()!=null){
            		invocation = true;
            		//System.out.println("Result was found");
            	}
            	
            	if ((System.currentTimeMillis() - wait)>15000){ //responsiveness limit should not exceed the mobile counterpart
            		invocation = true;
            		success = false;
            		long cloudTime = System.currentTimeMillis() - cloudProcessing;
            		System.out.println("Surrogate: " + surrogateNumber+":"+serverPort + ", Result was null or the execution exceed the waiting time" + ","+ (System.currentTimeMillis() - initialTime - cloudTime) + ",0");
            		paramResponseTime = -1;
            	}
            	

            }
            
            
        	//send back the result to the client
        	response = jvmProcess.getResultPack();
        	oos.flush();
        	oos.writeObject(response);
    		oos.flush(); 
    		
    		
    		if (response==null){
    			System.out.println("null value in Surrogate: " +  surrogateNumber+":"+serverPort);
    		}
    		
    		
    		if (success==true){
    			long cloudTime = System.currentTimeMillis() - cloudProcessing;
    			paramResponseTime = (System.currentTimeMillis() - initialTime - cloudTime);
    			System.out.println("Surrogate: " + surrogateNumber + ", Respose was sent to the mobile: " +  response.getresult() + ","+ paramResponseTime + ",1");
    		}
    		
    		
            
            //System.out.println("Response sent to the mobile");
            //System.out.println(System.currentTimeMillis() - initialTime);
            
   
        } catch (IOException e) {
            returnnull(oos);
        } catch (ClassNotFoundException e1) {
            returnnull(oos);
        } catch (Exception e2){
        	e2.printStackTrace();
        } 
        finally {
        	
        	try {
				oos.close();
				ois.close();

                in.close();
                out.close();

                proxyConnection.close();

                oos = null;
                ois = null;

                in = null;
                out = null;
                proxyConnection = null;
                
                dbcollector.saveTrace(System.currentTimeMillis(), paramAccGroup, paramRTT, paramEnergy, paramResponseTime);
                
                
                
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
          
        }
    }
    

    public void returnnull(ObjectOutputStream oos){
        if(oos != null)
            try {
                oos.writeObject(null);
                oos.flush();
            } catch (IOException ex1) {

            }
    }
    
    
    public byte[] getIpAddress(String ipAddress){
    	String[] parts = ipAddress.split("\\.");
    	byte[] surrogateIP = new byte[] {Integer.valueOf(parts[0]).byteValue(),Integer.valueOf(parts[1]).byteValue(),Integer.valueOf(parts[2]).byteValue(),Integer.valueOf(parts[3]).byteValue()};;
    	
    	return surrogateIP;
    }
    
 
    
}
