package org.moxhu.logging;

import org.apache.log4j.Logger;

/**
 *
 * :author  IGGY
 */
public class ComponentLogger
{

	/**
	 * holds the logger object for the general log file
	 */
	public static Logger LOGGER = Logger.getLogger(ComponentLogger.class);
	
	/**
	 * Gets the Logger
	 * @return
	 */
	public static Logger getLogger(){
		return LOGGER;
	}
    
}
