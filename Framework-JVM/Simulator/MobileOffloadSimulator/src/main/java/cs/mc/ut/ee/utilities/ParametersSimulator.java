package cs.mc.ut.ee.utilities;

import java.util.Random;

public class ParametersSimulator {
	
	
	public static double GenerateDouble(double min, double max){
		Random r = new Random();
		
		double value = min + (max - min) * r.nextDouble();
		
		return value;	
			
		
	}
	
	
	public static int GenerateInts(int min, int max){
		Random r = new Random();

		int value = r.nextInt(max-min) + min;
		
		return value;
			
			
	}
	


}
