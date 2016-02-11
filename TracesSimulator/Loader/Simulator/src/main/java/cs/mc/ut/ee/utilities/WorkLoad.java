package cs.mc.ut.ee.utilities;

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
	
	
}
