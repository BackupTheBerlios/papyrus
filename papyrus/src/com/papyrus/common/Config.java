package com.papyrus.common;

import java.util.*;
import java.lang.Exception;
import java.io.IOException;
import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.*;
// log4j 1.1.3 does not compile this class
import org.apache.log4j.xml.DOMConfigurator;


/**
 * Config is a singleton that loads all the config 
 * files content and stores the corresponding properties 
 * in a Properties map.
 * @author Didier Lafforgue
 */
public class Config {

    /**
     * logger object used to log activity in this object
     */
    private static Logger logger_ = Logger.getInstance(Config.class.getName());
    
    /**
     * array containing names of all config files to load <br> 
     * all this files must be in the application classpath
     */
    private static String[] mandatoryConfigFiles_ = {
    	"/properties/DataBaseConnection.properties", 
    };

    /**
     * array containing names of optional config files to load <br> 
     * these files are loaded if they are in the application classpath
     */
    private static String[] optionnalConfigFiles_ = {
    };

    /**
     * unique instance reference
     */
    private static Config config_ = null;

    /**
     * map used to store all config files content 
     */
    private Properties prop_ = null;


    /**
     * Initializes configuration object and logs librarie
     * @throws IOexception if any error occurs while loading config files, or initializing log librarie 
     */
    public static void initConfAndLogs() throws PapyrusException, FactoryConfigurationError { 
        
        /*
         * Init logs using xml configuration file 
         */

        String configFileName = "/properties/Logs.xml" ;
        java.io.InputStream is = null;
        is = Config.class.getResourceAsStream(configFileName);

        if (null != is) {
            try {
				//System.err.println("is = " + is.available());
                new DOMConfigurator().doConfigure(is, LogManager.getLoggerRepository());
            } catch (FactoryConfigurationError e) {
				System.err.println("initConfAndLogs : Error while reading Papyrus logs component <" + e + ">");
            	throw e;
        	} finally {
                try {
                    is.close();
                } catch (IOException f) {  }
            }
        } else {
			System.err.println("initConfAndLogs : Error while initializing Papyrus log component <can not find config file Logs.xml>");
        }
        
		/*
		 * Init configuration object
		 */
		Config PapyrusConfig = null;
    		
		try { 
			   PapyrusConfig = Config.loadFromFiles();
		} catch (PapyrusException e) {
			   System.err.println("initConfAndLogs : Error while reading Papyrus configuration <" + e.getMessage() + ">");
			   throw e;
		}
    }
    
    
    
    /**
     * Private constructor : reads properties files 
     * and store properties in prop_ map, then initialize log4j
     * @exception CGException thrown if one of the mandatory configuration files can not be found 
     */
    private Config() throws PapyrusException { 
        prop_ = new Properties();

        /*
         * Read mandatory config files content
         */
        for (int i=0 ; i < mandatoryConfigFiles_.length ; i++) {
            String configFileName = mandatoryConfigFiles_[i];
            java.io.InputStream is = null;
            is = getClass().getResourceAsStream(configFileName);
                 
            if (null != is) {
                try {
                    prop_.load(is);
               
                } catch (IOException e) {
                    throw new PapyrusException ("can not find mandatory config file: " + configFileName);
                } finally {
                    try {
                        is.close();
                    } catch (IOException f) {
                    }
                }
            } else {
                throw new PapyrusException ("can not find mandatory config file:" + configFileName);
            }
        }

        /*
         * Read optional config files content
         */
        int nbOptionalFilesLoaded = 0;
        for (int i=0 ; i < optionnalConfigFiles_.length ; i++) {
            String configFileName = optionnalConfigFiles_[i];
            java.io.InputStream is = null;
            is = getClass().getResourceAsStream(configFileName);

            if (null != is) {
                try {
                    prop_.load(is);
                    nbOptionalFilesLoaded ++;
                } catch (IOException f) {
                    // nothing to do here, this is an optionnal config file                
                } finally {
                    try {
                        is.close();
                    } catch (IOException f) {
                    }
                }
            }
        }

        if (nbOptionalFilesLoaded == 0) {
			//logger_.debug("Config : no optional config file found");
        }
		//logger_.debug("Config :end (config initialized successfully)");
    }


