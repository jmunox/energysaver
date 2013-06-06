package org.moxhu.util.db.mongodb;

import org.moxhu.MoxhuObject;
import org.moxhu.exception.GeneralException;
import org.moxhu.web.app.ContextApplication;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;

public class MongoDBConnectionManager implements MoxhuObject {
	
	private static Mongo MONGO_INSTANCE;
	public static String MONGODB_HOST_KEY = "mongodb.host";
	public static String MONGODB_PORT_KEY = "mongodb.port";
	public static String MONGODB_ESAVER_DB_KEY = "mongodb.db.esaver";
	
	private String host;
	private int port; 
	private String dbName;
	
	/**
	 * The instance of the <code>MongoDBConnectionManager</code> class implementing the
	 * <b>Singleton Pattern</b>
	 */
	private static MongoDBConnectionManager instance = null;

	
	private MongoDBConnectionManager() throws GeneralException{
		try {
			host = "127.0.0.1";//ContextApplication.getInstance().getBootstrapConfiguration().getConfiguration(MONGODB_HOST_KEY, "127.0.0.1");
			port = 27017; //ContextApplication.getInstance().getBootstrapConfiguration().getConfiguration(MONGODB_PORT_KEY, 27017);
			dbName = "plugwise"; //ContextApplication.getInstance().getBootstrapConfiguration().getConfiguration(MONGODB_ESAVER_DB_KEY, "plugwise");
			
			MONGO_INSTANCE = new Mongo( new DBAddress( host, port, dbName ) );
		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralException(e.getMessage());
		}
	}
	

	/**
	 * Method that returns the instance of the <code>MongoDBConnectionManager</code>
	 * 
	 * @return the <code>MongoDBConnectionManager</code> instance
	 * @throws GeneralException 
	 */
	public static MongoDBConnectionManager getInstance() throws GeneralException {
		if (instance == null) {
			synchronized (MongoDBConnectionManager.class) {
				if (instance == null) {
					instance = new MongoDBConnectionManager();
				}
			}
		}
		return instance;
	}
	
	public void closeDBConnections() {
		MONGO_INSTANCE.close();
	}
	
	
	public DB getDBConnection() {		
		return MONGO_INSTANCE.getDB(dbName);
	}
	
	public void restartBDConnections() throws GeneralException {
		
		try {
			MONGO_INSTANCE.close();
			host = ContextApplication.getInstance().getBootstrapConfiguration().getConfiguration(MONGODB_HOST_KEY, "127.0.0.1");
			port = ContextApplication.getInstance().getBootstrapConfiguration().getConfiguration(MONGODB_PORT_KEY, 27017);
			dbName = ContextApplication.getInstance().getBootstrapConfiguration().getConfiguration(MONGODB_ESAVER_DB_KEY, "plugwise");
			
			MONGO_INSTANCE = new Mongo( new DBAddress( host, port, dbName ) );
		} catch (Exception e) {
			e.printStackTrace();
			throw new GeneralException(e.getMessage());
		}
	}
}
