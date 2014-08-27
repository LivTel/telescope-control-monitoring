/**
 * 
 */
package ngat.tcm;

/**
 * @author eng
 *
 */
public class Rotator extends PrimaryAxis {
 
	public Rotator() {
		super("ROT");
		// TODO Auto-generated constructor stub
	}

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

	/**
	 * @see ngat.tcm.PrimaryAxis#updateAxisStatus(ngat.tcm.PrimaryAxisStatus)
	 */
	public void updateAxisStatus(RotatorAxisStatus status) {	
		super.updateAxisStatus(status);
		rotatorMode = status.getRotatorMode();
		skyAngle = status.getSkyAngle();
	}

}