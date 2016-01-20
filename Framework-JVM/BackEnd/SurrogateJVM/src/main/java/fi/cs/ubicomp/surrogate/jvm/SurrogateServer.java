package fi.cs.ubicomp.surrogate.jvm;

	
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import edu.ut.mobile.network.NetworkManagerServer;


/**
 * author Huber Flores
 */

public class SurrogateServer implements Runnable {


    protected int          serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    
   
    
	public SurrogateServer(int port){
        this.serverPort = port;
        
         
    }

    /**
     * starts the surrogate server, which executes the code
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
            
            System.out.println("Handling code offload request.");
            
        	new Thread(
        			new NetworkManagerServer(clientSocket)
        	).start();
    	    	
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
    
	 
}
