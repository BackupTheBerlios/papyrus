/*
 * Created on 29 janv. 2004
 *
 */
package com.papyrus.action;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.papyrus.data.administration.employee.EmployeeBean;
import com.papyrus.data.mapping.db.DBMappingFactory;
import com.papyrus.data.mapping.db.DBMappingObject;
import com.papyrus.common.*;
;

/**
 * Login Action 
 * 
 *  @author Didier Lafforgue
 *
 */
public class LoginAction implements DomainAction {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(LoginAction.class.getName());

	/** maximum inactive time for 2 requests of a session (in seconds) */
	private final static int MAX_INACTIVE_TIME = 600;
	
	/** name of the session attribute */
	public final static String SESSION_ATTRIBUTE_NAME = "isAuthorized";
	
	/** value of the session attribute */
	public final static String SESSION_ATTRIBUTE_VALUE = "YES";	

    /** SQL Query for authentication */
    public final static String SQL_QUERY_AUTHENTICATE = "SELECT id FROM employee WHERE login=? and password=?;";

	/** Error message */
	public final static String ERROR_LOGIN_FAILED = "Echec de l'authentification";

	public LoginAction() {
		logger_.debug("init : begin");
		
		logger_.debug("init : end");
	}
	
	public void perform(HttpServlet pserlvet, HttpServletRequest prequest, HttpServletResponse presponse)
	throws ServletException, IOException, PapyrusException {
		
		logger_.debug("perform : begin");
	
		/* variable needed for SQL */ 
		Connection connection = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;
	
		/* no more valid session */
		String url = "/global.jsp";
		HttpSession session;
		session = prequest.getSession();
		session.invalidate();
		
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			pstmt = connection.prepareStatement(SQL_QUERY_AUTHENTICATE);
			
			pstmt.setString(1, prequest.getParameter("login"));
			pstmt.setString(2, prequest.getParameter("password"));
			
			/* execute the query */
			rs = pstmt.executeQuery();
			
			/* check if the user exists */
			if (rs.next()) {
				/* OK */
				logger_.warn(0, "Successful Authentication");
				
				/* load this employee */
				DBMappingObject employeeDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(EmployeeBean.class.getName());
				
				logger_.debug("process : employeeDBMappingObject = " + employeeDBMappingObject);
		
				EmployeeBean employeeBean = (EmployeeBean) employeeDBMappingObject.load(new Integer(rs.getInt("id")));
				
				logger_.debug("perform : load User OK !!! (" + employeeBean + ")");
				
				/**
				 * TODO: to modify
				 */
				url = "/global.jsp";
				
				/* add "isAuthorized" and the employee in session */
				session = prequest.getSession(true); 
				if (null != session ){
					session.setAttribute(SESSION_ATTRIBUTE_NAME, SESSION_ATTRIBUTE_VALUE);
					session.setMaxInactiveInterval(MAX_INACTIVE_TIME);
				
					logger_.debug("perform : add employeeBean to the current session scope");
					session.setAttribute("employeeBean", employeeBean);
				}
			} else {
				/* KO: authentication failed */
				logger_.warn(0, "Unsucessful Authentication: " + prequest.getParameter("login") + "/" + prequest.getParameter("password"));
				
				url = "/login.jsp";
				
				/* remove session */
				if( null != prequest.getSession(false) ){
					prequest.getSession(false).invalidate();
				}  
				
				/* Create errorBean that will be passed to login.jsp */
				ErrorBean errorBean = new ErrorBean();
				errorBean.setCode(ERROR_LOGIN_FAILED);
				prequest.setAttribute ("errorBean", errorBean);
			}
			
			/* forward to the adequat page */
			RequestDispatcher requestDispatcher = prequest.getRequestDispatcher (url);	
			requestDispatcher.forward(prequest, presponse) ;
			
			logger_.debug("perform : end(" + url + ")");    	

		} catch (Exception e) {
			logger_.debug("perform : ERROR (" + e.getMessage() + ")");
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					rs.close();
					connection.close();
				}
			} catch (Exception e) { }
		}		
	}
}
