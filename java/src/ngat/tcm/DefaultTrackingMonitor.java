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
import ngat.net.cil.tcs.TcsStatusPacket;
import ngat.util.logging.LogGenerator;
import ngat.util.logging.LogManager;
import ngat.util.logging.Logger;

/**
 * Monitors Tracking in 1-3 axes and notifies observers when tracking lost.
 * 
 * <dl>
 * <dt><b>RCS:</b>
 * <dd>$Id: DefaultTrackingMonitor.java,v 1.2 2007/04/18 10:06:11 snf Exp $
 * <dt><b>Source:</b>
 * <dd>$Source:
 * /home/dev/src/rcs/java/ngat/rcs/scm/detection/RCS/DefaultTrackingMonitor
 * .java,v $
 * </dl>
 * 
 * @author $Author: snf $
 * @version $Revision: 1.2 $
 */

// TODO *** replace that observer i/f with telstatslistner

public class DefaultTrackingMonitor extends UnicastRemoteObject implements TelescopeStatusUpdateListener,
		TrackingMonitor {

	/** List of tracking listeners. */
	protected Vector listeners;

	/** Indicates that Azimuth tracking should be monitored. */
	protected volatile boolean trackAz;

	/** Indicates that Altitude tracking should be monitored. */
	protected volatile boolean trackAlt;

	/** Indicates that Rotator tracking should be monitored. */
	protected volatile boolean trackRot;

	double azDiff;
	double altDiff;
	double rotDiff;

	double azMax;
	double altMax;
	double rotMax;

	double azInt;
	double altInt;
	double rotInt;

	double azSqInt;
	double altSqInt;
	double rotSqInt;

	double azSum;
	double altSum;
	double rotSum;

	int count;

	long startTime;
	long timeStamp;
	long updateTimeStamp;

	boolean rotTrkIsLost = false;
	int rotTrkStatus;
	long rotTrkLostAt;
	long rotTrkLostFor;

	boolean altTrkIsLost = false;
	int altTrkStatus;
	long altTrkLostAt;
	long altTrkLostFor;

	boolean azTrkIsLost = false;
	int azTrkStatus;
	long azTrkLostAt;
	long azTrkLostFor;

	long maxTrkLostFor;

	boolean enableAlerts = false;

	private LogGenerator logger;
	
	public DefaultTrackingMonitor() throws RemoteException {
		super();
		listeners = new Vector();
		
		Logger alogger = LogManager.getLogger("TCM");
		logger = alogger.generate()
			.system("TCM")
			.subSystem("Monitoring")
			.srcCompClass(getClass().getSimpleName())
			.srcCompId("TrackMonitor");
		
		
	}

	/**
	 * Set the maximum time tracking can be lost for before signalling.
	 * (millis).
	 */
	public void setMaxTrackingLostTime(long delta) {
		this.maxTrkLostFor = delta;	
		logger.create().info().level(2).extractCallInfo()
			.msg("Max tracking lost time set to: "+(delta/1000)+"s").send();
	}
	
	/** Sets whether to generate alerts on lost tracking. */
	public void setEnableAlerts(boolean enable) {
		this.enableAlerts = enable;		
		logger.create().info().level(2).extractCallInfo()
			.msg("Tracking alerts are now "+(enableAlerts ? "enabled" : "disabled")).send();
	}

	public void setTrackAz(boolean trackAz) {
		this.trackAz = trackAz;
		logger.create().info().level(2).extractCallInfo()
		.msg("Azimuth tracking monitor is "+(trackAz ? "enabled" : "disabled")).send();
	}

	public void setTrackAlt(boolean trackAlt) {
		this.trackAlt = trackAlt;
		logger.create().info().level(2).extractCallInfo()
		.msg("Altitude tracking monitor is "+(trackAz ? "enabled" : "disabled")).send();
	}

	public void setTrackRot(boolean trackRot) {
		this.trackRot = trackRot;
		logger.create().info().level(2).extractCallInfo()
		.msg("Rotator tracking monitor is "+(trackAz ? "enabled" : "disabled")).send();
	}

	/** Warning - alerts are now automatically enabled on reset ! */
	public void reset() {

		azDiff = 0.0;
		altDiff = 0.0;
		rotDiff = 0.0;

		azMax = 0.0;
		altMax = 0.0;
		rotMax = 0.0;

		azInt = 0.0;
		altInt = 0.0;
		rotInt = 0.0;

		azSqInt = 0.0;
		altSqInt = 0.0;
		rotSqInt = 0.0;

		azSum = 0.0;
		altSum = 0.0;
		rotSum = 0.0;

		count = 0;

		startTime = System.currentTimeMillis();
		timeStamp = startTime;

		rotTrkIsLost = false;
		setEnableAlerts(true);

	}

	/** Return az diff in degrees. */
	public double getAzDiff() {
		return azDiff;
	}

	/** Return alt diff in degrees. */
	public double getAltDiff() {
		return altDiff;
	}

	/** Return rot diff in degrees. */
	public double getRotDiff() {
		return rotDiff;
	}

	/** Return az max in degrees. */
	public double getAzMax() {
		return azMax;
	}

	/** Return alt max in degrees. */
	public double getAltMax() {
		return altMax;
	}

	/** Return rot max in degrees. */
	public double getRotMax() {
		return rotMax;
	}

	/** Return az int in asec. */
	public double getAzRms() {
		return 3600.0 * Math.sqrt(1000.0 * azInt / (double) (updateTimeStamp - startTime));
	}

	/** Return alt int in asec. */
	public double getAltRms() {
		return 3600.0 * Math.sqrt(1000.0 * altInt / (double) (updateTimeStamp - startTime));
	}

	/** Return rot int in asec. */
	public double getRotRms() {
		return 3600.0 * Math.sqrt(1000.0 * rotInt / (double) (updateTimeStamp - startTime));
	}

	/** Return az int in asec. */
	public double getAzInt() {
		return 3600.0 * azInt;
	}

	/** Return alt int in asec. */
	public double getAltInt() {
		return 3600.0 * altInt;
	}

	/** Return rot int in asec. */
	public double getRotInt() {
		return 3600.0 * rotInt;
	}

	/** Return STD Az. */
	public double getAzStd() {
		return Math.sqrt(((double) count * azSqInt - azSum * azSum) / (count * (count - 1)));
	}

	/** Return STD Alt. */
	public double getAltStd() {
		return Math.sqrt(((double) count * altSqInt - altSum * altSum) / (count * (count - 1)));
	}

	/** Return STD Rot. */
	public double getRotStd() {
		return Math.sqrt(((double) count * rotSqInt - rotSum * rotSum) / (count * (count - 1)));
	}

	/** Return time since last reset. */
	public long getIntegrationTime() {
		return updateTimeStamp - startTime;
	}

	public void addTrackingStatusListener(TrackingStatusListener tsl) throws RemoteException {
		if (!listeners.contains(tsl)) {
			listeners.add(tsl);
			logger.create().info().level(2).extractCallInfo()
				.msg("Added new tracking status listener: "+tsl).send();
		}
		logger.create().info().level(2).extractCallInfo()
			.msg("Ignoring request to add known tracking status listener: "+tsl).send();
	}

	public void removeTrackingStatusListener(TrackingStatusListener tsl) throws RemoteException {
	
		if (!listeners.contains(tsl)) {
			logger.create().info().level(2).extractCallInfo()
			.msg("Ignoring request to remove unknown tracking status listener: "+tsl).send();
			return;
		}
		listeners.remove(tsl);
		logger.create().info().level(2).extractCallInfo()
			.msg("Removed tracking status listener: "+tsl).send();
	}

	public void triggerTrackingLost() throws RemoteException {
		logger.create().info().level(3).extractCallInfo()
			.msg("Executing external request to trigger tracking-lost notification").send();
				
		int ia = 0;
		Iterator aslist = listeners.iterator();
		while (aslist.hasNext()) {
			TrackingStatusListener tsl = (TrackingStatusListener) aslist.next();
			
			logger.create().info().level(4).extractCallInfo()
				.msg("Trigger tracking lost notification to listener: "+(++ia)+", "+tsl).send();
			
			tsl.trackingLost();
		}
	}

	public void telescopeStatusUpdate(TelescopeStatus status) throws RemoteException {
	
		logger.create().info().level(3).extractCallInfo()
			.msg("Recieved telescope status update packet, class: "+
				(status == null ? "NULL" : status.getClass().getName())).send();
		
		logger.create().info().level(4).extractCallInfo()
			.msg("Recieved telescope status update packet, actual: "+status).send();
		
		if (status == null)
			return;
	
		if (status instanceof PrimaryAxisStatus) {

			PrimaryAxisStatus axisStatus = (PrimaryAxisStatus) status;
			updateTimeStamp = axisStatus.getStatusTimeStamp();
			
			if (axisStatus.getMechanismName() == null)
				return;

			if (axisStatus.getMechanismName().equals("AZM")) {
				
				azDiff = Math.abs(axisStatus.getCurrentPosition() - axisStatus.getDemandPosition());
				azSum += axisStatus.getDemandPosition() - axisStatus.getCurrentPosition();

				// Monitoring AZ tracking status.
				if (trackAz) {

					azTrkStatus = axisStatus.getMechanismState();

					if (azTrkStatus != TCS_Status.MOTION_TRACKING) {

						if (azTrkIsLost) {
							
							azTrkLostFor = updateTimeStamp - azTrkLostAt;							
							logger.create().info().level(4).extractCallInfo()
								.msg("Azimuth Tracking lost for: "+(azTrkLostFor/1000)+" sec").send();

							if (azTrkLostFor > maxTrkLostFor) {							
								logger.create().info().level(4).extractCallInfo()
									.msg("Azimuth tracking lost duration exceeds max threshold").send();
								
								if (enableAlerts) {
								
									int it = 0;
									Iterator tslist = listeners.iterator();
									while (tslist.hasNext()) {
										TrackingStatusListener tsl = (TrackingStatusListener) tslist.next();									
										logger.create().info().level(4).extractCallInfo()
											.msg("Sending azimuth tracking lost notification to listener " + (++it) + ", "
												+ tsl).send();																				
										tsl.trackingLost();
									}
									// Disable events after firing once
									setEnableAlerts(false);
								}
							}

						} else {					
							logger.create().info().level(4).extractCallInfo()
								.msg("Azimuth tracking is now lost, starting recording").send();
							azTrkLostAt = updateTimeStamp;
							azTrkIsLost = true;
						}

					} else {

						if (azTrkIsLost) {						
							logger.create().info().level(4).extractCallInfo()
								.msg("Azimuth tracking re-established before alert trigger").send();							
							azTrkIsLost = false;
						}
					}

				}

			} else if (axisStatus.getMechanismName().equals("ALT")) {
			
				azDiff = Math.abs(axisStatus.getCurrentPosition() - axisStatus.getDemandPosition());
				altSum += axisStatus.getDemandPosition() - axisStatus.getCurrentPosition();

				// Monitoring alt tracking status.
				if (trackAlt) {

					altTrkStatus = axisStatus.getMechanismState();

					if (altTrkStatus != TCS_Status.MOTION_TRACKING) {

						if (altTrkIsLost) {
							
							altTrkLostFor = updateTimeStamp - altTrkLostAt;							
							logger.create().info().level(4).extractCallInfo()
								.msg("Altitude Tracking lost for: "+(altTrkLostFor/1000)+" sec").send();

							if (altTrkLostFor > maxTrkLostFor) {							
								logger.create().info().level(4).extractCallInfo()
									.msg("Altitude Tracking lost duration exceeds max threshold").send();
								
								if (enableAlerts) {
								
									int it = 0;
									Iterator tslist = listeners.iterator();
									while (tslist.hasNext()) {
										TrackingStatusListener tsl = (TrackingStatusListener) tslist.next();																		
										logger.create().info().level(4).extractCallInfo()
												.msg("Sending altitude tracking lost notification to listener " + (++it) + ", "
														+ tsl).send();																						
										tsl.trackingLost();
									}
									// Disable events after firing once
									setEnableAlerts(false);
								}
							}

						} else {					
							logger.create().info().level(4).extractCallInfo()
								.msg("Altitude tracking is now lost, starting recording").send();
							altTrkLostAt = updateTimeStamp;
							altTrkIsLost = true;
						}

					} else {

						if (altTrkIsLost) {						
							logger.create().info().level(4).extractCallInfo()
								.msg("Altitude tracking re-established before alert trigger").send();							
							altTrkIsLost = false;
						}
					}

				}

			}

			if (status instanceof RotatorAxisStatus) {
				RotatorAxisStatus rotStatus = (RotatorAxisStatus) status;
				
				rotDiff = Math.abs(rotStatus.getCurrentPosition() - rotStatus.getDemandPosition());
				rotSum += rotStatus.getDemandPosition() - rotStatus.getCurrentPosition();
				
				// Monitoring rotator tracking status.
				if (trackRot) {

					rotTrkStatus = axisStatus.getMechanismState();

					if (rotTrkStatus != TCS_Status.MOTION_TRACKING) {

						if (rotTrkIsLost) {
							
							rotTrkLostFor = updateTimeStamp - rotTrkLostAt;							
							logger.create().info().level(4).extractCallInfo()
								.msg("Rotator Tracking lost for: "+(rotTrkLostFor/1000)+" sec").send();

							if (rotTrkLostFor > maxTrkLostFor) {							
								logger.create().info().level(4).extractCallInfo()
									.msg("Rotator Tracking lost duration exceeds max threshold").send();
								
								if (enableAlerts) {
								
									int it = 0;
									Iterator tslist = listeners.iterator();
									while (tslist.hasNext()) {
										TrackingStatusListener tsl = (TrackingStatusListener) tslist.next();																			
										logger.create().info().level(4).extractCallInfo()
												.msg("Sending rotator tracking lost notification to listener " + (++it) + ", "
														+ tsl).send();																						
										tsl.trackingLost();
									}
									// Disable events after firing once
									setEnableAlerts(false);
								}
							}

						} else {					
							logger.create().info().level(4).extractCallInfo()
								.msg("Rotator tracking is now lost, starting recording").send();
							rotTrkLostAt = updateTimeStamp;
							rotTrkIsLost = true;
						}

					} else {

						if (rotTrkIsLost) {						
							logger.create().info().level(4).extractCallInfo()
								.msg("Rotator tracking re-established before alert trigger").send();							
							rotTrkIsLost = false;
						}
					}

				}
///lll
			} // which mechanism

		} // primary axis

		if (azDiff > azMax)
			azMax = azDiff;
		if (altDiff > altMax)
			altMax = altDiff;
		if (rotDiff > rotMax)
			rotMax = rotDiff;

		// integrated and time to sec -> (deg/sec)**2
		azInt = azInt + (double) (updateTimeStamp - timeStamp) * azDiff * azDiff / 1000.0;
		altInt = altInt + (double) (updateTimeStamp - timeStamp) * altDiff * altDiff / 1000.0;
		rotInt = rotInt + (double) (updateTimeStamp - timeStamp) * rotDiff * rotDiff / 1000.0;

		// Add squared differences.
		azSqInt += azDiff * azDiff;
		altSqInt += altDiff * altDiff;
		rotSqInt += rotDiff * rotDiff;

		// Add average terms

		count++;

		timeStamp = updateTimeStamp;

	} // (telescopeStatusUpdate)

	public void telescopeNetworkFailure(long time, String message) throws RemoteException {	
		// TODO what to do ?
	}

	
	
	
	
}

/**
 * $Log: DefaultTrackingMonitor.java,v $ /** Revision 1.2 2007/04/18 10:06:11
 * snf /** added azimuth tracking /** /** Revision 1.1 2006/12/12 08:31:16 snf
 * /** Initial revision /** /** Revision 1.1 2006/05/17 06:35:17 snf /** Initial
 * revision /**
 */
