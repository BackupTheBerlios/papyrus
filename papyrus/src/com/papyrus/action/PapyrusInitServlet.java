package com.papyrus.action;


import java.io.*;
import java.util.Locale;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.FactoryConfigurationError;

import java.sql.SQLException;
import com.sun.rowset.*;

import com.papyrus.data.mapping.db.DBMappingFactory;
import com.papyrus.data.mapping.form.FormMappingFactory;
import com.papyrus.data.administration.agency.*;
import com.papyrus.data.type.*;
//import com.papyrus.data.form.*;
import com.papyrus.data.security.*;
import com.papyrus.common.*;
import com.papyrus.menu.*;

/**
 * Servlet used for initializing Papyrus Gestion Commerciale application.                   <BR>
 * This servlet is loaded at (application server) resin's startup (init method is called). <BR>
 * Basically, this servlet :                                             <BR>
 *     <LI> Reads all project configuration
 *     <LI> Initializes log api (log4j)
 *     <LI> Initializes database connection pool
 *     <LI> Initializes task scheduler (java cron) component
 *     <LI> Is the input of the Papyrus Gestion Commerciale webapp : redirect the http request to the right JSP 
 *
 * @author Did
 * @version 1.0
 */
public class PapyrusInitServlet extends HttpServlet {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = null; 
	
	/**
	 * Integer object to know if app has been already configured <BR>
	 * If = 0 , then it is configured
	 */
	static private Integer isConfigured_ = new Integer(1);
	
	/**
	 * Method called at resin's startup
	 */
	public synchronized void init() 
	throws ServletException  {
		
		try {
			super.init();
			
			synchronized (isConfigured_) {
				
				if (isConfigured_.intValue() != 0) {
					
					/*
					 * add shutdown handler
					 */
					
					Runtime.getRuntime().addShutdownHook( new Thread() {         
						public void run() {
							clean();
						}
					});
					
					/*
					 * initialize config object (load config from properties files)
					 * and init log4j logs api
					 */
					try {
						Config.initConfAndLogs();
					} catch (PapyrusException e) {
						throw new ServletException (e.getMessage ());
						
					} catch (FactoryConfigurationError e) {
						throw new ServletException (e.getMessage ());
					}
					
					logger_ = Logger.getInstance(PapyrusInitServlet.class.getName());
					
					// set locale to France (used by date computation & number format)
					try {
						Locale.setDefault( Locale.FRANCE );
					} catch (Exception e) {
						throw new ServletException (e.getMessage ());
					}
					
					logger_.warn (99000, "Papyrus Service application starts");
					
					/*
					 * init Kjava Db connection 
					 */
					try {
						logger_.debug ("init:initialise the database connection...");
						PapyrusDatabasePool.init();
						logger_.debug ("init:the database connection has been initialised");
						
					} catch (PapyrusDbException e) {
						logger_.error (20000, "error when initialising database connection <" + e + ">");
						throw new ServletException (e.getMessage ());
					}

					/* Load Config */
					loadConfig(); 

					// set app as configured
					isConfigured_ = new Integer(0);
					
					/* Test Rowset */
					try {
						CachedRowSetImpl rowSet = new CachedRowSetImpl();
					} catch (SQLException e) { 
						logger_.debug("init : Rowset KO (" + e.getMessage() + ")");
					}
					
					logger_.warn (99001, "Papyrus application started successfully");
				}
			}
			
			logger_.debug ("init : end");
			
		}
		catch (ServletException s) {
			logger_.error (90010, "Error while starting Papyrus application <" + s + ">");
			throw s;
		}
		catch (Throwable t) {
			logger_.error (90010, "Error while starting Papyrus application <" + t + ">");
			throw new ServletException (t);
		}
	}
	
	/**
	 * Method called by servlet container in order to stop application
	 */
	public void clean() {
		
		logger_.debug ("clean:begin");
		logger_.warn (99010, "Papyrus application stops");
		logger_.warn (99011, "Papyrus application stopped successfully");
		logger_.debug ("clean:end");
	}
	
	/**
	 * Check that there is one HttpSession
	 * Try to generate pages not kept in the browser cache
	 * Set the Locale (in order to have wps:text tag working)
	 * Retrieves and call the appropriate Action based on action parameter
	 * @param req the HTTP request
	 * @param res the HTTP answer
	 */
	public void doGet (HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		logger_.debug("doGet : begin");
		
		// Do nothing
		
		logger_.debug("doGet : end");
	}
	
	/**
	 * Redirect to doGet
	 * @param req the HTTP request
	 * @param res the HTTP answer
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) 
	throws javax.servlet.ServletException, IOException {
		logger_.debug("doPost : begin");
		doGet(req, res);
		logger_.debug("doPost : end");
	}
	
	/**
	 * load from DB: menu tree, options list, suppliers list, agency list
	 * store all in application variables 
	 */
	public void loadConfig() throws PapyrusException {
		logger_.debug("loadConfig : begin");
		
		ServletContext application = this.getServletContext();
	
		try {
			/* Menu Tree */
			if (null == application.getAttribute("menu")) {
				Tree menu = new Tree();
				
				application.setAttribute("menu", menu);
				logger_.debug("loadConfig : menu OK");
			}
			
			/* Agency List */
			if (null == application.getAttribute("agenciesBean")) {
				AgenciesBean agenciesBean = new AgenciesBean();
				
				/* load all the agencies */
				agenciesBean.load();
				
				application.setAttribute("agenciesBean", agenciesBean);
				logger_.debug("loadConfig : agenciesBean OK");
			}
			
			/* Type list */
			if (null == application.getAttribute("typeListBean")) {
				TypeListBean typeListBean = new TypeListBean();
				
				application.setAttribute("typeListBean", typeListBean);
				logger_.debug("loadConfig : typeBeanList OK");
			}
			
			/* DBMappingFactory (Class --> SQL and SQL --> Class) */
			DBMappingFactory.init();
			DBMappingFactory.addFile("/properties/db/EmployeeDB.xml");
			DBMappingFactory.addFile("/properties/db/AgencyDB.xml");
			DBMappingFactory.addFile("/properties/db/CustomerDB.xml");
			DBMappingFactory.addFile("/properties/db/CatalogueItemDB.xml");
			logger_.debug ("loadConfig : DBMappingFactory OK");
			
			/* FormMappingFactory (HTML form --> Class and Class --> HTML form) */
			FormMappingFactory.init();
			FormMappingFactory.addFile("/properties/form/EmployeeForm.xml");
			FormMappingFactory.addFile("/properties/form/AgencyForm.xml");
			FormMappingFactory.addFile("/properties/form/CustomerForm.xml");
			FormMappingFactory.addFile("/properties/form/CatalogueItemForm.xml");
			logger_.debug ("loadConfig : FormMappingFactory OK");
			
			/* Security */
			SecurityFactory.init();
			SecurityFactory.addFile("/properties/Security.xml");
			logger_.debug("loadConfig: SecurityAction OK");
			
		} catch (PapyrusException e) { throw new PapyrusException(e.getMessage()); }
		
		logger_.debug("loadConfig : end");
	}
}