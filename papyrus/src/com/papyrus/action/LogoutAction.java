/*
 * Created on 29 janv. 2004
 *
 */
package com.papyrus.action;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.papyrus.common.PapyrusException;

/**
 * @author Didier Lafforgue
 *
 */
public class LogoutAction implements DomainAction {

	/**
	 * Redirect to the login page 
	 * invalidate the session
	 */ 
	public void perform(
		HttpServlet pserlvet,
		HttpServletRequest prequest,
		HttpServletResponse presponse)
		throws ServletException, IOException, PapyrusException {
		
		HttpSession session = prequest.getSession();
		session.invalidate();
		
		RequestDispatcher requestDispatcher = prequest.getRequestDispatcher ("/login.jsp");
		requestDispatcher.forward (prequest, presponse) ;        
	}
}