    /**
     * This method load configuration content from files. <br>
     * The names of config files to load are referenced in configFiles_ attribute. <br> 
     * This method should be used at startup to load all config files, or later
     * on to refresh config content from files.
     * @return the reference to the new refreshed unique instance 
     */
    static synchronized public Config loadFromFiles () throws PapyrusException {
        config_ = new Config();
        return config_;
    }

    /** 
     * @return a reference to the unique Config object instance  
     */
    static synchronized public Config getInstance() throws PapyrusException
    {   
        if (config_ == null) {
            throw new PapyrusException ("Config object not initialized before first use");
        }
        return config_ ;
    }


    /** 
     * @param name the name of the property to retrieve
     * @return the specified property as String
     * @throws Exception if the specified property can not be found
     */
    public String getProperty(String name)
    {   
    	logger_.debug("getProperty : begin name = " + name);
        
    	String property = prop_.getProperty(name);
        
    	logger_.debug("getProperty : end value = " + property);
        return property;
    }

    /** 
     * @param name the name of the property to retrieve
     * @return the specified property as boolean
     * @throws Exception if the specified property can not be found or 
     * if it is not a valid boolean
     */
    public boolean getBooleanProperty(String name) throws PapyrusException {
        logger_.debug("getBooleanProperty : begin name = " + name);

        String value = getProperty(name);
        if (value == null) {
            throw new PapyrusException("Config.getBooleanProperty : property not found:" + name);
        }
        value = value.trim().toLowerCase();
        if (value.equals("true")) {
            logger_.debug("getBooleanProperty : end, return true");
            return true;
        }
        if (value.equals("false")) {
            logger_.debug("getBooleanProperty : end, return false");
            return false;
        }
        throw new PapyrusException("Config.getBooleanProperty : not a boolean value for property " + name + ", value " + value);
    }

    /** 
     * @param name the name of the property to retrieve
     * @return the specified property as char[]
     * @throws Exception if the specified property can not be found
     */
    public char[] getCharProperty(String name)
    {   
        logger_.debug("getCharProperty : begin name = " + name);
        String value = getProperty(name);
        char[] valueAsChar = value.toCharArray();
        return valueAsChar;
    }

    /** 
     * @param name the name of the property to retrieve
     * @return the specified property as int
     * @throws Exception if the specified property can not be found or 
     * if it is not a valid int
     */
    public int getIntProperty(String name) throws PapyrusException  {
        logger_.debug("getIntProperty : begin name = " + name);
        String value = getProperty(name);
        try {
            int intValue = Integer.parseInt(value);
            return intValue;
        } catch (NumberFormatException e) {
            logger_.error(-1,"getIntProperty: not an int  value for property " + name + ", value " + value); 
            throw new PapyrusException("Config.getIntProperty : not an int value for property " + name + ", value " + value);
        }
    }


    /** 
     * @param name the name of the property to retrieve
     * @return the specified property as long
     * @throws Exception if the specified property can not be found or 
     * if it is not a valid int
     */
    public long getLongProperty(String name) throws PapyrusException {
        logger_.debug("getlongProperty : begin name = " + name);
        String value = getProperty(name);
        try {
            long longValue = Long.parseLong(value);
            return longValue;
        } catch (NumberFormatException e) {
            logger_.error(-1,"getLongProperty: not a long  value for property " + name + ", value " + value); 
            throw new PapyrusException("Config.getLongProperty : not a long value for property " + name + ", value " + value);
        }
    }

    /** 
     * @return the current complete Properties object
     */
    public Properties getProperties(){
        return prop_;
    } 

    public String toString() {
        return prop_.toString();
    }

    /** 
     * @param name the name of the property to set
     * @param the specified property value
     */
    public void setProperty(String name,String value){

        logger_.debug("setProperty : begin name = " + name +", value = "+ value);
        prop_.setProperty(name,value);
        logger_.debug("setProperty : end");
    }

    /**
     * Method main, used for getting one propertie value   
     * @param args key of the property to get 
     */
    public static void main(String[] args) 
    {
        try {
            Config.loadFromFiles();
            String value = config_.getProperty(args[0]); 

            //the value is passed to the script through STDOUT
        } catch (Exception e) {
            System.err.println ("[KO] Couldn't retrieve the property:" + e);
        }
    }


}

