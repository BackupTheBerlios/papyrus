/*
 * Created on 13 mars 2004
 *
 * Author: did
 */

package com.papyrus.data.management.customer;

import com.papyrus.common.*;

/**
 * @author did
 *
 * Customer data bean
 */
public class CustomerBean {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(CustomerBean.class.getName());
	
	/** Code for the company civility */
	public static int STE_CIVILITY = 4;
	public static int SARL_CIVILITY = 6;
	public static int SCI_CIVILITY = 7;
	
	/** unique customer identifier */
	public int id_ = 0;
	
	/** customer code */
	public String code_ = null;
	
	/** unique agency identifier */
	public int agencyId_ = 0;

	/** civility */
	public String civility_ = null;
	
	/** civility id */
	public short civilityId_ = 0;
	
	/** company name if the customer is a company */
	public String company_ = null;
	
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
	
	public CustomerBean() {
		logger_.debug("CustomerBean : begin");	
		
		/* Do Nothing */
		
		logger_.debug("CustomerBean : end");	
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
	 * @return
	 */
	public String getCode() {
		return code_;
	}

	/**
	 * @param string
	 */
	public void setCode(String string) {
		code_ = string;
	}

	/**
	 * Display the attributes of the object instance
	 */
	public String toString() {
		return (agencyId_ + ", " +
				civilityId_ + ", " +
				lastName_ + ", " +
				firstName_ + ", " +
				address_ + " " + postalCode_ + " " + city_ + ", " +
				phone_ + ", " +
				cellPhone_);
	}
}
