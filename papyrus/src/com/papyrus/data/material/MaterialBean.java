/*
 * Created on 14 juil. 2004
 *
 */
package com.papyrus.data.material;

import com.papyrus.common.*;

/**
 * @author did
 *
 * A material is a physical entity representing a product.
 */
public class MaterialBean {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(MaterialBean.class.getName());
	
	/** id of the material */
	private int id_ = 0;

	/** status of the material: in stock, reserved, invoiced, ordered, ...etc */ 
	private short statusId_ = 0;
	
	/** id of the associated product */
	private int productId_ = 0;

	/** id of the agency where the material is present */
	private int agencyId_ = 0;
	
	/** id of the order */
	private int orderId_ = 0;
	
	/** id of the invoice */
	private int invoiceId_ = 0;
	
	/** id of the credit */
	private int creditId_ = 0;
	
	/** sell price */
	private float sellPrice_ = 0;
	
	/** purchasing price */
	private float purchasingPrice_ = 0;

	/** name of the customer (used only for the list) */
	private String customerName_ = null;
	
	/** reference of the product (used only for the list) */
	private String reference_ = null;
	
	/** designation of the product (used only for the list) */
	private String designation_ = null;
	
	/** code of the order (used only for the list) */
	private String orderCode_ = null;
	
	/** code of the invoice (used only for the list) */
	private String invoiceCode_ = null;
	
	
	/**
	 * @return the id of the agency
	 */
	public int getAgencyId() {
		return agencyId_;
	}

	/**
	 * @return the id of the credit (0 if there is no associated credit)
	 */
	public int getCreditId() {
		return creditId_;
	}

	/**
	 * @return the complete name (civility + first name + last name) of the customer
	 */
	public String getCustomerName() {
		return customerName_;
	}

	/**
	 * @return the id of this material
	 */
	public int getId() {
		return id_;
	}

	/**
	 * @return the code of the invoice
	 */
	public String getInvoiceCode() {
		return invoiceCode_;
	}

	/**
	 * @return the id of the invoice
	 */
	public int getInvoiceId() {
		return invoiceId_;
	}

	/**
	 * @return the code of the order
	 */
	public String getOrderCode() {
		return orderCode_;
	}

	/**
	 * @return the id of the order
	 */
	public int getOrderId() {
		return orderId_;
	}

	/**
	 * @return the id of the associated product
	 */
	public int getProductId() {
		return productId_;
	}

	/**
	 * @return the purchasing price
	 */
	public float getPurchasingPrice() {
		return purchasingPrice_;
	}

	/**
	 * @return the reference of the associated product
	 */
	public String getReference() {
		return reference_;
	}

	/**
	 * @return the sell price 
	 */
	public float getSellPrice() {
		return sellPrice_;
	}

	/**
	 * @return the id of the status
	 */
	public short getStatusId() {
		return statusId_;
	}

	/**
	 * @param i is the id of the agency
	 */
	public void setAgencyId(int i) {
		agencyId_ = i;
	}

	/**
	 * @param i is the id of the credit
	 */
	public void setCreditId(int i) {
		creditId_ = i;
	}

	/**
	 * @param string is the complete name of the customer
	 */
	public void setCustomerName(String string) {
		customerName_ = string;
	}

	/**
	 * @param i is the unique id of this material
	 */
	public void setId(int i) {
		id_ = i;
	}

	/**
	 * @param string is the code of the invoice
	 */
	public void setInvoiceCode(String string) {
		invoiceCode_ = string;
	}

	/**
	 * @param i is the id of the invoice
	 */
	public void setInvoiceId(int i) {
		invoiceId_ = i;
	}

	/**
	 * @param string is the code of the order
	 */
	public void setOrderCode(String string) {
		orderCode_ = string;
	}

	/**
	 * @param i is the id of the order
	 */
	public void setOrderId(int i) {
		orderId_ = i;
	}

	/**
	 * @param i is the id of the associated product
	 */
	public void setProductId(int i) {
		productId_ = i;
	}

	/**
	 * @param f is the purchasing price
	 */
	public void setPurchasingPrice(float f) {
		purchasingPrice_ = f;
	}

	/**
	 * @param string is the reference of the associated product
	 */
	public void setReference(String string) {
		reference_ = string;
	}

	/**
	 * @param f is the sell price
	 */
	public void setSellPrice(float f) {
		sellPrice_ = f;
	}

	/**
	 * @param s is the id of the status
	 */
	public void setStatusId(short s) {
		statusId_ = s;
	}

	/**
	 * @return the designation of the associated product
	 */
	public String getDesignation() {
		return designation_;
	}

	/**
	 * @param string is the designation of the associated product
	 */
	public void setDesignation(String string) {
		designation_ = string;
	}

}
