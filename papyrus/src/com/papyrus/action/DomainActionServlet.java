/*
 * Created on 29 janv. 2004
 *
 */
package com.papyrus.action;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.Hashtable;

import com.papyrus.common.Logger;
import com.papyrus.common.PapyrusException;

import com.papyrus.data.administration.employee.EmployeeBean;
import com.papyrus.data.administration.employee.EmployeeAction;
import com.papyrus.data.administration.agency.AgencyAction;
import com.papyrus.data.management.customer.CustomerAction;
import com.papyrus.data.material.CatalogueItemAction;
import com.papyrus.data.security.*;

/**
 * For Papyrus project, controler class in the MVC model 
 * 
 * @author Didier Lafforgue
 *
 */
public class DomainActionServlet extends HttpServlet {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(DomainActionServlet.class.getName());
	
	/** Table of possible actions to perform */
	private static Hashtable domainActions_ = null;
	
	/** Default Action Name */
	private static String defaultDomainActionName_ = "login";
	
	/** 
	 * Servlet initialization 
	 */
	public void init() throws ServletException {
		logger_.debug("init : begin");
		
		/* list all possible actions */
		initActions();
		
		logger_.debug("init : end");
	}
	
	/** 
	 * create entries of the action hashtable 
	 */
	public void initActions() {
		logger_.debug("initActions : begin");
		
		if (null == domainActions_)
			domainActions_ = new Hashtable();
		
		/* add actions */
		domainActions_.put("login", new LoginAction());
		domainActions_.put("logout", new LogoutAction());
		
		domainActions_.put("employee", new EmployeeAction());
		domainActions_.put("agency", new AgencyAction());
		domainActions_.put("customer", new CustomerAction());
		domainActions_.put("catalogueItem", new CatalogueItemAction());
		
		logger_.debug("initActions : end");
	}
	
	/**
	 * @param req the HTTP request
	 * @param res the HTTP answer
	 */
	public void doGet(HttpServletRequest prequest, HttpServletResponse presponse) 
	throws ServletException, IOException {
		logger_.debug("doGet : begin");
		
		String domainName = prequest.getParameter("domain");
		String actionName = prequest.getParameter("action");
		
		// we do not accept null action, so we redirect to the login page
		if (null == domainName)
			domainName = defaultDomainActionName_;
	
		DomainAction domainAction = (DomainAction) domainActions_.get(domainName);
		// no action found
		if (null == domainAction) {
			presponse.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return ;
		}
		
		logger_.debug("doGet : domainName=" + domainName);
		logger_.debug("doGet : actionName=" + actionName);
		
		// check if the user is authenticated and authorized
		if (!isAuthenticated(prequest) && (!"authenticate".equals(domainName)) && !isAuthorized(prequest, domainName, actionName)) 
			domainAction = (DomainAction) domainActions_.get("login");
		
		// perform the action
		try { domainAction.perform(this, prequest, presponse); } 
		catch (PapyrusException e) { throw new ServletException(e.getMessage()); }
		
		logger_.debug("doGet : end");
	}
	
	/**
	 * Redirect to doGet
	 * @param req the HTTP request
	 * @param res the HTTP answer
	 */
	public void doPost(HttpServletRequest prequest, HttpServletResponse presponse) 
	throws javax.servlet.ServletException, IOException {
		logger_.debug("doPost : begin");
		
		doGet(prequest, presponse);
		
		logger_.debug("doPost : end");
	}
	
	/**
	 * Check if the current user is authenticated
	 * @return true if authentication is correct else false
	 */
	public boolean isAuthenticated(HttpServletRequest prequest) {
		logger_.debug("isAuthenticated : begin");
		
		boolean isAuthorized = false;
		HttpSession httpSession = prequest.getSession(false);
		
		if (httpSession != null) {
			String authorized = (String) httpSession.getAttribute(LoginAction.SESSION_ATTRIBUTE_NAME);
			if (authorized != null) {
				if (LoginAction.SESSION_ATTRIBUTE_VALUE.equals(authorized)) {
					isAuthorized = true ;
				}
			}
		}
	
		logger_.debug("isAuthenticated : end(" + isAuthorized + ")");
		
		return (isAuthorized);
	}

	/**
	 * Check if the current user is authorized to access to the ressource
	 * @return true if authorization is correct else false
	 */
	public boolean isAuthorized(HttpServletRequest prequest, String pdomainName, String pactionName) {
		logger_.debug("isAuthenticated : begin");
		
		boolean isAuthorized = false;
		HttpSession httpSession = prequest.getSession(false);
		
		if (httpSession != null) {
			EmployeeBean user = (EmployeeBean) httpSession.getAttribute("employeeBean");
			
			// check if the user is authorized to access to the domain and action
			if (null != user && false == SecurityFactory.getInstance().isAuthorized(pdomainName, pactionName, user.getRightsId()))
				isAuthorized = true;
		}
	
		logger_.debug("isAuthorized : end(" + isAuthorized + ")");
		return (isAuthorized);
	}	
	
}
