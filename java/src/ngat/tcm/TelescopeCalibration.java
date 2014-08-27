/**
 * 
 */
package ngat.tcm;

/** Provides information about the telescope's calibration requirements.
 * @author snf
 *
 */
public interface TelescopeCalibration {

	/**
	 * @return true if TELFOCUS calibration should be performed.
	 */
	public boolean doTelfocusCalibration();
	
	/**
	 * @return interval between TELFOCUS calibrations.
	 */
	public long getTelfocusCalibrationInterval();
	
	/**
	 * @return Minimum focus value for TELFOCUS (mm).
	 */
	public double getTelfocusFocusRange();
	
	/**
	 * @return FocusMechanism step value for TELFOCUS (mm).
	 */
	public double getTelfocusFocusStep();
	
	/**
	 * @return Signal-noise ration required for TELFOCUS.
	 */
	public double getTelfocusSignalNoise();
	
	/**
	 * @return ID of instrument used for TELFOCUS.
	 */
	public String getTelfocusInstrument();
	
	/**
	 * @return the telfocusExposureTime
	 */
	public double getTelfocusExposureTime();

	/**
	 * @return true if POINTING calibration should be performed.
	 */
	public boolean doPointingCalibration();
	
	/**
	 * @return interval between POINTING  calibrations.
	 */
	public long getPointingCalibrationInterval();
	

}
