/*
 * Papyrus Gestion Commerciale
 * 
 * Created on Feb 28, 2004
 *
 * Author: did
  */
  
package com.papyrus.data.administration.agency;

import java.util.LinkedList;

/**
 * @author did
 *
 * The AgencyUtility class contains only SQL Queries for the AgencyBean:
 * 	- search
 * 	- search light
 * 	- add
 * 	- update
 * 	- delete
 */
public class AgencyUtility {
	
	/** Complete Search from the id */
	public static String SEARCH_SQL_QUERY = "SELECT agency.id, entity.company, entity.address, " +
																				  "entity.city, entity.cp AS \"postalCode\", entity.phone, " +
																				  "entity.address_bis AS \"addressBis\" " + 
																				  "FROM agency INNER JOIN entity ON agency.id_entity = entity.id " + 
																				  "WHERE agency.id = ?;";

	/** Query which returns all records */
	public static String SEARCH_ALL_SQL_QUERY = "SELECT agency.id, entity.company, entity.address, " +
																					  "entity.city, entity.cp AS \"postalCode\", entity.phone, " +
																					  "entity.address_bis AS \"addressBis\" " + 
																					  "FROM agency INNER JOIN entity ON agency.id_entity = entity.id; ";

	/**
	 * Retreive with the id and in a list of agencies, the agency name
	 * @param pid
	 * @param pagencyList
	 * @return the name of the company
	 */
	public static String getAgency(int pid, LinkedList pagencyList) {
		String result = null;
		
		if (null != pagencyList) {
			for (int i = 0; i < pagencyList.size(); i++) {
				AgencyBean agencyBean = (AgencyBean) pagencyList.get(i);	
				/* agency found */
				if (pid == agencyBean.getId())
					result = agencyBean.getCompany();
			}
		}
		
		return result;
	}
																					 
}
