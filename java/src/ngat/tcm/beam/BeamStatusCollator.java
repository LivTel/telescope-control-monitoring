/**
 * 
 */
package ngat.tcm.beam;

import java.util.Enumeration;
import java.util.Hashtable;

import com.sun.corba.se.spi.servicecontext.SendingContextServiceContext;

import ngat.message.RCS_BSS.GET_STATUS;
import ngat.message.RCS_BSS.GET_STATUS_DONE;
import ngat.message.base.COMMAND_DONE;
import ngat.net.SendCommand;
import ngat.tcm.BasicTelescope;
import ngat.util.ControlThread;

/**
 * Collates beam system status.
 * 
 * @author eng
 * 
 */
public class BeamStatusCollator extends ControlThread {

	private BasicTelescope scope;

	private String bssHost;

	private int bssPort;

	private long interval;

	/**
	 * Create a BeamStatusCollator.
	 * 
	 * @param scope
	 *            The telescope instance to update.
	 * @param bssHost
	 *            Host for BSS.
	 * @param bssPort
	 *            Port for BSS.
	 * @param interval
	 *            How long between requests.
	 */
	public BeamStatusCollator(BasicTelescope scope, String bssHost, int bssPort, long interval) {
		super();
		this.scope = scope;
		this.bssHost = bssHost;
		this.bssPort = bssPort;
		this.interval = interval;
	}

	@Override
	protected void initialise() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mainTask() {

		System.err.println("BssCollator: send message...");

		SendCommand sender = new SendCommand(bssHost, bssPort);
		GET_STATUS getstatus = new GET_STATUS("");

		try {
			COMMAND_DONE done = sender.sendCommand(getstatus);

			if (done instanceof GET_STATUS_DONE) {
				GET_STATUS_DONE gsdone = (GET_STATUS_DONE) done;
				System.err.println("Received status:-");

				Hashtable hash = gsdone.getStatusData();

				Enumeration e = hash.keys();
				while (e.hasMoreElements()) {
					Object key = e.nextElement();
					Object value = hash.get(key);
					System.err.printf("%30.30s : %30.30s \n", key, value);
				}

				scope.updateBeamStatus(hash);
			}
		} catch (Exception e) {
			// didnt update beam status 
		}
		// loop round,
		try {
			Thread.sleep(interval);
		} catch (InterruptedException ix) {
		}
	}

	@Override
	protected void shutdown() {
		// TODO Auto-generated method stub

	}

}
