/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 28 mars 2004
 *
 * Author: did
 */
package com.papyrus.data.administration.agency;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.papyrus.action.DomainAction;
import com.papyrus.data.mapping.db.DBMappingFactory;
import com.papyrus.data.mapping.db.DBMappingObject;
import com.papyrus.data.mapping.form.*;
//import com.papyrus.data.form.FormBean;
import com.papyrus.data.ItemListBean;
import com.papyrus.common.PapyrusException;
import com.papyrus.common.Logger;

import com.papyrus.data.administration.employee.*;


/**
 * @author did
 *
 */
public class AgencyAction implements DomainAction {
	
	/** Error type while inserting or updating employee */
	public final static int EMPLOYEE_DUPLICATED_LOGIN = -1; 
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(AgencyAction.class.getName());

	/* (non-Javadoc)
	 * @see com.Papyrus.action.Action#perform(javax.servlet.http.HttpServlet, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void perform(
		HttpServlet pserlvet,
		HttpServletRequest prequest,
		HttpServletResponse presponse)
		throws ServletException, IOException, PapyrusException {
		logger_.debug("perform : begin");
		
		/* get the profil of the user */
		HttpSession session = prequest.getSession();
		EmployeeBean employeeBean = (EmployeeBean) session.getAttribute("employeeBean");
		
		String action = prequest.getParameter("action");
		String subAction = prequest.getParameter("subAction");
		String url = "/global.jsp";
		
		/* no subAction */
		if (null == action)
			return;
		
		logger_.debug("perform : action = " + action + ", subAction = " + subAction);
		
		/* have a correct agencyId is essential */
		/** TODO: put all the agency tests in the DomainActionServlet */
		HashMap parametersMap = new HashMap((Map) prequest.getParameterMap());
		int agencyId = (null != prequest.getParameter("agencyId")) ? Integer.parseInt(prequest.getParameter("agencyId"), 10) : employeeBean.getAgencyId();
		parametersMap.put("agencyId", Integer.toString(agencyId));
		logger_.debug("perform : agencyId = " + parametersMap.get("agencyId"));
		logger_.debug("perform : user's agencyId = " + employeeBean.getAgencyId() + " (" + agencyId + ")");
		
		/* list action */
		if ("list".equals(action)) 
			url = listAction(session, parametersMap);
		
		/* add agency */
		if ("add".equals(action))
			url = addAction(session, parametersMap);
		
		/* update agency */
		if ("update".equals(action))
			url = updateAction(session, parametersMap);		
		
		/* delete employee(s) */
		if ("delete".equals(action))
			url = deleteAction(session, parametersMap);
		
		/* forward to the next page */
		logger_.debug("perform : url = " + url);
		RequestDispatcher requestDispatcher = prequest.getRequestDispatcher(url);
		requestDispatcher.forward(prequest, presponse);
			
