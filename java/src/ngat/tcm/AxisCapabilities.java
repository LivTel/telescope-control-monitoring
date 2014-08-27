package ngat.tcm;

public interface AxisCapabilities {

	/**
	 * @return lo limit of axis travel.
	 */
	public double getLowLimit();
	
	/**
	 * @return Hi limit of axis travel.
	 */
	public double getHighLimit();
	
	/**
	 * @return Slew rate (max) of axis.
	 */
	public double getSlewRate();
	
}
