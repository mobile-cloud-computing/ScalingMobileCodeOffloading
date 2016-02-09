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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
//import fi.cs.ubicomp.database.traces.DBCollector;



/**
 * author Huber Flores
 */

public class FrontEnd implements Runnable{

    protected int          serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    
    protected int roundRobinCounter = 0;
    
    //DBCollector dbcollector;
    
    public static ArrayList<CodeResources> resources = new ArrayList<CodeResources>();;
    
    public FrontEnd(int port){
        this.serverPort = port;
        loadBackEndCodeInfo("surrogate.availability");
        
        //dbcollector = DBCollector.getInstance();
     
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
        	if (response!=null){ //no surrogates with the code required
        		
        		if (response.get("serverPort").length()>0){
        		
        			System.out.println("Handling code offload request");

        			//dbcollector.saveTrace(1.0, 1, 1.0, 1.0);        			
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
    
    
    /**
     * 
     * @param codeId is the key to search. It should be easily extended to support other filters, e.g., accelerationType
     * @return list of surrogates in which the code is present
     */
    private Map<String,String> getResource(String codeId){
    	
    	CodeResources code = findResources(codeId);
    	Surrogate surrogate = null;;
    	Map<String, String> response = null;
    	
    	roundRobinCounter++;
    	
    	if (roundRobinCounter>code.getBackEnd().size()-1){
    		roundRobinCounter = 0;
    	}
    	
    	
    	if (code!=null){
            response = new HashMap<String, String>();

    		//for (int i=0; i<app.getBackEnd().size(); i++){
    			//surrogate = app.getBackEnd().get(i);
    			surrogate = code.getBackEnd().get(roundRobinCounter);
    			
    			if (!surrogate.getCodePool().isEmpty()){
    				response.put("codeId", code.getCodeId());
    				response.put("ip", surrogate.getSurrogateIP());
    				response.put("serverPort", (String) surrogate.getCodePool().getFirst());
    				response.put("jarFile", surrogate.getJarFile());
    				
    				response.put("number", roundRobinCounter+"");
    				return response;
    			}
    		//}
    	}
    		
    	return response;
    }
  
    	
    
    
    
    private CodeResources findResources(String codeId){
    	for (int i= 0; i<resources.size(); i++){
    		if (resources.get(i).getCodeId().equals(codeId)){
    			return resources.get(i);
    		}
    	}
    	return null;
    }
    
    
    /**
     * 
     */
    private void addResource(String codeId, List<Surrogate> backEnd){
    	resources.add(new CodeResources(codeId, backEnd));
		
    }
    
    
     /**
      * 
      * 
      */
	 public void loadBackEndCodeInfo(String filePath){
		 InputStream in;
		 LinkedList<String> codePool=null;
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
							codePool = new LinkedList<String>();
							List<String> ports = map.get(appName).get(ip).get(attribute);
							Iterator value = ports.iterator();
							while (value.hasNext()){
								codePool.add(value.next()+"");
							}
						}else{
							if (attribute.equals("location")){
								List<String> location = map.get(appName).get(ip).get(attribute);
								path = location.get(0);
							}
						}

						
					}
					backEnd.add(new Surrogate(codePool, ip, path));

				}
				
				addResource(appName, backEnd);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 
	 }
	 
	 	 
}

