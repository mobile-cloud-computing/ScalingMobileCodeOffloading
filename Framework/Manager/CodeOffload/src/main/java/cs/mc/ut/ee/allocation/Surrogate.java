package cs.mc.ut.ee.allocation;

import java.util.Stack;

/**
 * 
 * @author Huber Flores
 *
 */

public class Surrogate {
	String jarFile;  //code to reconstruct the request in each component of the system
	
	Stack apkPool;   //ports in which the app is listening
	
	String ipAddress; //surrogate address
	
	/*
	 *String accelerationType;       //other attributes about the server can be defined 
	 */
	  
	
	public Surrogate(Stack apkPool, String ipAddress, String jarFile){
		setApkPool(apkPool);
		setSurrogateIP(ipAddress);
		setJarFile(jarFile);
	}
	
	public void setApkPool(Stack apkPool){
		this.apkPool = apkPool;
	}
	
	public void setSurrogateIP(String surrogateIP){
		this.ipAddress = surrogateIP;
	
	}
	
	public Stack getApkPool(){
		return this.apkPool;
	}
	
	public String getSurrogateIP(){
		return this.ipAddress;
	}
	
	public String getJarFile(){
		return this.jarFile;
	}

	
	public void setJarFile(String jarFile){
		this.jarFile = jarFile;
	}

}
