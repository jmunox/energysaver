package org.moxhu.logging;

import org.apache.log4j.Logger;

/**
 *  Class for General Logging purposes
 *  
 * @author   Jesus Munoz A
 */
public class GeneralLog
{
	/**
	 * holds the logger object for the general log file
	 */
	public static Logger LOGGER = Logger.getLogger(GeneralLog.class);
	
	/**
	 * Gets the Logger
	 * @return
	 */
	public static Logger getLogger(){
		return LOGGER;
	}
}
