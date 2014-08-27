/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author eng
 * 
 */
public abstract class BasicMechanismState implements Mechanism, Serializable {

	static HashMap<Integer, String> codes = new HashMap<Integer, String>();

	/** Category name for this mechanism. */
	protected String mechCategory;

	/** Mechanism state. */
	protected int mechanismState;

	static {

		codes.put(new Integer(MOTION_STOPPED), "STOPPED");
		codes.put(new Integer(MOTION_INPOSITION), "IN POSN");
		codes.put(new Integer(MOTION_TRACKING), "TRACKING");
		codes.put(new Integer(MOTION_MOVING), "MOVING");
		codes.put(new Integer(MOTION_LIMIT), "LIMIT");
		codes.put(new Integer(MOTION_OVERRIDE), "OVERRIDE");
		codes.put(new Integer(MOTION_EXPIRED), "EXPIRED");
		codes.put(new Integer(MOTION_OFF_LINE), "OFF-LINE");
		codes.put(new Integer(MOTION_UNKNOWN), "UNKNOWN");
		codes.put(new Integer(MOTION_INCREASING), "MOTION-INCREASING");
		codes.put(new Integer(MOTION_DECREASING), "MOTION-DECREASING");
		codes.put(new Integer(MOTION_SLEWING), "MOTION-SLEWING");
		codes.put(new Integer(MOTION_WARNING), "WARNING");

		codes.put(new Integer(POSITION_IN), "IN");
		codes.put(new Integer(POSITION_OUT), "OUT");
		codes.put(new Integer(POSITION_CLOSED), "CLOSED");
		codes.put(new Integer(POSITION_OPEN), "OPEN");
		codes.put(new Integer(POSITION_PARTIAL), "PARTIAL");
		codes.put(new Integer(POSITION_UNKNOWN), "POSITION-UNKNOWN");

		codes.put(new Integer(POSITION_STOWED), "STOWED");
		codes.put(new Integer(POSITION_PORT_1), "PORT 1");
		codes.put(new Integer(POSITION_PORT_2), "PORT 2");
		codes.put(new Integer(POSITION_PORT_3), "PORT 3");
		codes.put(new Integer(POSITION_PORT_4), "PORT 4");
		codes.put(new Integer(POSITION_PORT_5), "PORT 5");
		codes.put(new Integer(POSITION_PORT_6), "PORT 6");
		codes.put(new Integer(POSITION_PORT_7), "PORT 7");
		codes.put(new Integer(POSITION_PORT_8), "PORT 8");

		codes.put(new Integer(POSITION_INLINE), "IN LINE");
		codes.put(new Integer(POSITION_RETRACT), "RETRACT");

		codes.put(new Integer(STATE_OKAY), "OKAY");
		codes.put(new Integer(STATE_INIT), "INIT");
		codes.put(new Integer(STATE_STANDBY), "STANDBY");
		codes.put(new Integer(STATE_SUSPENDED), "SUSPEND");
		codes.put(new Integer(STATE_WARN), "WARN");
		codes.put(new Integer(STATE_FAILED), "FAILED");
		codes.put(new Integer(STATE_SAFE), "SAFE");
		codes.put(new Integer(STATE_ON), "ON");
		codes.put(new Integer(STATE_OFF), "OFF");
		codes.put(new Integer(STATE_INVALID), "INVALID");
		codes.put(new Integer(STATE_ERROR), "ERROR");
		codes.put(new Integer(STATE_UNKNOWN), "UNKNOWN");
		codes.put(new Integer(STATE_DISABLED), "DISABLED");
		codes.put(new Integer(STATE_ENABLED), "ENABLED");
		codes.put(new Integer(STATE_ENGAGED), "ENGAGED");

	}

	/**
	 * Create a mechanism state.
	 */
	public BasicMechanismState() {
	}

	/**
	 * @param mechCategory
	 */
	public BasicMechanismState(String mechCategory) {
		this();
		this.mechCategory = mechCategory;
	}

	/**
	 * @return the mechanismState
	 */
	public int getMechanismState() {
		return mechanismState;
	}

	/**
	 * @param mechanismState
	 *            the mechanismState to set
	 */
	public void setMechanismState(int mechanismState) {
		this.mechanismState = mechanismState;
	}

	public String getStatusCategory() {
		return mechCategory;
	}

	public String stateToText(int state) {
		Integer cd = new Integer(state);
		if (codes.containsKey(cd))
			return (String) codes.get(cd);
		else
			return "UNKNOWN";
	}

	public int textToState(String text) throws Exception {
		if (!codes.containsValue(text.trim()))
			return NO_VALUE;

		Iterator<Integer> it = codes.keySet().iterator();
		while (it.hasNext()) {
			int ik = it.next();
			String val = (String) codes.get(ik);
			if (val.equals(text.trim()))
				return ik;
		}

		return NO_VALUE;

	}

}
