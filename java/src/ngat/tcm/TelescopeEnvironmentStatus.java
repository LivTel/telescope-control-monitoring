/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;

/**
 * @author eng
 *
 */
public class TelescopeEnvironmentStatus implements TelescopeStatus, Serializable {

	private long statusTimeStamp;
	
	private double agBoxTemperature;
	
	private double trussTemperature;
	
	private double secondaryMirrorTemperature;
	
	private double primaryMirrorTemperature;
		
	private double oilTemperature;

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

	
	/**
	 * @return the primaryMirrorTemperature
	 */
	public double getPrimaryMirrorTemperature() {
		return primaryMirrorTemperature;
	}

	/**
	 * @param primaryMirrorTemperature the primaryMirrorTemperature to set
	 */
	public void setPrimaryMirrorTemperature(double primaryMirrorTemperature) {
		this.primaryMirrorTemperature = primaryMirrorTemperature;
	}

	
	/**
	 * @return the agBoxTemperature
	 */
	public double getAgBoxTemperature() {
		return agBoxTemperature;
	}

	/**
	 * @param agBoxTemperature the agBoxTemperature to set
	 */
	public void setAgBoxTemperature(double agBoxTemperature) {
		this.agBoxTemperature = agBoxTemperature;
	}

	/**
	 * @return the trussTemperature
	 */
	public double getTrussTemperature() {
		return trussTemperature;
	}

	/**
	 * @param trussTemperature the trussTemperature to set
	 */
	public void setTrussTemperature(double trussTemperature) {
		this.trussTemperature = trussTemperature;
	}

	/**
	 * @return the secondaryMirrorTemperature
	 */
	public double getSecondaryMirrorTemperature() {
		return secondaryMirrorTemperature;
	}

	/**
	 * @param secondaryMirrorTemperature the secondaryMirrorTemperature to set
	 */
	public void setSecondaryMirrorTemperature(double secondaryMirrorTemperature) {
		this.secondaryMirrorTemperature = secondaryMirrorTemperature;
	}

	/**
	 * @return the oilTemperature
	 */
	public double getOilTemperature() {
		return oilTemperature;
	}

	/**
	 * @param oilTemperature the oilTemperature to set
	 */
	public void setOilTemperature(double oilTemperature) {
		this.oilTemperature = oilTemperature;
	}
	
	public String toString() {
		return "ENV, agbox: "+agBoxTemperature+", truss: "+trussTemperature+
				", oil: "+oilTemperature+", sec_mirr: "+secondaryMirrorTemperature+
				", prim_mirr: "+primaryMirrorTemperature;		
	}

	public String getCategoryName() {
		return "ENV";
	}
	
}
