/*
 * Eurocash Gestion Commerciale
 * 
 * Created on 28 mars 2004
 *
 * Author: did
 */
package com.papyrus.data.material;

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
public class CatalogueItemAction implements DomainAction {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(CatalogueItemAction.class.getName());

	/* (non-Javadoc)
	 * @see com.eurocash.action.Action#perform(javax.servlet.http.HttpServlet, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
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
		
		/* add catalogue item */
		if ("add".equals(action))
			url = addAction(session, parametersMap);
		
		/* update catalogue item */
		if ("update".equals(action))
			url = updateAction(session, parametersMap);		
		
		/* delete catalogue item */
		if ("delete".equals(action))
			url = deleteAction(session, parametersMap);
		
		/* forward to the next page */
		logger_.debug("perform : url = " + url);
		RequestDispatcher requestDispatcher = prequest.getRequestDispatcher(url);
		requestDispatcher.forward(prequest, presponse);
			
		logger_.debug("perform : end");
	}
	
	/**
	 * List all items of the catalogue according to the form filled by the user
	 * @param psession 
	 * @param pparametersMap contains all the attributes of the form
	 * @return the url to the display of the results
	 */
	public String listAction(HttpSession psession, HashMap pparametersMap) throws ServletException, IOException, PapyrusException {
		logger_.debug("listAction : begin");
				
		String subAction = ((String[]) pparametersMap.get("subAction"))[0];
		ItemListBean catalogueItemListBean = null;
				
		/* reset the associated form bean and setup it with the current form */
		FormMappingObject formBean = (FormMappingObject) psession.getAttribute("catalogueItemListForm");
		if (null == formBean) {
			formBean = FormMappingFactory.getInstance().getFormMappingObject("CatalogueItem", "LIST");
			/* add the formBean to the session */
			psession.setAttribute("catalogueItemListForm", formBean);
		}
		else
			formBean.reset();
			
		formBean.setData(pparametersMap);
			
		/* if data ok and if it is the init subAction (first search), then execute the searching */
		if (true == formBean.isDataOk() && "init".equals(subAction)) {
			/* list of employees */
			catalogueItemListBean = new ItemListBean(CatalogueItemBean.class);
				
			DBMappingObject catalogueItemDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(CatalogueItemBean.class.getName());
				
			catalogueItemListBean.search(formBean, catalogueItemDBMappingObject.getQuery("SEARCH"));
				
			/* go to the first results page */
			catalogueItemListBean.loadResultsFromPage(1);
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
			catalogueItemListBean = (ItemListBean) psession.getAttribute("catalogueItemListBean");
				
			if (null != catalogueItemListBean)
				catalogueItemListBean.loadResultsFromPage(pageIndex);
		}		
		/* store in the request scope the list of customers */
		psession.setAttribute("catalogueItemListBean", catalogueItemListBean);
						
		logger_.debug("listAction : end");
		return ("/material/catalogueItemList.jsp");
	}
	
	/**
	 * Add an item of the catalogue to the DB
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
		String url = "/material/catalogueItemList.jsp";
		
		/* reset the associated form bean and setup it with the current form */
		FormMappingObject formBean = (FormMappingObject) psession.getAttribute("catalogueItemAddForm");
		DBMappingObject catalogueItemDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(CatalogueItemBean.class.getName());
		
		/* init subAction */
		if ("init".equals(subAction)) {
			if (null == formBean) {
				formBean = FormMappingFactory.getInstance().getFormMappingObject("CatalogueItem", "ADD");
				psession.setAttribute("catalogueItemAddForm", formBean);
			}
			else
				formBean.reset();	
			
			url = "/material/catalogueItemAdd.jsp";
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
				/* create the catalogue item and fill data with the form */
				CatalogueItemBean catalogueItemBean = (CatalogueItemBean) formBean.createNewObject(CatalogueItemBean.class);
			
				/* insert catalogue item to the database */
				queryResult = ((Long) catalogueItemDBMappingObject.add(catalogueItemBean)).longValue();
		
				logger_.debug("addAction : catalogue = " + catalogueItemBean.toString());
			} else
				url = "/material/catalogueAdd.jsp";
		} 
		
		logger_.debug("addAction : end(" + url + ")");
		return (url);
	}


	/**
	 * Update an item of the catalogue to the DB
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
		String url = "/material/catalogueItemList.jsp";
		
		/* reset the associated form bean and setup it with the current form */
		FormMappingObject formBean = (FormMappingObject) psession.getAttribute("catalogueItemUpdateForm");
		DBMappingObject catalogueItemDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(CatalogueItemBean.class.getName());
		
		/* init subAction */
		if ("init".equals(subAction)) {
			Integer catalogueItemId = new Integer(((String[]) pparametersMap.get("id"))[0]);
			
			if (null == formBean) {
				formBean = FormMappingFactory.getInstance().getFormMappingObject("CatalogueItem", "UPDATE");
				
				psession.setAttribute("catalogueItemUpdateForm", formBean);
			}
			else
				formBean.reset();	
			
			/* load the catalogue item */
			CatalogueItemBean catalogueItem = (CatalogueItemBean) catalogueItemDBMappingObject.load(catalogueItemId);
			/* and fill it in a formBean */
			formBean.setData(catalogueItem);
				
			url = "/material/catalogueItemUpdate.jsp";
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
			
				/* create the catalogue item and fill data with the form */
				CatalogueItemBean catalogueItemBean = (CatalogueItemBean) formBean.createNewObject(CatalogueItemBean.class);
			
				/* insert catalogue item to the database */
				queryResult = ((Long) catalogueItemDBMappingObject.update(catalogueItemBean)).longValue();
			} else
				url = "/material/catalogueItemUpdate.jsp";
		}
		
		logger_.debug("updateAction : end(" + url + ")");
		return (url);
	}
	
	/**
	 * Delete an item of the catalogue to the DB
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
		String url = "/material/catalogueItemList.jsp";
		
		/* subAction : delete one customer */
		if ("one".equals(subAction)) {
			Integer catalogueItemId = new Integer(((String[]) pparametersMap.get("id"))[0]);
			
			/* check if the id is correct */
			if (null != catalogueItemId) {
				Object result = null;
				
				CatalogueItemBean catalogueItem = new CatalogueItemBean();
				catalogueItem.setId(catalogueItemId.intValue());
				DBMappingObject catalogueItemDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(CatalogueItemBean.class.getName());
		
				/* delete */
				result = catalogueItemDBMappingObject.delete(catalogueItem);
			
				logger_.debug("deleteAction : result = " + result);
			}
		}
		
		logger_.debug("deleteAction : end(" + url + ")");
		return (url);
	}
}
