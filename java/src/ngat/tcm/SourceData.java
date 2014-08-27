/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;

/**
 * @author eng
 *
 */
public class SourceData implements TelescopeStatus, Serializable {

	private long statusTimeStamp;
	
    /** Records the current Source name. */
    public String srcName;

    /** Records the current Source RA (degrees). */
    public double srcRa;

    /** Records the current Source Declination (degrees). */
    public double srcDec;

    /** Records the current Source equinox (year). */
    public double srcEquinox;

    /** Records the current Source epoch (year). */
    public double srcEpoch;

    /** Records the current Source proper motion RA (sec/year). */
    public double srcPmRA;

    /** Records the current Source proper motion declination (arcsec/year). */
    public double srcPmDec;

    /**
     * Records the current Source non-sidereal tracking rate in RA
     * (sec/year).
     */
    public double srcNsTrackRA;

    /**
     * Records the current Source non-sidereal tracking rate in Dec
     * (arcsec/year).
     */
    public double srcNsTrackDec;

    /** Records the current Source parallax (arcsec). */
    public double srcParallax;

    /** Records the current Source radial velocity (km/sec). */
    public double srcRadialVelocity;

    /** Records the actual RA of the source - offsets included. */
    public double srcActRa;

    /** Records the actual Dec of the source - offsets included. */
    public double srcActDec;

    

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
	
	
	public String toString() {
	    return "SRC " +srcName+
		", RA "+srcRa+
		", Dec "+srcDec+
		", Eqx "+srcEquinox+
		", Ep "+srcEpoch+
		", Pmra "+srcPmRA+
		", Pmdec "+srcPmDec+
		", nsra "+srcNsTrackRA+
		", nsdec "+srcNsTrackDec+
		", para "+srcParallax+
		", rv "+srcRadialVelocity+
		", aRA "+srcActRa+
		", aDec "+	srcActDec;
	}

	public String getCategoryName() {
		return "SRC";
	}
	
}
