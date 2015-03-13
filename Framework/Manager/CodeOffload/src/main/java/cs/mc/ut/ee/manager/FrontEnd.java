/*
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * Please send inquiries to huber AT ut DOT ee
 */

package cs.mc.ut.ee.manager;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cs.mc.ut.ee.allocation.Surrogate;


/**
 * author Huber Flores
 */

public class FrontEnd implements Runnable{

    protected int          serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    
    public static ArrayList<AppResources> resources = new ArrayList<AppResources>();;
    
    public FrontEnd(int port){
        this.serverPort = port;
        loadBackEndAPKS("apks.availability");
        listAPKsInBackEnd("g_chess");   
    }

    /**
     * starts the front-end, which distributed the requests to the back-end
     */
    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
                		
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            
            Map<String, String> response = getResource("g_chess");
        	if (response!=null){ //no surrogates with the app
        	    new Thread(
                        new CodeOffloadManager(System.currentTimeMillis(),
                            clientSocket, response)
                    ).start();
    
        	    
    		}else{
    			System.out.println("No slots available for code offloading!");
    		}
    	
        }
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 6000", e);
        }
    }
    
    
    /**
     * 
     * @param appName is the key to seach. It should be easily extended to support other filters, e.g., accelerationType
     * @return list of surrogates in which the app is present
     */
    private Map<String,String> getResource(String appName){
    	
    	AppResources app = findResources(appName);
    	Surrogate surrogate = null;;
    	Map<String, String> response = null;
    	
    	
    	if (app!=null){
            response = new HashMap<String, String>();

    		for (int i=0; i<app.getBackEnd().size(); i++){
    			surrogate = app.getBackEnd().get(i);
    			
    			if (!surrogate.getApkPool().isEmpty()){
    				response.put("app", app.getAppName());
    				response.put("ip", surrogate.getSurrogateIP());}
    				response.put("apkPort", (String) surrogate.getApkPool().pop());
    				response.put("jarFile", surrogate.getJarFile());
    				
    				return response;
    			}
    			
    	}
    		
    	return response;
    }
  
    	
    
    
    
    private AppResources findResources(String appName){
    	for (int i= 0; i<resources.size(); i++){
    		if (resources.get(i).getAppName().equals(appName)){
    			return resources.get(i);
    		}
    	}
    	return null;
    }
    
    
    /**
     * 
     * @param appName is the id of the app
     * @param backEnd contains the information about the surrogates that contain the app
     */
    private void addResource(String appName, List<Surrogate> backEnd){
    	resources.add(new AppResources(appName, backEnd));
		
    }
    
    
     /**
      * 
      * @param filePath contains the information about the APK files in each surrogate
      * AppName<IP<Ports,<value1,value2,...,valueN>>>
      * 
      * An APK file (AppName) can be in different surrogates (IPs), each APK file listen(s) in multiple ports (Ports).
      * 
      */
	 public void loadBackEndAPKS(String filePath){
		 InputStream in;
		 Stack apkPool=null;
		 String path="";
		 List<Surrogate> backEnd = null;
		 
		try {
			in = new FileInputStream(new File(filePath));
			Gson gson = new Gson();		
			Type mapType = new TypeToken<Map<String,Map<String, Map<String, List<String>>>>>() {}.getType();
			Map<String,Map<String, Map<String,List<String>>>> map = gson.fromJson(new InputStreamReader(in), mapType);
			
			Set<String> apps = map.keySet(); 
			
			Iterator<String> iterator = apps.iterator();
			
			Set<String> surrogateIP, attributesValues;
			Iterator<String> subIterator,subsubIterator;
			while(iterator.hasNext()){
				String appName = iterator.next();
				//System.out.println(appName);
				
				surrogateIP = map.get(appName).keySet();
				subIterator = surrogateIP.iterator();
				backEnd = new ArrayList<Surrogate>();
				
				while(subIterator.hasNext()){
					String ip = subIterator.next();
					attributesValues = map.get(appName).get(ip).keySet();
					subsubIterator = attributesValues.iterator();
					
					while(subsubIterator.hasNext()){
						String attribute = subsubIterator.next();
						
						if (attribute.equals("ports")){
							apkPool = new Stack();
							List<String> ports = map.get(appName).get(ip).get(attribute);
							Iterator value = ports.iterator();
							while (value.hasNext()){
								apkPool.push(value.next());
							}
						}else{
							if (attribute.equals("location")){
								List<String> location = map.get(appName).get(ip).get(attribute);
								path = location.get(0);
							}
						}

						
					}
					backEnd.add(new Surrogate(apkPool, ip, path));

				}
				
				addResource(appName, backEnd);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 
	 }
	 
	 
	 
	 public void listeningAPKsFromApp(String appName){
		 AppResources app = findResources(appName);
		 
		 System.out.println("Active surrogates: " + appName);
		 for (int i=0; i<app.getBackEnd().size(); i++){
			 
			 System.out.println("IP address: " + app.getBackEnd().get(i).getSurrogateIP() + ", Ports listening: " + app.getBackEnd().get(i).getApkPool().toString());
			 
			
			 
		 }
	 }
	 
	 	 
	 /**
	  * 
	  * @param check the availability of apks from an appName listening in the back-end.
	  */
	 public void listAPKsInBackEnd(String appName){
 	    new Thread(
	                new CheckPortAvailability("g_chess")
	            ).start();
	 }
	 
	 	    
	 
	 class CheckPortAvailability implements Runnable {
	    	
	    	private String appName;
	    	
	    	CheckPortAvailability(String appName){
	    		this.appName = appName;
	    	}
	    	
	    	
	    	
			public void run() {
		
				while(!isStopped()){			
					AppResources app = findResources(appName);
					 
					 System.out.println("Active surrogates: " + appName);
					 for (int i=0; i<app.getBackEnd().size(); i++){
						 
						 System.out.println("IP address: " + app.getBackEnd().get(i).getSurrogateIP() + ", Ports listening: " + app.getBackEnd().get(i).getApkPool().toString());
						 
					 }
					
					 try {
						 Thread.sleep(5000);
				     }catch (InterruptedException e) {
						 e.printStackTrace();
				     }
					
				}
				
			}
	    	
	    }

	 
	 
	 
}

