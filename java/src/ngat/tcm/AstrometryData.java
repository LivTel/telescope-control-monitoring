/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;

/**
 * @author eng
 *
 */
public class AstrometryData implements Serializable, TelescopeStatus {

	private long statusTimeStamp;
	
	  /** Records the pressure used in the refraction calculations (mBar).*/
    public double refractionPressure;

    /** Records the temperature used in the refraction calculations (K).*/
    public double refractionTemperature;

    /** Records the humidity used in the refraction calculations (%).*/
    public double refractionHumidity;

    /** Records the wavelength used in the refraction calculations (microns).*/
    public double refractionWavelength;

    /** Records the difference between UT1 and UTC in use (secs).*/
    public double ut1_utc;

    /** Records the difference between TDT and UDT in use (secs).*/
    public double tdt_utc;

    /** Records the polar correction X (arcsec).*/
    public double polarMotion_X;

    /** Records the polar correction Y (arcsec).*/
    public double polarMotion_Y;

    /** Records the current airmass.*/
    public double airmass;

    /** Records the autoguider wavelength (microns).*/
    public double agwavelength;

	
	
	/**
     * @return the statusTimeStamp
	 */
	public long getStatusTimeStamp() {
		return statusTimeStamp;
	}

	/**
	 * @param statusTimeStamp the statusTimeStamp to set
	 */
	public void setStatusTimeStamp(long statusTimeStamp) {
		this.statusTimeStamp = statusTimeStamp;
	}

	
	public String getCategoryName() {
		return "AST";
	}
	
	public String toString() {
	    return "AST " +airmass;
	}
}
