package org.moxhu.util.db.mongodb;


import org.moxhu.MoxhuObject;
import org.moxhu.exception.GeneralException;

import com.mongodb.DB;


/**
 * #MongoDBDriver.java
 * 
 */
public class MongoDBDriver implements MoxhuObject {

	private DB db;
		
	private long id;
	private boolean domonitor;

	private Thread ping;

	private long lastactivity;
	private boolean active = false;


	/**
	 * Creates a new instance of MongoDBDriver
	 * @throws GeneralException 
	 */
	public MongoDBDriver() throws GeneralException {
		id = System.currentTimeMillis();
		try {
			
			db = MongoDBConnectionManager.getInstance().getDBConnection();
			active = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralException(e.getMessage());
		}

	}

	public void startDriver() throws GeneralException {
		
		db.requestStart();
		db.requestEnsureConnection();


		domonitor = true;

		ping = new Thread(new Monitor());
		ping.start();
	}

	public void stopDriver() throws GeneralException {

		db.requestDone();

		domonitor = false;
	}

	public DB getDBConnection() throws GeneralException {
		lastactivity = System.currentTimeMillis();
		return db;
	}


	private class Monitor implements Runnable {

		public void run() {

			while (domonitor) {

				try {

					Thread.sleep(1000);

					if (lastactivity + 300000 < System.currentTimeMillis()) {

						LOGGER
						        .debug("[MongoDBDriver]    MongoDB connection has been idle for 5 mins. Doing ping");

						db.collectionExists("users");
					}
				} catch (Exception e) {
					active = false;
				}
			}
		}
	}

	public boolean isActive() {

		LOGGER.debug("[MongoDBDriver]   Testing connection");

		if (!active)
			return active;

	try {
			db.collectionExists("users");
		} catch (Exception e) {
			LOGGER.error("ERROR validating connection " + e.getMessage());
			active = false;
		}
		return active;
	}

	public long getId() {
		return id;
	}
}
