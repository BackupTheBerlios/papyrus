/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 15 avr. 2004
 *
 * Author: did
 */
package com.papyrus.data.administration.agency;

import com.papyrus.common.Logger;
import com.papyrus.common.PapyrusException;
import com.papyrus.data.*;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author did
 *
 * Class storing the agencies of Papyrus.
 */
public class AgenciesBean {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(AgenciesBean.class.getName());

	/** HaspMap containing the agency */
	HashMap agenciesMap_ = null;
	
	/** LinkedList containing the agency */
	LinkedList agenciesList_ = null;

	/** Default constructor */
	public AgenciesBean() {
		logger_.debug("AgenciesBean : begin");
		
		agenciesMap_ = new HashMap();
		
		logger_.debug("AgenciesBean : end");
	}
	
	/**
	 * Load all agencies from db and store it into the local hashmap attribute
	 */
	public void load() throws PapyrusException {
		logger_.debug("load : begin");
		
		/* get the list */
		ItemBeanFactory agencyBeanFactory = new ItemBeanFactory(AgencyBean.class);
		agenciesList_ = agencyBeanFactory.loadAll(AgencyUtility.SEARCH_ALL_SQL_QUERY);
		
		/* loop the list and create an entry in the hashmap with the id for the key and the bean for value */
		for (int i = 0; i < agenciesList_.size(); i++) {
			AgencyBean agencyBean = (AgencyBean) agenciesList_.get(i);
			
			agenciesMap_.put(new Integer(agencyBean.getId()), agencyBean);
		}
		
		logger_.debug("load : end(" + agenciesList_.size()+ ")");
	}
	
	/**
	 * Get the hashmap attribute
	 * @return a hashmap containing the couple agency id and agency bean
	 */
	public HashMap getAgenciesMap() {
		return agenciesMap_;
	}

	/**
	 * @return a LinkedList containing the agency bean
	 */
	public LinkedList getAgenciesList() {
		return agenciesList_;
	}

}
