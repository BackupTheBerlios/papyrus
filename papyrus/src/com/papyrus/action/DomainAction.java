/*
 * Papyrus Gestion Commerciale
 * 
 * Created on Feb 19, 2004
 *
 * Author: did
  */
  
package com.papyrus.action;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.papyrus.common.*;

/**
 * Manage different actions 
 * 
 * @author did
 *
 */
public interface DomainAction {

	/* execute the action */
	public void perform(HttpServlet pserlvet, HttpServletRequest prequest, HttpServletResponse presponse)
	throws ServletException, IOException, PapyrusException;
	
}