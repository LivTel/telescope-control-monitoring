/**
 * 
 */
package ngat.tcm;

/** A place for collecting various environmental data.
 * @author eng
 *
 */
public class TelescopeEnvironment {

	private double agBoxTemperature;
	
	private double trussTemperature;
	
	private double secondaryMirrorTemperature;
	
	private double primaryMirrorTemperature;
	
	private double oilTemperature;

	
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
	
	public void updateStatus(TelescopeEnvironmentStatus status) {
		primaryMirrorTemperature = status.getPrimaryMirrorTemperature();
		secondaryMirrorTemperature = status.getSecondaryMirrorTemperature();
		agBoxTemperature = status.getAgBoxTemperature();
		oilTemperature = status.getOilTemperature();
		trussTemperature = status.getTrussTemperature();
	}
	
}
