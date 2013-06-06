package org.moxhu.logging;

import org.apache.log4j.Logger;

/**
 *  Class for Access Logging purposes
 *  
 * @author   Jesus Munoz Alcantara
 */
public class AccessLogger
{
	/**
	 * holds the logger object
	 */
	public static Logger LOGGER = Logger.getLogger(AccessLogger.class);
		
	/**
	 * Gets the Logger
	 * @return
	 */
	public static Logger getLogger(){
		return LOGGER;
	}
}