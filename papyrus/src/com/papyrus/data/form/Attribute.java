/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 29 mars 2004
 *
 * Author: did
 */
package com.papyrus.data.form;

import org.w3c.dom.*;
import com.papyrus.common.Logger;

/**
 * @author did
 *
 */
public class Attribute {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(Attribute.class.getName());

	/** Name of the attribute (for error message for example) */
	private String name_ = null;

	/** Name of the attribute in the form */
	private String formName_ = null;
	
	/** Type: String, Integer, Float, boolean */
	protected String type_ = null;
	
	/** Ignored Value (used for sql search, in order to have for example all the agencies with a 0 value) */
	private String ignoredValue_ = null;

	/** Required attribute */
	private boolean required_ = false; 

	/** 
	 * Constructor
	 * @param pname
	 * @param ptype
	 */
	public Attribute(String pname, String pformName, String ptype, String pignoredValue, boolean prequired) {
		logger_.debug("Attribute : begin(" + pname + ", " + pformName + ", " + ptype + ", " + pignoredValue + ", " + prequired + ")");
		
		this.name_ = pname;
		this.formName_ = pformName;
		this.type_ = ptype;
		this.ignoredValue_ = pignoredValue;
		this.required_ = prequired;
		
		logger_.debug("Attribute : end");
	}

	/**
	 * Construct a Attribute from a XML node with the basic properties.
	 * @param pattributesMap all the attributes of the XML node
	 * @param pformName the name of the attribute in the "form"
	 */
	public Attribute(NamedNodeMap pattributesMap, String pformName) {
		logger_.debug("Attribute : begin(" + pformName + ")");
		
		if (null != pattributesMap) {
			type_ = ((Node) pattributesMap.getNamedItem("type")).getNodeValue();
			name_ = ((Node) pattributesMap.getNamedItem("name")).getNodeValue();
			formName_ = pformName;
			ignoredValue_ = null != ((Node) pattributesMap.getNamedItem("ignoredValue")) ? ((Node) pattributesMap.getNamedItem("ignoredValue")).getNodeValue() : null;
			if ("true".equals(((Node) pattributesMap.getNamedItem("required")).getNodeValue()))
				required_ = true;
		}
		
		logger_.debug("Attribute : end");	
	}

	public Object getValue(String pstringValue) {
		logger_.debug("getValue : begin (value = " + pstringValue + ")");
		
		Object result = null;
		
		/* string */
		if ("String".equals(type_))
			result = pstringValue;
			
		/* integer */
		if ("Integer".equals(type_))
			try { result = new Integer(pstringValue); } 
			catch (NumberFormatException e) {
				logger_.debug("getValue : '" + pstringValue + "' is not an integer");
			}	
		
		/* float */
		if ("Float".equals(type_)) {
			try { result = new Float(pstringValue); } 
			catch (NumberFormatException e) {
				logger_.debug("getValue : '" + pstringValue + "' is not a float");
			}
		}	
		
		/* float */
		if ("Boolean".equals(type_)) {
			try { result = new Boolean(pstringValue); } 
			catch (NumberFormatException e) {
				logger_.debug("getValue : '" + pstringValue + "' is not a boolean");
			}
		}	
		
		logger_.debug("getValue : end (" + result + ")");
		return result;
	}

	/** 
	 * 
	 * @return the name of the attribute 
	 */
	public String getName() { return this.name_; }
	
	/** 
	 * 
	 * @return the name of the attribute in the form
	 */
	public String getFormName() { return this.formName_; }	
	
	/**
	 * 
	 * @return the type of the attribute
	 */
	public String getType() { return this.type_; }
	
	/** answers if this attribute is required or not */
	public boolean isRequired() { return this.required_; }
	
	/** answers the ignored value of this attribute */
	public String getIgnoredValue() { return this.ignoredValue_; }
	
	/** export to a string */
	public String toString() {
		return ("name = " + name_ +  "| type = " + type_);
	}
}
