/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;

/**
 * @author eng
 *
 */
public class BasicAxisCapabilities implements AxisCapabilities {

	private double highLimit;
	
	private double lowLimit;
	
	private double slewRate;
	
	/** Category name for this capabilities.*/
	private String category;
	
	
	/**
	 * @param category
	 */
	public BasicAxisCapabilities(String category) {
		super();
		this.category = category;
	}



	/**
	 * @return the highLimit
	 */
	public double getHighLimit() {
		return highLimit;
	}



	/**
	 * @param highLimit the highLimit to set
	 */
	public void setHighLimit(double highLimit) {
		this.highLimit = highLimit;
	}



	/**
	 * @return the lowLimit
	 */
	public double getLowLimit() {
		return lowLimit;
	}



	/**
	 * @param lowLimit the lowLimit to set
	 */
	public void setLowLimit(double lowLimit) {
		this.lowLimit = lowLimit;
	}



	/**
	 * @return the slewRate
	 */
	public double getSlewRate() {
		return slewRate;
	}



	/**
	 * @param slewRate the slewRate to set
	 */
	public void setSlewRate(double slewRate) {
		this.slewRate = slewRate;
	}



	/* (non-Javadoc)
	 * @see ngat.net.telemetry.StatusProvider#getStatusCategory()
	 */
	public String getStatusCategory() {
		return category;
	}

}
