/*
 * Created on 14 juil. 2004
 *
 */
package com.papyrus.data.management.order;

import com.papyrus.common.*;

import java.util.Date;

/**
 * @author did
 *
 * This bean represents an order for a customer.
 */
public class OrderBean {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(OrderBean.class.getName());
		
	/** id of the order */
	private int id_ = 0;

	/** id of the customer */
	private int customerId_ = 0;
	
	/** id of the employee who will be in charge of this order */
	private int employeeId_ = 0;

	/** id of the agency */
	private int agencyId_ = 0;
	
	/** tax (percent format) */
	private float tax_ = 0;
	
	/** observations */
	private String observations_ = null;
	
	/** complete name of the customer */
	private String customerName_ = null;
	
	/** creation date */
	private Date date_ = null;
	
	/** amount */
	private float amount_ = 0;
	
	/** number of materials */
	private int numberMaterials_ = 0;
	
	/** number of invoiced materials */
	private int numberInvoicedMaterials_ = 0;  
	
	/**
	 * @return id of the agency
	 */
	public int getAgencyId() {
		return agencyId_;
	}

	/**
	 * @return the total amount of the order
	 */
	public float getAmount() {
		return amount_;
	}

	/**
	 * @return the id of the customer
	 */
	public int getCustomerId() {
		return customerId_;
	}

	/**
	 * @return the complete name of the customer
	 */
	public String getCustomerName() {
		return customerName_;
	}

	/**
	 * @return the creation date of the order
	 */
	public Date getDate() {
		return date_;
	}

	/**
	 * @return the id of the employee
	 */
	public int getEmployeeId() {
		return employeeId_;
	}

	/**
	 * @return the id of the order
	 */
	public int getId() {
		return id_;
	}

	/**
	 * @return the number of invoiced materials
	 */
	public int getNumberInvoicedMaterials() {
		return numberInvoicedMaterials_;
	}

	/**
	 * @return the total number of materials
	 */
	public int getNumberMaterials() {
		return numberMaterials_;
	}

	/**
	 * @return the observations of the order
	 */
	public String getObservations() {
		return observations_;
	}

	/**
	 * @return the tax (percent format)
	 */
	public float getTax() {
		return tax_;
	}

	/**
	 * @param i is the id of the agency
	 */
	public void setAgencyId(int i) {
		agencyId_ = i;
	}

	/**
	 * @param f is the amount of the order
	 */
	public void setAmount(float f) {
		amount_ = f;
	}

	/**
	 * @param i is the id of the customer
	 */
	public void setCustomerId(int i) {
		customerId_ = i;
	}

	/**
	 * @param is the string the complete name of the customer
	 */
	public void setCustomerName(String string) {
		customerName_ = string;
	}

	/**
	 * @param date is the creation date of the order
	 */
	public void setDate(Date date) {
		date_ = date;
	}

	/**
	 * @param i is the id of the employee
	 */
	public void setEmployeeId(int i) {
		employeeId_ = i;
	}

	/**
	 * @param i is the id of the order 
	 */
	public void setId(int i) {
		id_ = i;
	}

	/**
	 * @param i is the number of invoiced of materials
	 */
	public void setNumberInvoicedMaterials(int i) {
		numberInvoicedMaterials_ = i;
	}

	/**
	 * @param i is the number of materials
	 */
	public void setNumberMaterials(int i) {
		numberMaterials_ = i;
	}

	/**
	 * @param string is the observations
	 */
	public void setObservations(String string) {
		observations_ = string;
	}

	/**
	 * @param f is the tax (percent format)
	 */
	public void setTax(float f) {
		tax_ = f;
	}

}
