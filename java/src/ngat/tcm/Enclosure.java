/**
 * 
 */
package ngat.tcm;

/**
 * @author eng
 *
 */
public class Enclosure {
	
	private AuxilliaryMechanism shutter1;
	
	private AuxilliaryMechanism shutter2;

	
	
	/**
	 * 
	 */
	public Enclosure() {
		shutter1 = new AuxilliaryMechanism("EN1");
		shutter2 = new AuxilliaryMechanism("En2");
	}

	/**
	 * @return the shutter1
	 */
	public AuxilliaryMechanism getShutter1() {
		return shutter1;
	}

	/**
	 * @param shutter1 the shutter1 to set
	 */
	public void setShutter1(AuxilliaryMechanism shutter1) {
		this.shutter1 = shutter1;
	}

	/**
	 * @return the shutter2
	 */
	public AuxilliaryMechanism getShutter2() {
		return shutter2;
	}

	/**
	 * @param shutter2 the shutter2 to set
	 */
	public void setShutter2(AuxilliaryMechanism shutter2) {
		this.shutter2 = shutter2;
	}
	
	
}
