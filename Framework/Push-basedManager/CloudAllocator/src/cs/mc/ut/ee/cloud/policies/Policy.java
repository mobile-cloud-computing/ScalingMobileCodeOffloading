package cs.mc.ut.ee.cloud.policies;

public abstract class Policy implements CloudScale{

	
	protected boolean monitoring = false;

	
	public abstract void setMonitoring(boolean value);
	
	
	public abstract boolean getMonitoring();
	
	
	
}
