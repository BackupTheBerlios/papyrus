/*
 * Created on 13 mars 2004
 *
 * Author: did
 */

package com.papyrus.data.administration.employee;

import com.papyrus.common.*;

import com.papyrus.data.*;

/**
 * @author did
 *
 * Employee data bean
 */
public class EmployeeBean extends ItemBean {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(EmployeeBean.class.getName());

	/** unique employee identifier */
	public int id_ = 0;
	
	/** unique agency identifier */
	public int agencyId_ = 0;
	
	/** rights */
	public short rightsId_ = 0;
	
	/** login */
	public String login_ = null;
	
	/** password */
	public String password_ = null;

	/** civility */
	public String civility_ = null;
	
	/** civility id */
	public short civilityId_ = 0;
	
	/** first name */
	public String firstName_ = null;
	
	/** last name */
	public String lastName_ = null;
	
	/** address */
	public String address_ = null;
	
	/** address_bis */
	public String addressBis_ = null;
	
	/** city */
	public String city_ = null;
	
	/** Postal Code */
	public String postalCode_ = null;
	
	/** Phone */
	public String phone_ = null;
	
	/** Cell phone */
	public String cellPhone_ = null;
	
	/** fax */
	public String fax_ = null;
	
	/** email */
	public String email_ = null;
	
	public EmployeeBean() {
		logger_.debug("EmployeeBean : begin");	
		
		/* Do Nothing */
		
		logger_.debug("EmployeeBean : end");	
	}

	/**
	 * @return Address
	 */
	public String getAddress() {
		return address_;
	}

	/**
	 * @return Address Bis
	 */
	public String getAddressBis() {
		return addressBis_;
	}

	/**
	 * @return Agency Id
	 */
	public int getAgencyId() {
		return agencyId_;
	}

	/**
	 * @return
	 */
	public String getCellPhone() {
		return cellPhone_;
	}

	/**
	 * @return City
	 */
	public String getCity() {
		return city_;
	}

	/**
	 * @return
	 */
	public String getCivility() {
		return civility_;
	}

	/**
	 * @return Email
	 */
	public String getEmail() {
		return email_;
	}

	/**
	 * @return Fax
	 */
	public String getFax() {
		return fax_;
	}

	/**
	 * @return FirstName
	 */
	public String getFirstName() {
		return firstName_;
	}

	/**
	 * @return Id
	 */
	public int getId() {
		return id_;
	}

	/**
	 * @return LastName
	 */
	public String getLastName() {
		return lastName_;
	}

	/**
	 * @return Login
	 */
	public String getLogin() {
		return login_;
	}

	/**
	 * @return Password
	 */
	public String getPassword() {
		return password_;
	}

	/**
	 * @return Phone
	 */
	public String getPhone() {
		return phone_;
	}

	/**
	 * @return PostalCode
	 */
	public String getPostalCode() {
		return postalCode_;
	}

	/**
	 * @return Rights
	 */
	public short getRightsId() {
		return rightsId_;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		address_ = address;
	}

	/**
	 * @param addressBis
	 */
	public void setAddressBis(String addressBis) {
		addressBis_ = addressBis;
	}

	/**
	 * @param agencyId
	 */
	public void setAgencyId(int agencyId) {
		agencyId_ = agencyId;
	}

	/**
	 * @param cellPhone
	 */
	public void setCellPhone(String cellPhone) {
		cellPhone_ = cellPhone;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		city_ = city;
	}

	/**
	 * @param civility
	 */
	public void setCivility(String civility) {
		civility_ = civility;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		email_ = email;
	}

	/**
	 * @param fax
	 */
	public void setFax(String fax) {
		fax_ = fax;
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		firstName_ = firstName;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		id_ = id;
	}

	/**
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		lastName_ = lastName;
	}

	/**
	 * @param login
	 */
	public void setLogin(String login) {
		login_ = login;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		password_ = password;
	}

	/**
	 * @param phone
	 */
	public void setPhone(String phone) {
		phone_ = phone;
	}

	/**
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		postalCode_ = postalCode;
	}

	/**
	 * @param rights
	 */
	public void setRightsId(short rightsId) {
		rightsId_ = rightsId;
	}
	
	/**
	 * @return
	 */
	public short getCivilityId() {
		return civilityId_;
	}

	/**
	 * @param pcivilityId
	 */
	public void setCivilityId(short pcivilityId) {
		civilityId_ = pcivilityId;
	}
	
	/**
	 * The complete name
	 * @return lastName + firstName with a space between
	 */
	public String getName() { 
		return (lastName_ + " " + firstName_);
	}
	
	public String toString() {
		return (agencyId_ + ", " +
				civilityId_ + ", " +
				lastName_ + ", " +
				firstName_ + ", " +
				address_ + " " + postalCode_ + " " + city_ + ", " +
				phone_ + ", " +
				cellPhone_ + ", " +
				login_ + ", " +
				password_ + ", " +
				rightsId_);
	}
	/*
	public void setData(ItemFormBean pitemFormBean) {
		logger_.debug("setData : begin");
		
		id_ = pitemFormBean.getIntValue("employeeId");
		agencyId_ = pitemFormBean.getIntValue("agencyId");
		lastName_ = pitemFormBean.getStringValue("lastName");
		firstName_ = pitemFormBean.getStringValue("firstName");
		address_ = pitemFormBean.getStringValue("address");
		city_ = pitemFormBean.getStringValue("city");
		postalCode_ = pitemFormBean.getStringValue("postalCode");
		phone_ = pitemFormBean.getStringValue("phone");
		cellPhone_ = pitemFormBean.getStringValue("cellPhone");
		login_ = pitemFormBean.getStringValue("login");
		password_ = pitemFormBean.getStringValue("password");
		rights_ = pitemFormBean.getIntValue("rightsId");
		
		logger_.debug("setData : end");
	}
	*/
}
