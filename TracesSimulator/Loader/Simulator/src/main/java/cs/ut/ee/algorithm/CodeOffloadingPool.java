package cs.ut.ee.algorithm;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CodeOffloadingPool {
	
	private static final String requestsPackage = "fi.cs.ubicomp.taskpool";
    

	private static List<String> /*List<Class>*/ getClassesForPackage(String pkgName) {
	    String pkgname = pkgName;

	    List<Class> classes = new ArrayList<Class>();
	    List<String> tmp = new ArrayList<String>();

	    File directory = null;
	    String fullPath;
	    String relPath = pkgname.replace('.', '/');

	    URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);

	    if (resource == null) {
	        throw new RuntimeException("No resource for " + relPath);
	    }
	    fullPath = resource.getFile();


	    try {
	        directory = new File(resource.toURI());
	    } catch (URISyntaxException e) {
	        throw new RuntimeException(pkgname + " (" + resource + ") does not appear to be a valid URL / URI.", e);
	    } catch (IllegalArgumentException e) {
	        directory = null;
	    }


	    if (directory != null && directory.exists()) {


	        String[] files = directory.list();
	        for (int i = 0; i < files.length; i++) {

	            if (files[i].endsWith(".class")) {

	                String className = pkgname + '.' + files[i].substring(0, files[i].length() - 6);

	                try {
	                	
	                    classes.add(Class.forName(className));
	                    tmp.add(className);
	                    
	                } catch (ClassNotFoundException e) {
	                    throw new RuntimeException("ClassNotFoundException loading " + className);
	                }
	            }
	        }
	    } else {
	        try {
	            String jarPath = fullPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
	            JarFile jarFile = new JarFile(jarPath);
	            Enumeration<JarEntry> entries = jarFile.entries();
	            while (entries.hasMoreElements()) {
	                JarEntry entry = entries.nextElement();
	                String entryName = entry.getName();
	                if (entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
	                    String className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");

	                    try {
	                        classes.add(Class.forName(className));
	                    } catch (ClassNotFoundException e) {
	                        throw new RuntimeException("ClassNotFoundException loading " + className);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            throw new RuntimeException(pkgname + " (" + directory + ") does not appear to be a valid package", e);
	        }
	    }
	    return tmp;
	    //return classes;
	}
	
	
	
	public static List<String> getComputationalWorkload(){
		return getClassesForPackage(requestsPackage);
	}
	
	
	
	/*Demo*/
	/*
	public static void main(String[] args){		
		List<String> list = CodeOffloadingPool.getClassesForPackage(requestsPackage);
		
		
		for (String task: list){
			try {
				
				new Thread((Runnable)ClassLoader.getSystemClassLoader().loadClass(task).newInstance()).start();
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}*/
		
	

}
