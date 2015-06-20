package cs.mc.ut.ee.cloud.commons;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class Utilities {

	public static String deployment = "deployment.properties";
	
	public static String defaultAttribute = "defaul";
	
	
	public static <T> void iterateList(List<T> list){
		Iterator<T> iterator = list.iterator();
		
		while (iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
	
	public static String getIPFromInterface(String ni) throws UnknownHostException, SocketException{
   	 
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

    public static String getIPAddress(NetworkInterface e){
	Enumeration<InetAddress> a = e.getInetAddresses();
    for (; a.hasMoreElements();)
    {
    	InetAddress addr = a.nextElement(); //IPv6 
    	addr = a.nextElement(); //IPv4
    	
    	return	addr.getHostAddress();
    	    	        	
    }
    
    return null;
}

	
}
