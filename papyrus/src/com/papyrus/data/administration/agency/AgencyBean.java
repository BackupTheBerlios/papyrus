/*
 * Papyrus Gestion Commerciale
 * 
 * Created on Feb 26, 2004
 *
 * Author: did
  */
  
package com.papyrus.data.administration.agency;

import java.util.LinkedList;

import com.papyrus.common.*;
import com.papyrus.data.*;

/**
 * Agency data bean
 * 
 * @author did
 */

public class AgencyBean extends ItemBean {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(AgencyBean.class.getName());

	/** id of this agency */
	public int id_ = 0; 
	
 	/** id of the employee leader */
	public int employeeLeaderId_ = 0;
	
	/** Name of the agency */
	public String company_ = null;
	
	/** Address */
	public String address_ = null;
	
	/** City */
	public String city_ = null;
	
	/** Postal Code */
	public String postalCode_ = null;
	
	/** Phone */
	public String phone_ = null;
	
	/** Fax */
	public String fax_ = null;
	
	/** Email */
	public String email_ = null;

	/** Number of employees */
	public int nbEmployees_ = 0;
	
	/** Customer code */
	public String customerCode_ = null;
	
	/** Bill code */
	public String billCode_ = null;
	
	/** Agency code */
	public String agencyCode_ = null;
	
	/** Is the parent company */
	public boolean  parentCompany_ = false;;
	
	/** List of employees of this agency */
	private LinkedList employeesList_ = null; 
	
	/* default constructor */
	public AgencyBean() {
		logger_.debug("AgencyBean : begin(default constructor)");

		logger_.debug("AgencyBean : end");	
	}
		
	/* Getter / Setter */
	public String getAgencyCode() { return agencyCode_; }
	
	public String getAddress() { return address_; }
	
	public String getBillCode() { return billCode_; }
	
	public String getCity() { return city_; }
	
	public String getCustomerCode() { return customerCode_; }
	
	public String getEmail() { return email_; }
	
	public String getFax() { return fax_;	}
	
	public int getId() { return id_; }
	
	public int getEmployeeLeaderId() { return employeeLeaderId_; }
	
	public String getCompany() { return company_; }
	
	public int getNbEmployees() { return nbEmployees_; }
	
	public String getPhone() { return phone_; }
	
	public String getPostalCode() { return postalCode_; }
	
	public boolean getParentCompany() { return parentCompany_;	}
	
	public boolean isParentCompany() { return parentCompany_;	}
	
	/**
	 * @param string
	 */
	public void setAddress(String string) {
		address_ = string;
	}

	/**
	 * @param string
	 */
	public void setAgencyCode(String string) {
		agencyCode_ = string;
	}

	/**
	 * @param string
	 */
	public void setBillCode(String string) {
		billCode_ = string;
	}

	/**
	 * @param string
	 */
	public void setCity(String string) {
		city_ = string;
	}

	/**
	 * @param string
	 */
	public void setCompany(String string) {
		company_ = string;
	}

	/**
	 * @param string
	 */
	public void setCustomerCode(String string) {
		customerCode_ = string;
	}

	/**
	 * @param string
	 */
	public void setEmail(String string) {
		email_ = string;
	}

	/**
	 * @param i
	 */
	public void setEmployeeLeaderId(int i) {
		employeeLeaderId_ = i;
	}

	/**
	 * @param string
	 */
	public void setFax(String string) {
		fax_ = string;
	}

	/**
	 * @param i
	 */
	public void setId(int i) {
		id_ = i;
	}

	/**
	 * @param i
	 */
	public void setNbEmployees(int i) {
		nbEmployees_ = i;
	}

	/**
	 * @param b
	 */
	public void setParentCompany(boolean b) {
		parentCompany_ = b;
	}

	/**
	 * @param string
	 */
	public void setPhone(String string) {
		phone_ = string;
	}

	/**
	 * @param string
	 */
	public void setPostalCode(String string) {
		postalCode_ = string;
	}

	/**
	 * @return
	 */
	public LinkedList getEmployeesList() {
		return employeesList_;
	}

	/**
	 * @param list
	 */
	public void setEmployeesList(LinkedList list) {
		employeesList_ = list;
	}

}
