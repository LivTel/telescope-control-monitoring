/**
 * 
 */
package ngat.tcm;

/**
 * @author eng
 *
 */
public class RotatorAxisStatus extends PrimaryAxisStatus {

	/** Sky PA.*/
	private double skyAngle;
	
	/** RotatorMechanism mode.*/
	private int rotatorMode;

	/**
	 * @return the skyAngle
	 */
	public double getSkyAngle() {
		return skyAngle;
	}

	/**
	 * @param skyAngle the skyAngle to set
	 */
	public void setSkyAngle(double skyAngle) {
		this.skyAngle = skyAngle;
	}

	/**
	 * @return the rotatorMode
	 */
	public int getRotatorMode() {
		return rotatorMode;
	}

	/**
	 * @param rotatorMode the rotatorMode to set
	 */
	public void setRotatorMode(int rotatorMode) {
		this.rotatorMode = rotatorMode;
	}
	
	public String toString() {
			return super.toString()+", sky: "+skyAngle+", mode: "+rotatorMode;
	}

}
