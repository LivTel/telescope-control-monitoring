package ngat.tcm.test;

import ngat.tcm.*;
import java.rmi.*;

public class EnableTweaks {

	public static void main(String args[]) {

		try {

			String host = args[0];

			TelescopeAlignmentAdjuster taj = (TelescopeAlignmentAdjuster) Naming.lookup("rmi://" + host
					+ "/TelescopeAlignmentAdjuster");

			String mode = args[1];

			if (mode.equalsIgnoreCase("INTERVAL")) {

				long interval = (long) (Integer.parseInt(args[2]));

				taj.enableAdjustmentsInterval(interval);

			} else if (mode.equalsIgnoreCase("TRIGGER")) {

				taj.enableAdjustmentsTrigger();

			} else if (mode.equalsIgnoreCase("SINGLE")) {

				taj.enableAdjustmentsSingle();

			} else if (mode.equalsIgnoreCase("DISABLE")) {

				taj.disableAdjustments();

			} else
				System.err.println("Syntax: <host> [SINGLE, INTERVAL <ms>, TRIGGER, NONE]");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}