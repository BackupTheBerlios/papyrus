package com.papyrus.common;

import java.sql.*;
import org.postgresql.jdbc2.optional.*;

/**
 * Manage the Eurocash database connection.<BR>
 *
 * Use the pool by first calling the static init method.<BR>
 * Get the single instance and get a connection with the getConnection method.<BR>
 * Connections are pooled using the PostgreSQL pooling package.<BR>
 * Connections got from the pool should be properly closed using close() method
 * in order to regive it to the pool. 
 
 *
 * @version $Revision: 1.1 $
 */
public class PapyrusDatabasePool {

    /**
     * logger object used to log activity in this object
     */
    private static Logger logger_ = Logger.getInstance(PapyrusDatabasePool.class.getName());

    /** Unique instance of the pool */
    private static PapyrusDatabasePool instance_ = null;

    /** PostgreSQL pool connection from a datasource */
    private PoolingDataSource dataSource_ = null;

	/** Name of the dataBase Driver */
	private final static String DB_PROPERTIE_DRIVER = "database.driver";

	/** Name of the database name parameter in the dataBase.properties file */
	private final static String DB_PROPERTIE_DATABASENAME = "database.name";

    /** Name of the server name parameter in the dataBase.properties file */
    private final static String DB_PROPERTIE_SERVERNAME = "database.servername";

    /** Name of the login parameter in the dataBase.properties file */
    private final static String DB_PROPERTIE_USER = "database.user";

    /** Name of the password parameter in the dataBase.properties file */
    private final static String DB_PROPERTIE_PASSWORD = "database.password";

    /** 
     * Name of the max active connections parameter in the dataBase.properties file <BR> 
     * The maximum number of active connections that can be allocated in this pool. 
     */
    private final static String DB_PROPERTIE_MAX_ACTIVE_CONNECTION = "database.max.active.connection";

    /** 
     * Name max idle connections parameter in the dataBase.properties file <BR>
     * The maximum number of active connections that can remain idle in the pool, without extra ones
     * being released.     
     */
    private final static String DB_PROPERTIE_MAX_IDLE_CONNECTION = "database.max.idle.connection";

	/** Property value of db driver */
	String dbDriver_ = null;

    /** Property value for db url */
    String dbServerName_ = null;
 
    /** Property value for db name */
    String dbName_ = null;

    /** Property value for db user */
    String dbUser_ = null;

    /** Property value for db password */
    String dbPassword_ = null;

    /** Property value for nb max active connection */
    int dbMaxActiveConnection_ = 0;

    /** Property value for nb max idle connection */
    int dbMaxIdleConnection_ = 0;

    /**
     * attribute used to know in which status is the pool reinitialization (in case of error)
     * @see getConnection method
     */
    Integer poolReinitStatus_ = new Integer(0);

    /** 
     * Private constructor for singleton needs. Just sets properties. 
     */
    private PapyrusDatabasePool()
    throws PapyrusDbException
    {
        logger_.debug("PapyrusDatabasePool : begin");

        /*
         * Get config parameters
         */
        try {
            Config config = Config.getInstance();
            dbDriver_ = config.getProperty(DB_PROPERTIE_DRIVER);
            dbServerName_ = config.getProperty(DB_PROPERTIE_SERVERNAME);
            dbName_ = config.getProperty(DB_PROPERTIE_DATABASENAME);
            dbUser_ = config.getProperty(DB_PROPERTIE_USER);
            dbPassword_ = config.getProperty(DB_PROPERTIE_PASSWORD);
            dbMaxActiveConnection_ = config.getIntProperty(DB_PROPERTIE_MAX_ACTIVE_CONNECTION);
            dbMaxIdleConnection_   = config.getIntProperty(DB_PROPERTIE_MAX_IDLE_CONNECTION);
           
            logger_.debug("init : start, driver = " + dbDriver_ +
            			  ", servername = " + dbServerName_ +
             			  ", dataBaseName = " + dbName_ +
                          ", user = "  + dbUser_                +
                          ", password = " + dbPassword_            + 
                          ", dbMaxActiveConnection = " + dbMaxActiveConnection_ +
                          ", dbMaxIdleConnection = "  + dbMaxIdleConnection_);
        } catch (PapyrusException e) {
            throw new PapyrusDbException ("Error while reading database configuration:" + e);
        }
                        
        /* Load driver */
        try {
        	Class.forName(dbDriver_);
        	
        	logger_.debug("PapyrusDatabasePool : Driver loaded");
        } catch (Exception e) {
			throw new PapyrusDbException("PapyrusDatabasePool : Error (incorrect driver, " + e.getMessage() + ")");        
        }
                                   
        /*
         * Initialize connection pool
         */
		initializePoolConnections();
       
        logger_.debug("PapyrusDatabasePool : end");
    }

    /** 
     * Initialize the pool
     */
    public static synchronized void init() throws PapyrusDbException {   
    	logger_.debug("init : begin");
        
        if (instance_ == null) {
            logger_.debug("init : creating unique singleton instance");
            instance_ = new PapyrusDatabasePool();
        }
        
        logger_.debug("init : end");
    }

    /**
     * @return the unique instance of PapyrusDatabasePool
     */
    public static PapyrusDatabasePool getInstance() {
        if (null == instance_) {
            logger_.debug ("getInstance : error, Database connection is not yet initialised");
        }
        return instance_;
    }
    
    /** 
     * This Method initializes Connection Cache.
     */
    private void initializePoolConnections() throws PapyrusDbException {
        try {

			logger_.debug("initializePoolConnections : begin");

            /*
             * Create a data source
             */
             dataSource_ = new PoolingDataSource();
            
            //OracleConnectionPoolDataSource dataSource = new OracleConnectionPoolDataSource();

            // Configure the Datasource with proper values
            dataSource_.setDataSourceName("Papyrus Database Pool"); 
            dataSource_.setServerName(dbServerName_);
            dataSource_.setDatabaseName(dbName_);
            dataSource_.setUser (dbUser_);
            dataSource_.setPassword (dbPassword_);
            
            // Set Max Limit for the Cache
            dataSource_.setMaxConnections(dbMaxActiveConnection_);

            // Set Min Limit for the Cache
			dataSource_.setInitialConnections(dbMaxIdleConnection_);

			logger_.debug("initializePoolConnections : end");

        } catch (Exception ex) { // Trap SQL Errors
            throw new PapyrusDbException ("SQL Error while Instantiating Connection Pool : \n" +
                                      ex.toString());
        }
    }


    /**
     * @return a Connection instance
     */
    public java.sql.Connection getConnection() throws PapyrusDbException 
    {
        logger_.debug("getConnection : start");
        
        Connection connection = null;
		try {
			// get a connection from the pool connections
    		connection = dataSource_.getConnection();
    		
			logger_.debug("getConnection : end");
    		
    		return connection;   
        } catch (SQLException error) {
        	throw new PapyrusDbException ("Can not get connection from pool " + error);
		}
    }
}



