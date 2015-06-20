package cs.mc.ut.ee.cloud.policies;

public interface CloudScale {
	
	public boolean scaleUp();;
	
	public boolean scaleDown();
	
	public void coolDownTime();
}
