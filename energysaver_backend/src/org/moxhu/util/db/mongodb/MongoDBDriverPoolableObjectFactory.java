package org.moxhu.util.db.mongodb;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.moxhu.MoxhuObject;

/**
 * An implementation of a BasePoolableObjectFactory for MongoDB objects
 * 
 * @author Jesus
 * 
 * 
 */
public class MongoDBDriverPoolableObjectFactory extends BasePoolableObjectFactory implements MoxhuObject {

	
	public void destroyObject(Object arg0) throws Exception {
		MongoDBDriver driver = (MongoDBDriver) arg0;
		LOGGER.debug("DRIVERPOOL: Destroying Object, id="
		        + driver.getId());
		driver.stopDriver();
		driver = null;
		arg0 = null;
	}

	public Object makeObject() throws Exception {
		MongoDBDriver driver = new MongoDBDriver();
		LOGGER.debug("DRIVERPOOL: Creating New Object, id="
		        + driver.getId());
		driver.startDriver();
		return driver;
	}

	public boolean validateObject(Object arg0) {
		MongoDBDriver driver = (MongoDBDriver) arg0;
		MongoDBDriverFactory.LOGGER.debug("DRIVERPOOL: Validating Object, id="
		        + driver.getId());
		return driver.isActive();
	}

}
