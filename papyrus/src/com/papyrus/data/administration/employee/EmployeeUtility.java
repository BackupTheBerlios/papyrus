/*
 * Created on 4 juil. 2004
 *
 */
 
package com.papyrus.data.administration.employee;

import java.util.LinkedList;

import com.papyrus.common.Logger;
import com.papyrus.common.PapyrusException;
import com.papyrus.data.mapping.db.DBMappingFactory;
import com.papyrus.data.mapping.db.DBMappingObject;

/**
 * @author did
 *
 */
public class EmployeeUtility {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(EmployeeUtility.class.getName());
	
	/**
	 * Load all the employees wich own to the agency
	 * @param pagencyId the id of the agency
	 * @return the list of employees
	 */
	public static LinkedList loadByAgency(int pagencyId) throws PapyrusException {
		logger_.debug("loadByAgency : begin (" + pagencyId + ")");
		
		LinkedList result = null;
		DBMappingObject employeeDBMappingObject = DBMappingFactory.getInstance().getDBMappingObject(EmployeeBean.class.getName());
		
		String attributes[] = { "agencyId" } ;
		Object values[] = { new Integer(pagencyId) };
		
		try {
			result = employeeDBMappingObject.loadByWhere(attributes, values);
		} catch (PapyrusException e) { 
			logger_.error(0, "loadByAgency : ERROR (" + e.getMessage() + ")");
			throw e;
		}
		
		logger_.debug("loadByAgency : end (" + result.size() + ")");
		return result;
	}


}
