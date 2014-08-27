/**
 * 
 */
package ngat.tcm;

import java.io.Serializable;

import ngat.net.telemetry.StatusCategory;


/** A container for status updates from telescope.
 * @author eng
 *
 */
public interface TelescopeStatus extends Serializable, StatusCategory {

	/** Constant: Indicates that quantity has not been set.*/
    public static final int NO_VALUE      = -1;

	   /** Constant: Indicates that a drive is in state STOPPED.*/
    public static final int MOTION_STOPPED    = 220;

    /** Constant: Indicates that a drive is in state IN_POSITION.*/
    public static final int MOTION_INPOSITION = 221;

    /** Constant: Indicates that a drive is in state TRACKING.*/
    public static final int MOTION_TRACKING   = 222;

    /** Constant: Indicates that a drive is in state MOVING.*/
    public static final int MOTION_MOVING     = 223;

    /** Constant: Indicates that a drive is in state LIMIT.*/
    public static final int MOTION_LIMIT      = 224;

    /** Constant: Indicates that a drive is in state OVERRIDE.*/
    public static final int MOTION_OVERRIDE   = 225;

    /** Constant: Indicates that a drive is in state EXPIRED.*/
    public static final int MOTION_EXPIRED    = 226;

    /** Constant: Indicates that a drive is in state OFF_LINE.*/
    public static final int MOTION_OFF_LINE   = 227;
    
    /** Constant: Indicates that a drive is in state UNKNOWN.*/
    public static final int MOTION_UNKNOWN    = 228;

    /** Constant: Indicates (SIMULATION_MODE) that a drive is in state MOTION_INCREASING .*/
    public static final int MOTION_INCREASING = 230;

    /** Constant: Indicates (SIMULATION_MODE) that a drive is in state MOTION_DECREASING.*/
    public static final int MOTION_DECREASING = 231;

    /** Constant: Indicates (SIMULATION_MODE) that a drive is in state SLEWING.*/
    public static final int MOTION_SLEWING    = 232;

    /** Constant: Indicates that a drive is in state ERROR.*/
    public static final int MOTION_ERROR      = 233;

    /** Constant: Indicates that a drive is in state WARNING.*/
    public static final int MOTION_WARNING    = 234;
    
    /** Constant: Indicates that a mechanism is in state IN.*/
    public static final int POSITION_IN      = 340;

    /** Constant: Indicates that a mechanism is in state OUT.*/
    public static final int POSITION_OUT     = 341;

    /** Constant: Indicates that a mechanism is in state CLOSED.*/
    public static final int POSITION_CLOSED  = 342;
    
    /** Constant: Indicates that a mechanism is in state OPEN.*/
    public static final int POSITION_OPEN    = 343;

    /** Constant: Indicates that a mechanism is in state PARTIAL.*/
    public static final int POSITION_PARTIAL = 344;
    
    /** Constant: Indicates that a mechanism is in state UNKNOWN.*/
    public static final int POSITION_UNKNOWN = 345;

    /** Constant: Indicates that a mechanism is in location STOWED.*/
    public static final int POSITION_STOWED  = 351;
    
    /** Constant: Indicates that a mechanism is in location PORT_1.*/
    public static final int POSITION_PORT_1  = 352;
    
    /** Constant: Indicates that a mechanism is in location PORT_2.*/
    public static final int POSITION_PORT_2  = 353;
    
    /** Constant: Indicates that a mechanism is in location PORT_3.*/
    public static final int POSITION_PORT_3  = 354;
    
    /** Constant: Indicates that a mechanism is in location PORT_4.*/
    public static final int POSITION_PORT_4  = 355;

    /** Constant: Indicates that a mechanism is in location PORT_5.*/
    public static final int POSITION_PORT_5  = 356;
    
    /** Constant: Indicates that a mechanism is in location PORT_6.*/
    public static final int POSITION_PORT_6  = 357;
    
    /** Constant: Indicates that a mechanism is in location PORT_7.*/
    public static final int POSITION_PORT_7  = 358;
    
    /** Constant: Indicates that a mechanism is in location PORT_8.*/
    public static final int POSITION_PORT_8  = 359;

     /** Constant: Indicates that a mechanism is in state IN_LINE.*/
    public static final int POSITION_INLINE  = 360;

    /** Constant: Indicates that a mechanism is in state RETRACT.*/
    public static final int POSITION_RETRACT = 361;

    /** Constant: Indicates that a subsystem is in state OKAY.*/
    public static final int STATE_OKAY      = 460;

    /** Constant: Indicates that a subsystem is in state INIT.*/
    public static final int STATE_INIT      = 461;

    /** Constant: Indicates that a subsystem is in state STANDBY.*/
    public static final int STATE_STANDBY   = 462;

    /** Constant: Indicates that a subsystem is in state SUSPENDED.*/
    public static final int STATE_SUSPENDED = 463;

    /** Constant: Indicates that a subsystem is in state WARN.*/
    public static final int STATE_WARN      = 464;

    /** Constant: Indicatesthat a subsystem is in state FAILED.*/
    public static final int STATE_FAILED    = 465;

    /** Constant: Indicates that a subsystem is in state SAFE.*/
    public static final int STATE_SAFE      = 466;

    /** Constant: Indicates that a subsystem is in state OFF.*/
    public static final int STATE_OFF       = 467;
   
    /** Constant: Indicates that a subsystem is in state ON .*/
    public static final int STATE_ON        = 468;
   
    /** Constant: Indicates that a subsystem is in state INVALID.*/
    public static final int STATE_INVALID   = 469;

    /** Constant: Indicates that a subsystem is in state ERROR .*/
    public static final int STATE_ERROR     = 470;

    /** Constant: Indicates that a subsystem is in state UNKNOWN.*/
    public static final int STATE_UNKNOWN   = 471;

    /** Constant: Indicates that a subsystem is in state DISABLED.*/
    public static final int STATE_DISABLED  = 472;

    /** Constant: Indicates that a subsystem is in state ENABLED.*/
    public static final int STATE_ENABLED   = 473;

    /** Constant: Indicates that a subsystem is in state ENGAGED.*/
    public static final int STATE_ENGAGED   = 474;
   
}
