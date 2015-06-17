/*
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * Please send inquiries to huber AT ut DOT ee
 */

package cs.mc.ut.ee.push.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cs.mc.ut.ee.allocation.Surrogate;
import cs.mc.ut.ee.manager.AppResources;
import cs.mc.ut.ee.push.manager.CodeOffloadManager;

/**
 * 
 * @author Huber Flores
 *
 */

public class PushManager implements Runnable{
	
	protected int          serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    
    public static ArrayList<AppResources> resources = new ArrayList<AppResources>();;
	
	public PushManager(int port){
		this.serverPort = port;
		loadSurrogateAPKS("apks.availability");
		listAPKsInBackEnd("g_chess");
	}

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
        		
        		if (response.get("apkPort").length()>0){
        		
        			new Thread(
        					new CodeOffloadManager(System.currentTimeMillis(),
        							clientSocket, response)
        					).start();
    
        		}else{
        			System.out.println("No slots available for code offloading!,0");
        		}
        		
    		}else{
    			System.out.println("No surrogate available for code offloading!,0");
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
    
    private Map<String,String> getResource(String appName){
    	
    	AppResources app = findResources(appName);
    	Surrogate surrogate = null;;
    	Map<String, String> response = null;
    	
    	    	
    	if (app!=null){
            response = new HashMap<String, String>();

                //There should be only one surrogate in this deployment
    			surrogate = app.getBackEnd().get(app.getBackEnd().size()-1);
    			
    			if (!surrogate.getApkPool().isEmpty()){
    				response.put("app", app.getAppName());
    				response.put("ip", surrogate.getSurrogateIP());
    				response.put("apkPort", (String) surrogate.getApkPool().removeFirst());
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
   
   
   private void addResource(String appName, List<Surrogate> backEnd){
   	resources.add(new AppResources(appName, backEnd));
		
   }
   
   
   public void loadSurrogateAPKS(String filePath){
		 InputStream in;
		 LinkedList<String> apkPool=null;
		 String path="";
		 List<Surrogate> backEnd = null;
		 String ip="";
		 try{
				ip = getIPFromInterface("wlan0");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		    }
		 
		try {
			in = new FileInputStream(new File(filePath));
			Gson gson = new Gson();		
			Type mapType = new TypeToken<Map<String, Map<String, List<String>>>>() {}.getType();
			Map<String,Map<String,List<String>>> map = gson.fromJson(new InputStreamReader(in), mapType);
			
			Set<String> apps = map.keySet(); 
			
			Iterator<String> iterator = apps.iterator();
			
			Set<String> attributesValues;
			Iterator<String> subIterator;
			
			
			while(iterator.hasNext()){
				String appName = iterator.next();
				//System.out.println(appName);
			
				backEnd = new ArrayList<Surrogate>();
				attributesValues = map.get(appName).keySet();
				subIterator = attributesValues.iterator();
				
				while(subIterator.hasNext()){
					String attribute = subIterator.next();
					
					if (attribute.equals("ports")){
						apkPool = new LinkedList<String>();
						List<String> ports = map.get(appName).get(attribute);
						Iterator value = ports.iterator();
						while (value.hasNext()){
							apkPool.add(value.next()+"");
						}
					}else{
						if (attribute.equals("location")){
							List<String> location = map.get(appName).get(attribute);
							path = location.get(0);
						}
					}
				}
				
				backEnd.add(new Surrogate(apkPool, ip, path));
				addResource(appName, backEnd);
			}
			
			//System.out.println("Surrogate apps are loaded" + resources.toString());
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 
	 }

    
    public String getIPFromInterface(String ni) throws UnknownHostException, SocketException{
    	 
    	    if (ni.equals("lo")){
    	    	return InetAddress.getLocalHost().getHostAddress();
    	    }
    	 
    	    //other Network interfaces
    	    Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
    	    for (; n.hasMoreElements();)
    	    {
    	        NetworkInterface e = n.nextElement();
    	        
    	        if (e.getDisplayName().equals(ni)){
    	        	return getIPAddress(e);
    	        }

    	        
    	    }
    	    
    	   return null;
    }
    
    public String getIPAddress(NetworkInterface e){
    	Enumeration<InetAddress> a = e.getInetAddresses();
        for (; a.hasMoreElements();)
        {
        	InetAddress addr = a.nextElement(); //IPv6 
        	addr = a.nextElement(); //IPv4
        	
        	return	addr.getHostAddress();
        	    	        	
        }
        
        return null;
    }
    
  
    /*
     * This should be extracted to a separate class
     */
    
    public void listAPKsInBackEnd(String appName){
 	    new Thread(
	                new CheckPortAvailability(appName)
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
