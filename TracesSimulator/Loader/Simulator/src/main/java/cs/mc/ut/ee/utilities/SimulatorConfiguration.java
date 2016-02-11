package cs.mc.ut.ee.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SimulatorConfiguration {
	
	private int simulatorMode;
	
	private Properties props = new Properties();
	
	public SimulatorConfiguration(){
		try {
			props.load(new FileInputStream(Commons.conf));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
			simulatorMode = 0;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		loadConfiguration();
    	
	}
	
	public void loadConfiguration() {
		
		try{
			simulatorMode = Integer.valueOf(props.getProperty("mode"));
			
			if ((simulatorMode>=0) & (simulatorMode<=2)){
				
			}else{
				simulatorMode = 0; //Default mode
			}
			
			
		}catch(NumberFormatException e){
			simulatorMode = 0; //Default mode
		}
		
	}
	
	
	public int getSimulatorMode(){
		return simulatorMode;
	}
	

}
