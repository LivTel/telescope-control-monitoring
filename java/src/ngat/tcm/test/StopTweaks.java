/**
 * 
 */
package ngat.tcm.test;

import java.rmi.Naming;

import ngat.tcm.TelescopeAlignmentAdjuster;

/**
 * @author eng
 *
 */
public class StopTweaks {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {

		    String host = args[0];

		    TelescopeAlignmentAdjuster taj = (TelescopeAlignmentAdjuster)Naming.lookup("rmi://"+host+"/TelescopeAlignmentAdjuster");

		    taj.stopAdjustments();

		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

}
