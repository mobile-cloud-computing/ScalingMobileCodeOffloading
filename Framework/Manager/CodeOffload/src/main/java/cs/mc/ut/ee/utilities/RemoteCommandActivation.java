/*
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * Please send inquiries to huber AT ut DOT ee
 */


package cs.mc.ut.ee.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class RemoteCommandActivation {

	public static String activateAPK(String host, String user, String password, String cmd ){

		String s = null;

			try{

				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				JSch jsch = new JSch();
				Session session=jsch.getSession(user, host, 22);
				session.setPassword(password);
				session.setConfig(config);
				session.connect();
				System.out.println("Connected "+user+"@"+host);

				Channel channel=session.openChannel("exec");
				((ChannelExec)channel).setCommand(cmd);

				((ChannelExec)channel).setErrStream(System.err);

				channel.connect();

				BufferedReader stdInput = new BufferedReader(new
						InputStreamReader(channel.getInputStream()));

				String line = null;
				while ((line = stdInput.readLine()) != null)
					s = (s == null)?line:(s+"\n"+line);

				channel.disconnect();
				session.disconnect();
				System.out.println("disconnected");
			}catch(Exception e){
				e.printStackTrace();
			}

			return (s != null)?s:"no matches found";
		}	
	
	/*public static void main(String[] args) {
		cmdExecSSH("54.170.37.245","huber","thisisnothepass:D","cd /home/ubuntu/android-x86/ ; ./rund.sh -version");
		
		//"cd /home/ubuntu/android-x86/ ; ./rund.sh -cp g_chess_Server__6003.apk edu.ut.mobile.network.Main"
		//"cd /home/ubuntu/android-x86/ ; ./rund.sh -version"
	}*/
}




