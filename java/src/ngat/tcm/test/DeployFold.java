/**
 * 
 */
package ngat.tcm.test;

import ngat.tcm.ScienceFold;
import ngat.util.logging.BasicLogFormatter;
import ngat.util.logging.ConsoleLogHandler;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

/**
 * @author eng
 *
 */
public class DeployFold {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Logger logger = LogManager.getLogger("TCM");
		ConsoleLogHandler con = new ConsoleLogHandler(new BasicLogFormatter(150));
		con.setLogLevel(5);
		logger.addExtendedHandler(con);
		logger.setLogLevel(5);

		try {
		    ScienceFold fold = new ScienceFold();
		    fold.deploy();
		} catch (Exception e) {
		    e.printStackTrace();
		}

	    
	}

}
