package cs.mc.ut.ee.cloud.commons;

import java.util.Iterator;
import java.util.List;

public class Utilities {

	public static String deployment = "deployment.properties";
	
	
	public static <T> void iterateList(List<T> list){
		Iterator<T> iterator = list.iterator();
		
		while (iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
	
}
