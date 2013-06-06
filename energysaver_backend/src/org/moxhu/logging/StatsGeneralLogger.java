package org.moxhu.logging;

import org.apache.log4j.Logger;

/**
 *  Class for Statistics Logging purposes
 *  
 * @author   Jes�s Mu�oz A
 * @version 2.0.rc1.1, Wednesday, November 14, 2007
 */
public class StatsGeneralLogger
{
	/**
	 * holds the logger object for the statistics general log file
	 */
	public static Logger LOGGER = Logger.getLogger(StatsGeneralLogger.class);
	

	/**
	 * Gets the Logger
	 * @return
	 */
	public static Logger getLogger(){
		return LOGGER;
	}
}
