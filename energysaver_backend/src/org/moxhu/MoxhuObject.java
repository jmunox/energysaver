package org.moxhu;

import org.apache.log4j.Logger;
import org.moxhu.logging.AccessLogger;
import org.moxhu.logging.ComponentLogger;
import org.moxhu.logging.GeneralLog;
import org.moxhu.logging.StatsGeneralLogger;

public interface MoxhuObject {
	
	/**
	 * holds the logger object for the general log file
	 */
	public static Logger LOGGER = ComponentLogger.LOGGER;
	
	public static Logger SYS_LOGGER = GeneralLog.LOGGER;
	
	public static Logger STATS_LOGGER = StatsGeneralLogger.LOGGER;
	
	public static Logger ACCESS_LOGGER = AccessLogger.LOGGER;

}
