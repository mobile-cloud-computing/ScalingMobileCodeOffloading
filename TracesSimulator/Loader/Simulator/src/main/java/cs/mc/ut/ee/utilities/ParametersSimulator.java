package cs.mc.ut.ee.utilities;


import java.util.Random;
import cs.mc.ut.ee.utilities.WorkLoad;
import fi.cs.ubicomp.traces.DataTrace;


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
	
	
	public static String[] getUser(){
		DataTrace user = WorkLoad.getInstance().getUser();
		
		if (user!=null){
			return new String[]{ user.getAccGroup(), user.getUserId() };
		}else{
			return new String[]{ null, null };	
		}
		
	}
	
	public static int getRandomNumber(int number){
		Random r = new Random();
		
		int value = r.nextInt(number);
		
		
		return value;
	}
	
	
	public static String[] getRandomUser(){
		Random r = new Random();
		int user = r.nextInt(WorkLoad.getInstance().getNumberOfUsers());
		int accgroup = r.nextInt(WorkLoad.getInstance().getNumberOfGroups());
		
		return new String[]{String.valueOf(accgroup), String.valueOf(user)};
		
		
	}
	
	
	public static String[] getSnapShotUser(){
		DataTrace user = WorkLoad.getInstance().getSnapShotUser();
		
		if (user!=null){
			return new String[]{ String.valueOf(user.getIndex()), user.getAccGroup(), user.getUserId() };
		}else{
			return new String[]{ null, null, null };	
		}
	}
	


}