		logger_.debug("perform : end");
	}
	
	/**
	 * List all agencies according to the form filled by the user
	 * @param psession 
	 * @param pparametersMap contains all the attributes of the form
	 * @return the url to the display of the results
	 */
	public String listAction(HttpSession psession, HashMap pparametersMap) throws ServletException, IOException, PapyrusException {
		logger_.debug("listAction : begin");
				
		String subAction = ((String[]) pparametersMap.get("subAction"))[0];
		ItemListBean agencyListBean = null;
				
		/* reset the associated form bean and setup it with the current form */
		FormMappingObject formBean = (FormMappingObject) psession.getAttribute("agencyListForm");
		if (null == formBean)
			formBean = FormMappingFactory.getInstance().getFormMappingObject("Agency", "LIST");
		else
			formBean.reset();
			
//		formBean.init("Employee", "LIST");
		formBean.setData(pparametersMap);
			
		/* if data ok and if it is the init subAction (first search), then execute the searching */
		if (true == formBean.isDataOk() && "init".equals(subAction)) {
			/* list of employees */
			agencyListBean = new ItemListBean(AgencyBean.class);
				
//			employeeListBean.search(EmployeeUtility.constructSearchQuery(formBean), formBean.toLinkedList());
			DBMappingObject agencyDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(AgencyBean.class.getName());
				
			logger_.debug("listAction : agencyDBMappingObject = " + agencyDBMappingObject);
				
			agencyListBean.search(formBean, agencyDBMappingObject.getQuery("SEARCH"));
				
			/* go to the first results page */
			agencyListBean.loadResultsFromPage(1);
		} else 
			if (false == formBean.isDataOk())
				logger_.debug("listAction : data are not correct (" + formBean.getErrorMessage() + ") !!!");
			
		/* subAction: go to the next page or go to the previous one */
		if ("goto".equals(subAction)) {
			logger_.debug("listAction : pageIndex = " + ((String[]) pparametersMap.get("pageIndex"))[0]);
			int pageIndex = 1;
				
			try { pageIndex = Integer.parseInt(((String[]) pparametersMap.get("pageIndex"))[0]); }
			catch (NumberFormatException e) { }
				
			/* get the employeeListBean attribute from the current session */
			agencyListBean = (ItemListBean) psession.getAttribute("agencyListBean");
				
			if (null != agencyListBean)
				agencyListBean.loadResultsFromPage(pageIndex);
		}
			
		/* store the form in order to redisplay it */
		psession.setAttribute("agencyListForm", formBean);
			
		/* store in the request scope the list of employees */
		psession.setAttribute("agencyListBean", agencyListBean);
		
		logger_.debug("listAction : end");
		
		return ("/administration/agencyList.jsp");
	}
	
	/**
	 * Add an agency to the DB
	 * @param psession
	 * @param pparametersMap contains all the attributes of the form
	 * @return the target url after processing
	 * @throws ServletException
	 * @throws IOException
	 * @throws PapyrusException
	 */
	public String addAction(HttpSession psession, HashMap pparametersMap) throws ServletException, IOException, PapyrusException {
		logger_.debug("addAction : begin");
		
		String subAction = ((String[]) pparametersMap.get("subAction"))[0];
		String url = "/administration/employeeList.jsp";
		
		/* reset the associated form bean and setup it with the current form */
		FormMappingObject formBean = (FormMappingObject) psession.getAttribute("employeeAddForm");
		DBMappingObject employeeDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(EmployeeBean.class.getName());
		
		/* init subAction */
		if ("init".equals(subAction)) {
			if (null == formBean) {
				formBean = FormMappingFactory.getInstance().getFormMappingObject("Employee", "ADD");
				psession.setAttribute("employeeAddForm", formBean);
			}
			else
				formBean.reset();	
			
			url = "/administration/employeeAdd.jsp";
		}
		
		/* insert subAction */
		if ("ok".equals(subAction)) {
			long queryResult;
			
			/* reset the associated form bean and setup it with the current form */
			if (null != formBean)
				formBean.reset();
			
			formBean.setData(pparametersMap);
			logger_.debug("addAction: formBean error ??? = " + formBean.getErrorMessage());
			
			if (formBean.isDataOk()) {
				/* create the employee and fill data with the form */
				EmployeeBean employeeBean = (EmployeeBean) formBean.createNewObject(EmployeeBean.class);
			
				/* insert employee to the database */
				queryResult = ((Long) employeeDBMappingObject.add(employeeBean)).longValue();
			
				if (EMPLOYEE_DUPLICATED_LOGIN == queryResult) {
					formBean.setErrorFields("login", "login déjà utilisé");
					url = "/administration/employeeAdd.jsp";
				} else
					url = "/DomainActionServlet?domain=employee&action=list&subAction=init";
				
				logger_.debug("addAction : employee = " + employeeBean.toString());
			} else
				url = "/administration/employeeAdd.jsp";
		} 
		
		logger_.debug("addAction : end(" + url + ")");
		return (url);
	}


	/**
	 * Update an employee to the DB
	 * @param psession
	 * @param pparametersMap contains all the attributes of the form
	 * @return the target url after processing
	 * @throws ServletException
	 * @throws IOException
	 * @throws PapyrusException
	 */
	public String updateAction(HttpSession psession, HashMap pparametersMap) throws ServletException, IOException, PapyrusException {
		logger_.debug("updateAction : begin");
		
		String subAction = ((String[]) pparametersMap.get("subAction"))[0];
		String url = "/administration/employeeList.jsp";
		
		/* reset the associated form bean and setup it with the current form */
		FormMappingObject formBean = (FormMappingObject) psession.getAttribute("employeeUpdateForm");
		DBMappingObject employeeDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(EmployeeBean.class.getName());
		
		/* init subAction */
		if ("init".equals(subAction)) {
			Integer employeeId = new Integer(((String[]) pparametersMap.get("id"))[0]);
			
			if (null == formBean) {
				formBean = FormMappingFactory.getInstance().getFormMappingObject("Employee", "UPDATE");
				
				psession.setAttribute("employeeUpdateForm", formBean);
			}
			else
				formBean.reset();	
			
			/* load the employee */
			EmployeeBean employee = (EmployeeBean) employeeDBMappingObject.load(employeeId);
			/* and fill it in a formBean */
			formBean.setData(employee);
				
			url = "/administration/employeeUpdate.jsp";
		}
		
		/* insert subAction */
		if ("ok".equals(subAction)) {
			long queryResult;
			
			/* reset the associated form bean and setup it with the current form */
			if (null != formBean)
				formBean.reset();
			
			formBean.setData(pparametersMap);
			
			if (formBean.isDataOk()) {
				logger_.debug("updateAction: formBean error ??? = " + formBean.getErrorMessage());
			
				/* create the employee and fill data with the form */
				EmployeeBean employeeBean = (EmployeeBean) formBean.createNewObject(EmployeeBean.class);
			
				/* insert employee to the database */
				queryResult = ((Long) employeeDBMappingObject.update(employeeBean)).longValue();
			
				if (EMPLOYEE_DUPLICATED_LOGIN == queryResult) {
					formBean.setErrorFields("login", "login déjà utilisé");
					url = "/administration/employeeUpdate.jsp";
				} else
					url = "/DomainActionServlet?domain=employee&action=list&subAction=init";
					
				logger_.debug("updateAction : employee = " + employeeBean.toString());
			} else
				url = "/administration/employeeUpdate.jsp";
		}
		
		logger_.debug("updateAction : end(" + url + ")");
		return (url);
	}
	
	/**
	 * Delete an employee in the DB
	 * @param psession
	 * @param pparametersMap contains all the attributes of the form
	 * @return the target url after processing
	 * @throws ServletException
	 * @throws IOException
	 * @throws PapyrusException
	 */
	public String deleteAction(HttpSession psession, HashMap pparametersMap) throws ServletException, IOException, PapyrusException {
		logger_.debug("deleteAction : begin");
		
		String subAction = ((String[]) pparametersMap.get("subAction"))[0];
		String url = "/DomainActionServlet?domain=employee&action=list&subAction=init";
		
		/* subAction : delete one employee */
		if ("one".equals(subAction)) {
			Integer employeeId = new Integer(((String[]) pparametersMap.get("id"))[0]);
			
			/* check if the id is correct */
			if (null != employeeId) {
				Object result = null;
				
				EmployeeBean employee = new EmployeeBean();
				employee.setId(employeeId.intValue());
				DBMappingObject employeeDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(EmployeeBean.class.getName());
		
				/* delete */
				result = employeeDBMappingObject.delete(employee);
			
				logger_.debug("deleteAction : result = " + result);
			}
		}
		
		logger_.debug("deleteAction : end(" + url + ")");
		return (url);
	}
}
