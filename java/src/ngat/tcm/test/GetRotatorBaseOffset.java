/**
 * 
 */
package ngat.tcm.test;

import java.rmi.Naming;

import ngat.tcm.SciencePayload;
import ngat.tcm.Telescope;
import ngat.tcm.TelescopeSystem;

/** Get the rotator base offset from a remote science payload.
 * @author eng
 *
 */
public class GetRotatorBaseOffset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
		
			Telescope scope = (Telescope)Naming.lookup("rmi://ltsim1/Telescope");
			TelescopeSystem sys = scope.getTelescopeSystem();
			SciencePayload sci = sys.getSciencePayload();
			
			double rotoff = sci.getRotatorBaseOffset();
			
			System.err.printf("Rot base: %4.2f \n", Math.toDegrees(rotoff));
			

	} catch (Exception e) {
		e.printStackTrace();
	}
	}
}
