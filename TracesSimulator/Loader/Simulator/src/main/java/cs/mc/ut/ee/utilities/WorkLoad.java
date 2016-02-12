package cs.mc.ut.ee.utilities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fi.cs.ubicomp.traces.DataTrace;

public class WorkLoad {
	
	/**
	 * Traces mode
	 */
	
	public static WorkLoad instance;
	
	LinkedList<DataTrace> users = null;
	
	private WorkLoad(){
		
	}
	
	public static synchronized WorkLoad getInstance(){
		if (instance==null){
			instance = new WorkLoad();
			return instance;
		}
		
		return instance;
	}

	public void setUsersWorkLoad(List<DataTrace> users){
		this.users = (LinkedList<DataTrace>) users;
	}
	
	
	public DataTrace getUser(){
		if (users!=null){
			if (!users.isEmpty()){
				DataTrace user = users.removeFirst();
				return user;
			}
		}
		
		return null;
	}
	
	/**
	 * Interarrival mode
	 */
	
	private int numberOfUsers = 0;
	private int numberOfGroups = 0;
	
	List<DataTrace> snapShot;
	
	public void setNumberOfUsers(int numberOfUsers){
		this.numberOfUsers = numberOfUsers;
	}
	
	public int getNumberOfUsers(){
		return this.numberOfUsers;
	}
	
	public void setAccelerationGroups(int numberOfGroups){
		this.numberOfGroups = numberOfGroups;
	}
	
	public int getNumberOfGroups(){
		return this.numberOfGroups;
	}
	
	public void generateSnapShot(){
		snapShot = new ArrayList<DataTrace>();
		for (int i=0; i<numberOfUsers; i++){
			//int acceleration = ParametersSimulator.getRandomNumber(numberOfGroups); //snapshot in time
			int acceleration = 0; //everybody initiates in 0
			snapShot.add(new DataTrace(String.valueOf(i), "user"+i, String.valueOf(acceleration)));
		}
	}
	
	public DataTrace getSnapShotUser(){
		if (snapShot!=null){
			if (!snapShot.isEmpty()){
				int index = ParametersSimulator.getRandomNumber(snapShot.size());
				
				DataTrace user = snapShot.get(index);
				
				return user;
			}
		}
		
		return null;
	}
	
	
	public void promoteSnapShotUser(int index, String id, String userId, String accGroup){
		snapShot.get(index).setIndex(Integer.valueOf(id));
		snapShot.get(index).setUserId(userId);
		snapShot.get(index).setAccGroup(accGroup);
		
	}
	
	
	public int getSnapShotUserIndex(String index){
		for (int i=0; i<snapShot.size(); i++){
			if (snapShot.get(i).getIndex() == Integer.valueOf(index)){
				return i;
			}
		}
		
		return 0;
	}
	
	
	
	
	
	
	
}
