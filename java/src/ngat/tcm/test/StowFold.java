package ngat.tcm.test;

import ngat.tcm.*;
import ngat.util.logging.*;

public class StowFold {

    public static void main(String args[]) {

	Logger logger = LogManager.getLogger("TCM");
	ConsoleLogHandler con = new ConsoleLogHandler(new BasicLogFormatter(150));
	con.setLogLevel(5);
	logger.addExtendedHandler(con);
	logger.setLogLevel(5);

	try {
	    ScienceFold fold = new ScienceFold();
	    fold.stow();
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}