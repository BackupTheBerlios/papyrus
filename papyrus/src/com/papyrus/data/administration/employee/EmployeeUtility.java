/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 13 mars 2004
 *
 * Author: did
 */
package com.papyrus.data.administration.employee;

import java.util.LinkedList;

import com.papyrus.common.*;
import com.papyrus.data.form.*;

/**
 * @author did
 *
 * The EmployeeUtility class contains only SQL Queries for the EmployeeBean:
 * 	- search
 * 	- search light
 * 	- add
 * 	- update
 * 	- delete
 */
public class EmployeeUtility {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(EmployeeUtility.class.getName());


	/** Default search query */
	public static String SQL_DEFAULT_SEARCH_QUERY = "SELECT * FROM view_employee <CONDITIONS> " + 
													"ORDER BY \"agencyId\", \"lastName\"";

	/** Complete Search */
	public static String SQL_SEARCH_QUERY = "SELECT * FROM view_employee WHERE employee.id = ?;";

	/** Add query */
	public static String SQL_ADD_QUERY = "{ ? = call \"addEmployee\"()";
	
	/**
	 * Construct a sql query from a form and its xml descriptor
	 * @param form
	 * @return a complete sql query
	 */
	public static String constructSearchQuery(FormBean form) {
		logger_.debug("constructSearchQuery : begin");
		
		StringBuffer query = new StringBuffer();
		
		/* get an instance of a XMLFormFactory */
		XMLFormFactory formFactory = XMLFormFactory.getInstance();
		
		/* get the SQL attributes list */
		LinkedList attributesList = formFactory.getAllFormAttributes("Employee", "LIST", "sql");	
		
		for (int i = 0; i < attributesList.size(); i++) {
			Attribute attribute = (Attribute) attributesList.get(i);
			
			/* to be accepted in the sql query, the attribute must be not null and different from the ignored value */
			/* in the example of a agency search, if id_agency == 0 == ignoredValue, then no criter */
			if (null != form.getValue(attribute.getName())) {
				if (null == attribute.getIgnoredValue() || !attribute.getIgnoredValue().equals(form.getValue(attribute.getName()).toString())) {
					/* for the search, in the case of a string parameter, we use ~* operator */
					if ("String".equals(attribute.getType()))
						query.append("AND (\"" + attribute.getFormName() + "\" ~* ?) ");			
					else
						query.append("AND (\"" + attribute.getFormName() + "\" = ?) ");
				}
			}
		}
		
		if (query.length() != 0) {
			/* remove the AND keyword from the beginning */
			query.delete(0, 4);
			/* insert the WHERE clause */
			query.insert(0, "WHERE ");
		}
		
		logger_.debug("constructSearchQuery : end(" + query + ")");
		
		return(Utilities.replaceString(SQL_DEFAULT_SEARCH_QUERY, "<CONDITIONS>", query.toString()));
	}
	
	
}
