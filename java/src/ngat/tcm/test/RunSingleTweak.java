package ngat.tcm.test;

import ngat.tcm.*;
import java.rmi.*;

public class RunSingleTweak {

    public static void main(String args[]) {

	try {

	    String host = args[0];

	    TelescopeAlignmentAdjuster taj = (TelescopeAlignmentAdjuster)Naming.lookup("rmi://"+host+"/TelescopeAlignmentAdjuster");

	    taj.makeAdjustment(50.0);

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }


}