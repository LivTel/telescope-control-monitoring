/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;

/**
 * @author eng
 *
 */
public class TimeData implements TelescopeStatus, Serializable {

	private long statusTimeStamp;

    /** MJD.*/
    public double mjd;
    
    /** Records the current time (UT1) (secs). */
    public double ut1;

    /** Records the current time (Local Sidereal Time) (secs). */
    public double lst;




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
	    return "TIM MJD "+mjd+                                                                                                                          
		", UT1 "+ut1+
		", LST "+lst;       
	}

	public String getCategoryName() {
	    return "TIM";
	}
    
}
