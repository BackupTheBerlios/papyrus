/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 28 mars 2004
 *
 * Author: did
 */
package com.papyrus.data.management.customer;

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
import com.papyrus.data.administration.employee.EmployeeBean;
import com.papyrus.data.mapping.db.DBMappingFactory;
import com.papyrus.data.mapping.db.DBMappingObject;
import com.papyrus.data.mapping.form.*;
import com.papyrus.data.ItemListBean;
import com.papyrus.common.PapyrusException;
import com.papyrus.common.Logger;



/**
 * @author did
 *
 */
public class CustomerAction implements DomainAction {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(CustomerAction.class.getName());

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
		
		/* add customer */
		if ("add".equals(action))
			url = addAction(session, parametersMap);
		
		/* update customer */
		if ("update".equals(action))
			url = updateAction(session, parametersMap);		
		
		/* delete customer(s) */
		if ("delete".equals(action))
			url = deleteAction(session, parametersMap);
		
		/* forward to the next page */
		logger_.debug("perform : url = " + url);
		RequestDispatcher requestDispatcher = prequest.getRequestDispatcher(url);
		requestDispatcher.forward(prequest, presponse);
			
		logger_.debug("perform : end");
	}
	
	/**
	 * List all customers according to the form filled by the user
	 * @param psession 
	 * @param pparametersMap contains all the attributes of the form
	 * @return the url to the display of the results
	 */
	public String listAction(HttpSession psession, HashMap pparametersMap) throws ServletException, IOException, PapyrusException {
		logger_.debug("listAction : begin");
				
		String subAction = ((String[]) pparametersMap.get("subAction"))[0];
		ItemListBean customerListBean = null;
				
		/* reset the associated form bean and setup it with the current form */
		FormMappingObject formBean = (FormMappingObject) psession.getAttribute("customerListForm");
		if (null == formBean) {
			formBean = FormMappingFactory.getInstance().getFormMappingObject("Customer", "LIST");
			/* add the formBean to the session */
			psession.setAttribute("customerListForm", formBean);
		}
		else
			formBean.reset();
			
		formBean.setData(pparametersMap);
			
		/* if data ok and if it is the init subAction (first search), then execute the searching */
		if (true == formBean.isDataOk() && "init".equals(subAction)) {
			/* list of employees */
			customerListBean = new ItemListBean(CustomerBean.class);
				
			DBMappingObject customerDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(CustomerBean.class.getName());
				
			customerListBean.search(formBean, customerDBMappingObject.getQuery("SEARCH"));
				
			/* go to the first results page */
			customerListBean.loadResultsFromPage(1);
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
			customerListBean = (ItemListBean) psession.getAttribute("customerListBean");
				
			if (null != customerListBean)
				customerListBean.loadResultsFromPage(pageIndex);
		}		
		/* store in the request scope the list of customers */
		psession.setAttribute("customerListBean", customerListBean);
						
		logger_.debug("listAction : end");
		return ("/management/customerList.jsp");
	}
	
	/**
	 * Add a customer to the DB
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
		String url = "/management/customerList.jsp";
		
		/* reset the associated form bean and setup it with the current form */
		FormMappingObject formBean = (FormMappingObject) psession.getAttribute("customerAddForm");
		DBMappingObject customerDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(CustomerBean.class.getName());
		
		/* init subAction */
		if ("init".equals(subAction)) {
			if (null == formBean) {
				formBean = FormMappingFactory.getInstance().getFormMappingObject("Customer", "ADD");
				psession.setAttribute("customerAddForm", formBean);
			}
			else
				formBean.reset();	
			
			url = "/management/customerAdd.jsp";
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
				/* create the customer and fill data with the form */
				CustomerBean customerBean = (CustomerBean) formBean.createNewObject(CustomerBean.class);
			
				/* insert customer to the database */
				queryResult = ((Long) customerDBMappingObject.add(customerBean)).longValue();
		
				logger_.debug("addAction : customer = " + customerBean.toString());
			} else
				url = "/administration/employeeAdd.jsp";
		} 
		
		logger_.debug("addAction : end(" + url + ")");
		return (url);
	}


	/**
	 * Update a customer to the DB
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
		String url = "/management/customerList.jsp";
		
		/* reset the associated form bean and setup it with the current form */
		FormMappingObject formBean = (FormMappingObject) psession.getAttribute("customerUpdateForm");
		DBMappingObject customerDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(CustomerBean.class.getName());
		
		/* init subAction */
		if ("init".equals(subAction)) {
			Integer customerId = new Integer(((String[]) pparametersMap.get("id"))[0]);
			
			if (null == formBean) {
				formBean = FormMappingFactory.getInstance().getFormMappingObject("Customer", "UPDATE");
				
				psession.setAttribute("customerUpdateForm", formBean);
			}
			else
				formBean.reset();	
			
			/* load the customer */
			CustomerBean customer = (CustomerBean) customerDBMappingObject.load(customerId);
			/* and fill it in a formBean */
			formBean.setData(customer);
				
			url = "/management/customerUpdate.jsp";
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
			
				/* create the customer and fill data with the form */
				CustomerBean customerBean = (CustomerBean) formBean.createNewObject(CustomerBean.class);
			
				/* insert customer to the database */
				queryResult = ((Long) customerDBMappingObject.update(customerBean)).longValue();
			} else
				url = "/management/customerUpdate.jsp";
		}
		
		logger_.debug("updateAction : end(" + url + ")");
		return (url);
	}
	
	/**
	 * Delete a customer in the DB
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
		String url = "/management/customerList.jsp";
		
		/* subAction : delete one customer */
		if ("one".equals(subAction)) {
			Integer customerId = new Integer(((String[]) pparametersMap.get("id"))[0]);
			
			/* check if the id is correct */
			if (null != customerId) {
				Object result = null;
				
				CustomerBean employee = new CustomerBean();
				employee.setId(customerId.intValue());
				DBMappingObject customerDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(CustomerBean.class.getName());
		
				/* delete */
				result = customerDBMappingObject.delete(employee);
			
				logger_.debug("deleteAction : result = " + result);
			}
		}
		
		logger_.debug("deleteAction : end(" + url + ")");
		return (url);
	}
}
