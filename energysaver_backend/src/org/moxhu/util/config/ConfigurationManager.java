/*
 * ConfigurationManager.java
 *
 */

package org.moxhu.util.config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.moxhu.MoxhuObject;


/**
 *
 */

public class ConfigurationManager implements MoxhuObject{
    
	private static ConfigurationManager _instance = new ConfigurationManager();
    

    /**Generic object used for synchronization purposes*/
    private Object lock=new Object();
    
    /** Creates a new instance of ConfigurationManager */
    private ConfigurationManager() {
    }
    
    public static Configurations getConfigurations(String filePath) {
    	return getInstance()._getConfigurations(filePath);
    }
    
    public Configurations _getConfigurations(String filePath) {
    	synchronized (lock)
        {
    		Configurations config = new Configurations();
    		 Properties props = new Properties();
    		try{
    			//get configurations from 
    			//props.load(new java.io.FileInputStream(filePath));
    			props.load(this.getClass().getClassLoader().getResourceAsStream(filePath));    			        
    			config = new Configurations(props, filePath);
			} catch (FileNotFoundException e) {
				LOGGER.error("[ConfigurationManager] Error while loading properties",e);
			} catch (IOException e) {
				LOGGER.error("[ConfigurationManager] Error while loading properties",e);
			}
    		return config;
    		
        }
    }
    
    public static ConfigurationManager getInstance(){
        return _instance;
    }
    
    
    public static void storeConfigurations(Configurations config) {
    	 getInstance()._storeConfigurations(config);
    }
    
    /**
     *Stores the system's properties to the path specified in configFile
     */
    public void _storeConfigurations(Configurations config) {
        synchronized (lock)
        {
        	Properties props = config.getProperties();
            try
            {
            	LOGGER.debug("[ConfigurationManager] Storing configuration" + config);
                FileOutputStream fileos=new FileOutputStream(config.getFilePath());
                props.store(fileos, "");
                fileos.close();
            }
            catch (Exception x)
            {
            	System.out.println("[ConfigurationManager] Unable to store configuration!" + x.getMessage());
                x.printStackTrace();
                props=null;
            }
        }
    }
}