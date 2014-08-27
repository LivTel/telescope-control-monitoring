/**
 * 
 */
package ngat.tcm.test;

import java.io.Serializable;

import ngat.tcm.TelescopeCalibration;
import ngat.util.ConfigurationProperties;
import ngat.util.PropertiesConfigurable;

/**
 * @author snf
 *
 */
public class TestTelescopeCalibration implements TelescopeCalibration, PropertiesConfigurable, Serializable {

	private static final long DEFAULT_TELFOCUS_CALIBRATION_INTERVAL = 24*3600*1000L;

	private static final long DEFAULT_POINTING_CALIBRATION_INTERVAL = 24*3600*1000L;

	/** True if POINTING calibration should be performed.*/
	boolean doPointingCalibration;
	
	/** True if TELFOCUS calibration should be performed.*/
	boolean doTelfocusCalibration;
	
	/** Interval between POINTING calibrations (ms).*/
	long pointingCalibrationInterval;
	
	/** Interval between TELFOCUS calibrations (ms).*/
	long telfocusCalibrationInterval;

	double telfocusFocusRange;
	
	double telfocusFocusStep;
	
	double telfocusSignalNoise;
	
	double telfocusExposureTime;
	
	String telfocusInstrument;
	
	/**
	 * @return the doPointingCalibration
	 */
	public boolean doPointingCalibration() {
		return doPointingCalibration;
	}

	/**
	 * @param doPointingCalibration the doPointingCalibration to set
	 */
	public void setDoPointingCalibration(boolean doPointingCalibration) {
		this.doPointingCalibration = doPointingCalibration;
	}

	/**
	 * @return true if TELFOCUS calibration should be performed.
	 */
	public boolean doTelfocusCalibration() {
		return doTelfocusCalibration;
	}

	/**
	 * @param doTelfocusCalibration the doTelfocusCalibration to set
	 */
	public void setDoTelfocusCalibration(boolean doTelfocusCalibration) {
		this.doTelfocusCalibration = doTelfocusCalibration;
	}

	/**
	 * @return the pointingCalibrationInterval
	 */
	public long getPointingCalibrationInterval() {
		return pointingCalibrationInterval;
	}

	/**
	 * @param pointingCalibrationInterval the pointingCalibrationInterval to set
	 */
	public void setPointingCalibrationInterval(long pointingCalibrationInterval) {
		this.pointingCalibrationInterval = pointingCalibrationInterval;
	}

	/**
	 * @return the telfocusCalibrationInterval
	 */
	public long getTelfocusCalibrationInterval() {
		return telfocusCalibrationInterval;
	}

	/**
	 * @param telfocusCalibrationInterval the telfocusCalibrationInterval to set
	 */
	public void setTelfocusCalibrationInterval(long telfocusCalibrationInterval) {
		this.telfocusCalibrationInterval = telfocusCalibrationInterval;
	}

	
	/**
	 * @return the telfocusFocusStep
	 */
	public double getTelfocusFocusStep() {
		return telfocusFocusStep;
	}

	/**
	 * @param telfocusFocusStep the telfocusFocusStep to set
	 */
	public void setTelfocusFocusStep(double telfocusFocusStep) {
		this.telfocusFocusStep = telfocusFocusStep;
	}

	/**
	 * @return the telfocusInstrument
	 */
	public String getTelfocusInstrument() {
		return telfocusInstrument;
	}

	/**
	 * @param telfocusInstrument the telfocusInstrument to set
	 */
	public void setTelfocusInstrument(String telfocusInstrument) {
		this.telfocusInstrument = telfocusInstrument;
	}

	/**
	 * @return the telfocusMaxFocus
	 */
	public double getTelfocusFocusRange() {
		return telfocusFocusRange;
	}

	/**
	 * @param telfocusMaxFocus the telfocusMaxFocus to set
	 */
	public void setTelfocusFocusRange(double telfocusFocusRange) {
		this.telfocusFocusRange = telfocusFocusRange;
	}


	/**
	 * @return the telfocusSignalNoise
	 */
	public double getTelfocusSignalNoise() {
		return telfocusSignalNoise;
	}

	/**
	 * @param telfocusSignalNoise the telfocusSignalNoise to set
	 */
	public void setTelfocusSignalNoise(double telfocusSignalNoise) {
		this.telfocusSignalNoise = telfocusSignalNoise;
	}

	/**
	 * @return the telfocusExposureTime
	 */
	public double getTelfocusExposureTime() {
		return telfocusExposureTime;
	}

	/**
	 * @param telfocusExposureTime the telfocusExposureTime to set
	 */
	public void setTelfocusExposureTime(double telfocusExposureTime) {
		this.telfocusExposureTime = telfocusExposureTime;
	}

	/** Configure from a properties.*/
	public void configure(ConfigurationProperties config) throws Exception {
		doPointingCalibration = (config.getProperty("do.pointing.calibration") != null);
		pointingCalibrationInterval = config.getLongValue("pointing.calibration.interval", DEFAULT_POINTING_CALIBRATION_INTERVAL);
	
		doTelfocusCalibration = (config.getProperty("do.telfocus.calibration") != null);
		telfocusCalibrationInterval = config.getLongValue("telfocus.calibration.interval", DEFAULT_TELFOCUS_CALIBRATION_INTERVAL);
		telfocusFocusRange = config.getDoubleValue("telfocus.focus.range");
		telfocusFocusStep = config.getDoubleValue("telfocus.focus.step");
		telfocusSignalNoise = config.getDoubleValue("telfocus.signal.noise");
		telfocusInstrument = config.getProperty("telfocus.instrument");
		telfocusExposureTime = config.getDoubleValue("telfocus.exposure.time");
	}
	
	/** Returns a readable description of this calibration.*/
	public String toString() {
			return "TelescopeCalib: "+
			(doPointingCalibration ? "POINTING_CALIB @"+(pointingCalibrationInterval/3600000)+"H" : "NO_POINTING")+			
			(doTelfocusCalibration ? "TELFOCUS_CALIB @"+(telfocusCalibrationInterval/3600000)+"H ("+telfocusFocusRange+" ("+telfocusFocusStep+") " :
				"NO_TELFOCUS");			
	}
	
}