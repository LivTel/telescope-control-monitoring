/**
 * 
 */
package ngat.tcm.test;

import java.rmi.Naming;

import ngat.tcm.AutoguiderStatusCollator;
import ngat.tcm.BasicTelescope;

/**
 * @author eng
 *
 */
public class RunAgStatusCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		try {
			
			
			AutoguiderStatusCollator collator = new AutoguiderStatusCollator(null, "CASS", "ltsim1", 6571, 10000L);
			collator.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
