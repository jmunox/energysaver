package org.moxhu.util.db.mongodb;

//import org.apache.commons.pool.impl.GenericObjectPool;
//import org.apache.commons.pool.impl.StackObjectPool;

import org.moxhu.MoxhuObject;
import org.moxhu.exception.GeneralException;
import org.moxhu.web.app.ContextApplication;


/**
 * #MongoDBDriverFactory.java
 * 
 * @author Jesus
 * 
 */
public class MongoDBDriverFactory implements MoxhuObject {

	private static NonBlockingPool POOL = null;
	public static final String DRIVER_POOL_MAX_PROPERTY = "driver/pool/max";
	private int maxDrivers = 1;
	
	/**
	 * The instance of the <code>MongoDBDriverFactory</code> class implementing the
	 * <b>Singleton Pattern</b>
	 */
	private static MongoDBDriverFactory instance = null;

	/**
	 * Creates a new instance of MongoDBDriverFactory
	 */
	private MongoDBDriverFactory() {
		maxDrivers = ContextApplication.getInstance().getBootstrapConfiguration().getConfiguration(
		        DRIVER_POOL_MAX_PROPERTY, 1);
	}

	
	/**
	 * Method that returns the instance of the <code>MongoDBDriverFactory</code>
	 * 
	 * @return the <code>MongoDBDriverFactory</code> instance
	 */
	public static MongoDBDriverFactory getInstance() {
		if (instance == null) {
			synchronized (MongoDBDriverFactory.class) {
				if (instance == null) {
					instance = new MongoDBDriverFactory();
				}
			}
		}
		return instance;
	}
	
	public MongoDBDriver getDriver(Object params) throws GeneralException {
		MongoDBDriver driver = null;
		try {
			driver = (MongoDBDriver) POOL.borrowObject();
		} catch (Exception e) {
			LOGGER.error("Exception while getting Driver from pool"
			        + e.getMessage(), e);
		}
		LOGGER.debug("DRIVERPOOL getDriver, id="
		        + ((MongoDBDriver) driver).getId());
		return driver;
	}

	public void returnDriver(MongoDBDriver driver) throws GeneralException {
		try {
			LOGGER.debug("DRIVERPOOL returnDriver , id="
			        + ((MongoDBDriver) driver).getId());
			POOL.returnObject(driver);
		} catch (Exception e) {
			LOGGER.error(
			        "DRIVERPOOL Exception while returning Driver from pool"
			                + e.getMessage(), e);
		}
	}

	public void startFactory() throws GeneralException {
		// POOL = new GenericObjectPool(new MongoDBDriverPoolableObjectFactory(),
		// 15,GenericObjectPool.WHEN_EXHAUSTED_BLOCK, 500, 5) ;
		POOL = new NonBlockingPool(new MongoDBDriverPoolableObjectFactory(),
		        maxDrivers);
	}

	public void stopFactory() throws GeneralException {
		try {
			LOGGER.debug("DRIVERPOOL Closing");
			POOL.close();
		} catch (Exception e) {
			LOGGER.warn(
			        "Exception while Closing Driver pool " + e.getMessage(), e);
		}
		MongoDBConnectionManager.getInstance().closeDBConnections();
	}
}