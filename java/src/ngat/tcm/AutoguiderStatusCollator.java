/**
 * 
 */
package ngat.tcm;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import ngat.autoguider.command.StatusGuideActiveCommand;
import ngat.autoguider.command.StatusTemperatureGetCommand;
import ngat.net.TelnetSocketConnection;
import ngat.util.ControlThread;

/**
 * @author eng
 *
 */
public class AutoguiderStatusCollator extends ControlThread {
	
	private BasicTelescope scope;

	private String autoguiderName;
	
	private String agStatusHost;

	private int agStatusPort;

	private long interval;

	
	/**
	 * @param scope
	 * @param autoguiderName
	 * @param agStatusHost
	 * @param agStatusPort
	 * @param interval
	 */
	public AutoguiderStatusCollator(BasicTelescope scope, String autoguiderName, String agStatusHost, int agStatusPort,
			long interval) {
		super("AG_"+autoguiderName, true);
		this.scope = scope;
		this.autoguiderName = autoguiderName;
		this.agStatusHost = agStatusHost;
		this.agStatusPort = agStatusPort;
		this.interval = interval;
	}

	/* (non-Javadoc)
	 * @see ngat.util.ControlThread#initialise()
	 */
	@Override
	protected void initialise() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see ngat.util.ControlThread#mainTask()
	 */
	@Override
	protected void mainTask() {
		
		try {
			Thread.sleep(interval);
		} catch (InterruptedException ix) {
		}
		
		//System.err.println("AgStatusCollator: send message...");
		// Try to get Temperature AG status directly from AG..
		
		boolean online  = false;
		
		boolean agactive = false;
		boolean agactivegot = false;
		try {			
			agactive = checkAgActive();
			agactivegot = true;
		} catch (Exception e) {	
			e.printStackTrace();
			//System.err.println("AGStatusCollator::StatusGuideActiveCommand: Error getting status: "
						//	+ e.getMessage());
		
		}
			
		double agtemp = 0.0;
		boolean agtempgot = false;
		try {
			agtemp = checkAgTemp();
			// this is kelvins 
			agtempgot = true;
		} catch (Exception e) {
			e.printStackTrace();
			//System.err.println("AGStatusCollator::StatusTemperatureGetCommand: Error getting ccd temperature: "
							//+ e.getMessage());
		}
	
		if (agtempgot && agactivegot)
			online = true;
		
		
		int agTempStatus = 0;
		
		if (agtempgot) {
		
			if (agtemp < 223) {				
				agTempStatus = AutoguiderActiveStatus.AUTOGUIDER_TEMPERATURE_FAIL_LOW;				
			} else if 
				(agtemp < 228) {
				agTempStatus = AutoguiderActiveStatus.AUTOGUIDER_TEMPERATURE_WARN_LOW;					
			} else if
				(agtemp < 238) {				
				agTempStatus = AutoguiderActiveStatus.AUTOGUIDER_TEMPERATURE_OKAY;					
			} else if (agtemp < 243) {				
				agTempStatus = AutoguiderActiveStatus.AUTOGUIDER_TEMPERATURE_WARN_HIGH;	
			} else {				
				agTempStatus = AutoguiderActiveStatus.AUTOGUIDER_TEMPERATURE_FAIL_HIGH;	
			}
		}
		
			AutoguiderActiveStatus agActiveStatus = new AutoguiderActiveStatus();
			agActiveStatus.setAutoguiderName(autoguiderName);
			agActiveStatus.setStatusTimeStamp(System.currentTimeMillis());
			// could be unknown...
			agActiveStatus.setActiveStatus(agactive);
			agActiveStatus.setOnline(online);
			// could be unknown...
			agActiveStatus.setTemperature(agtemp);
			// could be unknown...
			agActiveStatus.setTemperatureStatus(agTempStatus);
			
			//System.err.println("Sending: "+agActiveStatus);
			
			if (scope != null)
			scope.updateAutoguiderActiveStatus(agActiveStatus);
			
		// loop round,
				
		
	}

	/* (non-Javadoc)
	 * @see ngat.util.ControlThread#shutdown()
	 */
	@Override
	protected void shutdown() {
		// TODO Auto-generated method stub

	}
	
	/** Collect the ag active status.
	 * @return 
	 */
	public boolean checkAgActive() throws Exception {

		StatusGuideActiveCommand command = new StatusGuideActiveCommand(agStatusHost, agStatusPort);
		command.run();
		
		if (command.getRunException() != null) {
			throw command.getRunException();
		} else {

			command.getCommandFinished();
			command.getParsedReplyOK();
			
			boolean active = command.isGuiding();
			return active;
		}

	}

	/** Collect the AG temperature.
	 * @return The AG temperature in Kelvins.
	 * @throws Exception
	 */
	public double checkAgTemp() throws Exception {

		StatusTemperatureGetCommand command = new StatusTemperatureGetCommand(agStatusHost, agStatusPort);
		command.run();
		
		if (command.getRunException() != null) {
			throw command.getRunException();
		} else {

			command.getCommandFinished();
			command.getParsedReplyOK();
			
			Date timestamp = command.getTimestamp();
			double temp = command.getCCDTemperature();
		
			return temp+273.0;
		}

	}

	

}
