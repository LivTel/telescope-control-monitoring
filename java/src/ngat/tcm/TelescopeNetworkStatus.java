package ngat.tcm;
import java.io.Serializable;


/**
 * 
 */

/**
 * @author eng
 *
 */
public class TelescopeNetworkStatus implements TelescopeStatus, Serializable {

    public static int NETWORK_OKAY = 1;

    public static int NETWORK_FAIL = 2;

    /** Which network is this.*/
    private String networkCategory;

	private long statusTimeStamp;
	
	private int telescopeNetworkState;
	
	
	/**
	 * @param networkCategory
	 */
	public TelescopeNetworkStatus(String networkCategory) {
		super();
		this.networkCategory = networkCategory;
	}

	/**
	 * @return the networkCategory
	 */
	public String getNetworkCategory() {
		return networkCategory;
	}

	/**
	 * @param networkCategory the networkCategory to set
	 */
	public void setNetworkCategory(String networkCategory) {
		this.networkCategory = networkCategory;
	}

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
	 * @return the telescopeNetworkState
	 */
	public int getTelescopeNetworkState() {
		return telescopeNetworkState;
	}

	/**
	 * @param telescopeNetworkState the telescopeNetworkState to set
	 */
	public void setTelescopeNetworkState(int telescopeNetworkState) {
		this.telescopeNetworkState = telescopeNetworkState;
	}


	public String toString() {
		return "Network: "+networkCategory+": "+telescopeNetworkState;
	}

	public String getCategoryName() {
		return networkCategory;
	}
	
}
