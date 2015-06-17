/*
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * Please send inquiries to huber AT ut DOT ee
 */

package cs.mc.ut.ee.push.manager;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;


import cs.mc.ut.ee.allocation.Surrogate;
import cs.mc.ut.ee.manager.APKHandler;
import cs.mc.ut.ee.manager.AppResources;
import cs.mc.ut.ee.manager.DynamicObjectInputStream;
import cs.mc.ut.ee.utilities.RemoteCommandActivation;

import edu.ut.mobile.network.NetInfo;
import edu.ut.mobile.network.Pack;
import edu.ut.mobile.network.ResultPack;

/**
 * author Huber Flores
 */

public class CodeOffloadManager implements Runnable{

    protected Socket proxyConnection = null;
    protected String mobileApp   = null;
    protected String jar = null;
    protected String apk = null;
    protected String ipAddress = null;
        
	APKHandler dalvikProcess;
	 
    Pack request = null;
    ResultPack response = null;
    
    InputStream in = null;
	OutputStream out = null;
	DynamicObjectInputStream ois = null;
	ObjectOutputStream oos = null;
	
	double initialTime;
	
	String surrogateNumber;


    public CodeOffloadManager(double time, Socket proxyConnection, Map<String, String> response) {
        this.proxyConnection = proxyConnection;
        this.mobileApp = response.get("app");
        this.jar = response.get("jarFile");
        this.apk = response.get("apkPort");
        this.ipAddress = response.get("ip");
        this.initialTime = time;
        
        this.surrogateNumber = response.get("number");
                
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
    
            
            /*
             * apkFiles stack has the available apps to use in a specific port
             * The idea is that the file pop is utilized for invocation
             * and once is finished is push again into the stack
             * BlubbleSort_Server_6001
             * BlubbleSort_Server_6002, etc.
             */

            //-TODO-(introduce a delay)
        	//Process send = Runtime.getRuntime().exec(new String[] {"sh", "-c", "cd /home/huber/Desktop/TechnicalInformation/x86Image/android-x86/; ./rund.sh -cp " + apk +" " + "edu.ut.mobile.network.Main"});
        	
        	
        	/**
        	 * Proceed to connect to the APK in order to execute the code.
        	 * Notice that the IP address of the invocation defines in which server the apk is located.
        	 */
  

            /**
             * Support for multi-servers 
            byte[] surrogate = {Integer.valueOf("192").byteValue(),
            	Integer.valueOf("168").byteValue(),
            	Integer.valueOf("1").byteValue(),
            	Integer.valueOf("65").byteValue()};;
            
            dalvikProcess = new APKHandler(surrogate, getPort(apk), jar);*/
            
                       
            NetInfo.ipAddress = getIpAddress(ipAddress);
            
            if (NetInfo.ipAddress==null){
            	System.out.println("surrogate indefined");
            }
            
            long cloudProcessing = System.currentTimeMillis();
            dalvikProcess = new APKHandler(getIpAddress(ipAddress), Integer.valueOf(apk), jar);
            dalvikProcess.setOffloadRequest(request);
            dalvikProcess.connect();
            dalvikProcess.execute();
                     
            boolean success = true;
            long wait = System.currentTimeMillis();
            boolean invocation = false;
            while (!invocation){
            	
            	if (dalvikProcess.getResultPack()!=null){
            		invocation = true;
            		//System.out.println("Result was found");
            	}
            	
            	if ((System.currentTimeMillis() - wait)>15000){ //responsiveness limit should not exceed the mobile counterpart
            		invocation = true;
            		success = false;
            		long cloudTime = System.currentTimeMillis() - cloudProcessing;
            		System.out.println("Surrogate: " + surrogateNumber+":"+apk + ", Result was null or the execution exceed the waiting time" + ","+ (System.currentTimeMillis() - initialTime - cloudTime) + ",0");
            	}
            	

            }
            
            
        	//send back the result to the client
        	response = dalvikProcess.getResultPack();
        	oos.flush();
        	oos.writeObject(response);
    		oos.flush(); 
    		
    		
    		if (response==null){
    			System.out.println("null value in Surrogate: " +  surrogateNumber+":"+apk);
    		}
    		
    		
    		if (success==true){
    			long cloudTime = System.currentTimeMillis() - cloudProcessing;
        		System.out.println("Surrogate: " + surrogateNumber + ", Respose was sent to the mobile: " +  response.getresult() + ","+ (System.currentTimeMillis() - initialTime - cloudTime) + ",1");	
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
                
                
                leavePortListeningForNextRequest();
                
                
                
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
          
        }
    }
    
    
    public void leavePortListeningForNextRequest(){
    	
    	try {
    		//RemoteCommandActivation.activateAPK(ipAddress, "huber", "thisisnotthepass", "cd " + jar +";" + "./rund.sh -cp " + mobileApp+"_Server__"+ apk + ".apk" +" " + "edu.ut.mobile.network.Main");
    		RemoteCommandActivation.activateAPK(ipAddress, "huber", "thisisnotthepass", "cd " + "/home/ubuntu/android-x86/" +";" + "nohup ./rund.sh -cp " + mobileApp+"_Server__"+ apk + ".apk" +" " + "edu.ut.mobile.network.Main &");
			Thread.sleep(500);
			pushResource(mobileApp, ipAddress, apk);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    private void pushResource(String appName, String ipAddress, String apk){	
    	Surrogate surrogate = null;;
      
    	for (int i=0; i<findResources(appName).getBackEnd().size(); i++){
    	   surrogate = findResources(appName).getBackEnd().get(i);
    			
    	   if (surrogate.getSurrogateIP().equals(ipAddress)){
    		   findResources(appName).getBackEnd().get(i).getApkPool().add(apk);
    	   }
    	}		
 
    }
    
    private AppResources findResources(String appName){
    	for (int i= 0; i<PushManager.resources.size(); i++){
    		if (PushManager.resources.get(i).getAppName().equals(appName)){
    			return PushManager.resources.get(i);
    		}
    	}
    	return null;
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
