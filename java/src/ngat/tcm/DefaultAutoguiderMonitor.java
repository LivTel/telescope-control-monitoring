/*   
     Copyright 2006, Astrophysics Research Institute, Liverpool John Moores University.

     This file is part of Robotic Control System.

     Robotic Control Systemis free software; you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation; either version 2 of the License, or
     (at your option) any later version.

     Robotic Control System is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with Robotic Control System; if not, write to the Free Software
     Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package ngat.tcm;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import ngat.message.RCS_TCS.*;
import ngat.phase2.IAutoguiderConfig;
import ngat.net.cil.tcs.TcsStatusPacket;
import ngat.util.logging.LogGenerator;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

/**
 * Monitors guide status of autoguider and notifies observers when lost.
 * 
 * <dl>
 * <dt><b>RCS:</b>
 * <dd>$Id: DefaultAutoguiderMonitor.java,v 1.1 2006/12/12 08:31:16 snf Exp $
 * <dt><b>Source:</b>
 * <dd>$Source:
 * /home/dev/src/rcs/java/ngat/rcs/scm/detection/RCS/DefaultAutoguiderMonitor
 * .java,v $
 * </dl>
 * 
 * @author $Author: snf $
 * @version $Revision: 1.1 $
 */

// public class DefaultAutoguiderMonitor implements Observer {
public class DefaultAutoguiderMonitor extends UnicastRemoteObject implements  AutoguiderMonitor,
		TelescopeStatusUpdateListener {

	/** The autoguider which we are monitoring.*/
	private String agname;
	
	/** Listeners for AG monitor trigger events. */
	protected Vector statusListeners;

	/** Listeners for changes of state of the AG monitor. */
	protected Vector monitorStateListeners;

	long startTime;
	long timeStamp;
	long updateTimeStamp;

	boolean guideIsLost = false;
	int guideStatus;
	long guideLostAt;
	long guideLostFor;
	long maxGuideLostFor;
	boolean enableAlerts = false;

	private LogGenerator logger;

	/**
	 * Create a DefaultAutoguiderMonitor.
	 * 
	 * @throws RemoteException
	 */
	public DefaultAutoguiderMonitor(String agname) throws RemoteException {
		super();
		this.agname = agname;
		statusListeners = new Vector();
		monitorStateListeners = new Vector();
		Logger alogger = LogManager.getLogger("TCM");
		logger = alogger.generate().system("TCM").subSystem("Monitoring").srcCompClass(getClass().getSimpleName())
				.srcCompId("AgMonitor:"+agname);

	}

	/** Sets whether to generate alerts on lost guiding. */
	public void setEnableAlerts(boolean enable) {
		this.enableAlerts = enable;	
		logger.create().info().level(2).extractCallInfo()
			.msg("Autoguider lock alerts are now "+(enableAlerts ? "enabled" : "disabled")).send();
		notifyListenersStateEnabled(enable);

	}

	/**
	 * Notify any registered listeners that the monitor has been enabled or
	 * disabled.
	 * 
	 * @param b
	 *            true if it was enabled otherwise false.
	 */
	private void notifyListenersStateEnabled(boolean b) {
		for (int il = 0; il < monitorStateListeners.size(); il++) {
			AutoguiderMonitorStateListener agsl = (AutoguiderMonitorStateListener) monitorStateListeners.get(il);
			if (agsl != null) {
				try {					
					agsl.autoguiderMonitorEnabled(b);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO Get rid of the offending listener next cycle...
				}
			}
		}
	}

	/**
	 * Set the maximum time GUIDING can be lost for before signalling. (millis).
	 */
	public void setMaxGuidingLostTime(long delta) {
		this.maxGuideLostFor = delta;
		logger.create().info().level(3).extractCallInfo()
			.msg("Max guide lost time set to: "+(delta/1000)+"s").send();
	}

	/** Warning - alerts are now automatically enabled on reset ! */
	public void reset() {

		startTime = System.currentTimeMillis();
		timeStamp = startTime;

		guideIsLost = false;
		setEnableAlerts(true);

	}

	/**
	 * Add an object as an AutoguiderStatusListener.
	 * 
	 * @param asl
	 *            The object to add to the list of statusListeners.
	 * @throws RemoteException
	 */
	public void addGuideStatusListener(AutoguiderStatusListener asl) throws RemoteException {
		if (!statusListeners.contains(asl)) {
			statusListeners.add(asl);
			logger.create().info().level(3).extractCallInfo()
				.msg("Added new guide status listener: "+asl).send();
		}
		logger.create().info().level(3).extractCallInfo()
			.msg("Ignoring request to add known guide status listener: "+asl).send();
	}

	/**
	 * Remove an object as an AutoguiderStatusListener.
	 * 
	 * @param asl
	 *            The object to remove from the list of statusListeners.
	 * @throws RemoteException
	 */
	public void removeGuideStatusListener(AutoguiderStatusListener asl) throws RemoteException {	
		if (!statusListeners.contains(asl)) {
			logger.create().info().level(3).extractCallInfo()
				.msg("Ignoring request to remove unknown guide status listener: "+asl).send();
			return;
		}
		statusListeners.remove(asl);
		logger.create().info().level(3).extractCallInfo()
		.msg("Removed guide status listener: "+asl).send();
	}

	/**
	 * Force a lock-lost trigger event.
	 * 
	 * @throws RemoteException
	 */
	public void triggerLockLost() throws RemoteException {
		logger.create().info().level(3).extractCallInfo()
		.msg("Executing external request to trigger guide-lost notification").send();
			
		int ia = 0;
		Iterator aslist = statusListeners.iterator();
		while (aslist.hasNext()) {
			AutoguiderStatusListener asl = (AutoguiderStatusListener) aslist.next();
			logger.create().info().level(4).extractCallInfo()
			.msg("Trigger guide lost notification to listener: "+(++ia)+", "+asl).send();
			asl.guideLockLost();
		}
	}

	/**
	 * Add an object as an AutoguiderMonitorStateListener.
	 * 
	 * @param agsl
	 *            The object to add to the list of statusListeners.
	 * @throws RemoteException
	 */
	public void addAutoguiderMonitorStateListener(AutoguiderMonitorStateListener agsl) throws RemoteException {
		if (!monitorStateListeners.contains(agsl)) {
			monitorStateListeners.add(agsl);
			System.err.println("AGMon:: Added MonitorStateChangeListener: " + agsl);
		}
	}

	/**
	 * Remove an object as an AutoguiderMonitorStateListener.
	 * 
	 * @param agsl
	 *            The object to remove from the list of statusListeners.
	 * @throws RemoteException
	 */
	public void removeAutoguiderMonitorStateListener(AutoguiderMonitorStateListener agsl) throws RemoteException {
		System.err.println("AGMon:: Requested to remove MonitorStateChangeListener: " + agsl);
		if (!monitorStateListeners.contains(agsl))
			return;
		monitorStateListeners.remove(agsl);
		System.err.println("AGMon:: Removed MonitorStateChangeListener: " + agsl);
	}

	public void telescopeStatusUpdate(TelescopeStatus status) throws RemoteException {

		logger.create().info().level(3).extractCallInfo()
			.msg("Recieved telescope status update packet, class: "+
				(status == null ? "NULL" : status.getClass().getName())).send();
		
		logger.create().info().level(4).extractCallInfo()
			.msg("Recieved telescope status update packet, actual: "+status).send();

		if (status == null)
			return;

		if (status instanceof AutoguiderStatus) {
			
			AutoguiderStatus agStatus = (AutoguiderStatus) status; 
	
			// Monitoring rotator tracking status.
			guideStatus = agStatus.getGuideState();
			
			logger.create().info().level(3).extractCallInfo()
				.msg("Guide status: "+TCS_Status.codeString(guideStatus)).send();

			if (guideStatus != TCS_Status.AG_LOCKED) {

				if (guideIsLost) {

					guideLostFor = updateTimeStamp - guideLostAt;
					logger.create().info().level(4).extractCallInfo()
					.msg("Guide lock lost for: "+(guideLostFor/1000)+" sec").send();

					if (guideLostFor > maxGuideLostFor) {
						logger.create().info().level(4).extractCallInfo()
							.msg("Guide lock lost duration exceeds max threshold").send();
						
						if (enableAlerts) {
							
							int ia = 0;
							Iterator aslist = statusListeners.iterator();
							while (aslist.hasNext()) {
								AutoguiderStatusListener asl = (AutoguiderStatusListener) aslist.next();								
								logger.create().info().level(4).extractCallInfo()
									.msg("Sending guide lost notification to listener " + (++ia) + ", "
										+ asl).send();				
								asl.guideLockLost();
							}

							// Disable events after firing once
							// - listener which fixes should re-enable if
							// successful
							setEnableAlerts(false);
						}
					}

				} else {					
					logger.create().info().level(4).extractCallInfo()
						.msg("Guide lock is now lost, starting recording").send();
					guideLostAt = updateTimeStamp;
					guideIsLost = true;

				}

			} else {

				if (guideIsLost) {
					logger.create().info().level(4).extractCallInfo()
						.msg("Guide lock re-established before alert trigger").send();			
					guideIsLost = false;
				}

			}

			timeStamp = updateTimeStamp;

		}

	}

	public void telescopeNetworkFailure(long time, String message) throws RemoteException {		
		// TODO what to do ?
	}

}
