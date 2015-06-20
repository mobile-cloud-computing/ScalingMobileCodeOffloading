/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/

package cs.mc.ut.ee.cloud.policies;

public class PolicyPerformance extends Policy {

		
	@Override
	public boolean scaleUp() {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean scaleDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMonitoring(boolean value) {
		// TODO Auto-generated method stub
		this.monitoring = value;
	}

	@Override
	public boolean getMonitoring() {
		// TODO Auto-generated method stub
		return this.monitoring;
	}

	@Override
	public void coolDownTime() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}




}
