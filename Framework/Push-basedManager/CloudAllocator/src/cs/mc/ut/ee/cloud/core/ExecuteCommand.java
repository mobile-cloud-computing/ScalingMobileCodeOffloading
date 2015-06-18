/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/

package cs.mc.ut.ee.cloud.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * author Huber Flores
 */


public class ExecuteCommand {

	public static String executeCommand(String command) {
		System.out.println("COMMAND: " + command);
		StringBuilder tmp = new StringBuilder();
		try {
			ProcessBuilder pb = new ProcessBuilder(
					"/bin/bash", "-c", command
					);
			Process p = pb.start();
	        p.waitFor();
	        if(p != null){
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
				String line;
				while( (line = reader.readLine()) != null){
					tmp.append(line).append("\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		return tmp.toString();
	}
	
}
