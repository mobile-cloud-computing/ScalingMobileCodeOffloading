package fi.cs.ubicomp.traces;

public class DataTrace {
	
	private int index;
	private String userId;
	private String accGroup;
	
	public DataTrace(String index, String userId, String accGroup){
		this.index = Integer.parseInt(index);
		this.userId = userId;
		this.accGroup = accGroup;
	}

	public int getIndex(){
		return this.index;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public String getAccGroup(){
		return this.accGroup;
	}
	
}
