/*
 * Created on 20 juin 2004
 * 
 * Author: did
 */
 
package com.papyrus.data.material;

import com.papyrus.common.*;

/**
 * @author did
 *
 * This class represents an item in the catalogue
 * the item can be a product, a service, a discount or a garanty
 */
public class CatalogueItemBean {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(CatalogueItemBean.class.getName());
	
	/** id of the item */
	private int id_ = 0;
	
	/** Refence of the item */
	private String reference_ = null;

	/** Designation */
	private String designation_ = null;
	
	/** id of the brand */
	private short brandId_ = 0;
	
	/** name of the brand */
	private String brand_ = null;
	
	/** id of the category */
	private short categoryId_ = 0;
	
	/** name of the catagory */
	private String category_ = null;
	
	/** id of the type (product, service, discount or garanty) */
	private short typeId_ = 0;

	/** Default constructor */
	public CatalogueItemBean() { 
		logger_.debug("CatalogueItem : begin");
		
		/* Do nothing */
		
		logger_.debug("CatalogueItem : end");
	}

	/**
	 * @return the id of the brand
	 */
	public short getBrandId() {
		return brandId_;
	}
	
	/**
	 * @return the name of the brand
	 */
	public String getBrand() {
		return brand_;
	}

	/**
	 * @return the id of the category
	 */
	public short getCategoryId() {
		return categoryId_;
	}
	
	/**
	 * @return the name of the category
	 */
	public String getCategory() {
		return category_;
	}

	/**
	 * @return the designation of the item
	 */
	public String getDesignation() {
		return designation_;
	}

	/**
	 * @return the unique id of the item
	 */
	public int getId() {
		return id_;
	}

	/**
	 * @return the reference of the item
	 */
	public String getReference() {
		return reference_;
	}

	/**
	 * @return the id of the type
	 */
	public short getTypeId() {
		return typeId_;
	}

	/**
	 * @param id of the brand
	 */
	public void setBrandId(short s) {
		brandId_ = s;
	}

	/**
	 * @param name of the brand
	 */
	public void setBrand(String s) {
		brand_ = s;
	}

	/**
	 * @param id of the category
	 */
	public void setCategoryId(short s) {
		categoryId_ = s;
	}

	/**
	 * @param name of the category
	 */
	public void setCategory(String s) {
		category_ = s;
	}

	/**
	 * @param the designation of the item
	 */
	public void setDesignation(String string) {
		designation_ = string;
	}

	/**
	 * @param the unique id of the item
	 */
	public void setId(int i) {
		id_ = i;
	}

	/**
	 * @param the reference of the item
	 */
	public void setReference(String string) {
		reference_ = string;
	}

	/**
	 * @param the id of the type
	 */
	public void setTypeId(short s) {
		typeId_ = s;
	}

}
