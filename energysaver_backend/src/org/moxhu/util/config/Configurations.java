package org.moxhu.util.config;


import java.io.FileOutputStream;
import java.util.Properties;

import org.moxhu.MoxhuObject;

public class Configurations implements MoxhuObject
{
	private Properties _properties;
	private String _configFilePath = "";
    
	public Configurations(){
		_properties = new Properties();
	}
	
	public Configurations(Properties properties, String filePath){
		_properties = (Properties)properties.clone();
		setFilePath(filePath);
	}
	
    public String getFilePath(){
        return _configFilePath;
    }
    
    public void setFilePath(String filePath){
        this._configFilePath = filePath;
    }
    
    public synchronized boolean saveConfiguration(String key, String value){
        boolean saved = false;
        
        try{
        	_properties.setProperty(key, value);
        	_properties.store(new FileOutputStream(getFilePath()), null);
            saved = true;
        }catch(Exception e){
            saved = false;
        }
        
        return saved;
    }
    
    public synchronized Properties getProperties(){
    	return (Properties)_properties.clone();
    }
    
    public String getConfiguration(String key)
    {
        return _properties.getProperty(key);
    }
    
    public String getConfiguration(String key, String defaultValue)
    {
        return _properties.getProperty(key, defaultValue);
    }
    
    public void set(String key, String value)
    {
    	if(value==null)
    		value="";
    	_properties.setProperty(key, value);
    }
    
    public int getConfiguration(String key, int defaultValue)
    {
        try
        {
            return Integer.parseInt(getConfiguration(key, ""+defaultValue));
        }
        catch (Exception x)
        {
            return defaultValue;
        }
    }
    
    public long getConfiguration(String key, long defaultValue)
    {
        try
        {
            return Long.parseLong(getConfiguration(key, ""+defaultValue));
        }
        catch (Exception x)
        {
            return defaultValue;
        }
    }
    
    public double getConfiguration(String key, double defaultValue)
    {
        try
        {
            return Double.parseDouble(getConfiguration(key, ""+defaultValue));
        }
        catch (Exception x)
        {
            return defaultValue;
        }
    }
    
    public float getConfiguration(String key, float defaultValue)
    {
        try
        {
            return Float.parseFloat(getConfiguration(key, ""+defaultValue));
        }
        catch (Exception x)
        {
            return defaultValue;
        }
    }
    
    public boolean getConfiguration(String key, boolean defaultValue)
    {
        return getBooleanConfiguration(key, defaultValue);
    }
    
    protected boolean getBooleanConfiguration(String key, boolean defaultValue)
    {
        return getConfiguration(key, ""+defaultValue).trim().equalsIgnoreCase("true");
    }
    
    public Configurations clone(){
    	Configurations conf = new Configurations(_properties, getFilePath());
    	return conf;
    }
}
