/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 29 mars 2004
 *
 * Author: did
 */
package com.papyrus.data.mapping.form;

import org.w3c.dom.*;
import com.papyrus.common.Logger;

/**
 * @author did
 *
 */
public class Field {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(Field.class.getName());

	/** Name of the attribute in the form */
	private String name_ = null;

	/** Label: used for indicating error */
	private String label_ = null;

	/** Name of the attribute in the corresponding java class */
	private String objectField_ = null;
	
	/** Name of the attribute in the sql query */
	private String sqlName_ = null;
	
	/** Type: String, Integer, Float, boolean */
	protected String type_ = null;
	
	/** Ignored Value (used for sql search, in order to have for example all the agencies with a 0 value) */
	private String ignoredValue_ = null;

	/** Required attribute */
	private boolean required_ = false; 

	/**
	 * Construct a Field from a XML node with the basic properties.
	 * @param pattributesMap attributes of the node
	 */
	public Field(NamedNodeMap pattributesMap) {
		logger_.debug("Field : begin");
		
		if (null != pattributesMap) {
			/* type */
			Node node = (Node) pattributesMap.getNamedItem("type");
			if (null != node)
				type_ = node.getNodeValue();
			
			/* name */
			node = (Node) pattributesMap.getNamedItem("name");
			if (null != node)
				name_ = node.getNodeValue();
			
			/* label */
			node = (Node) pattributesMap.getNamedItem("label");
			if (null != node)
				label_ = node.getNodeValue();			
			
			/* object name */
			node = (Node) pattributesMap.getNamedItem("objectField");
			if (null != node)
				objectField_ = node.getNodeValue();			
		
			/* sql name */
			node = (Node) pattributesMap.getNamedItem("sqlName");
			if (null != node)
				sqlName_ = node.getNodeValue();		
						
			/* required */
			node = (Node) pattributesMap.getNamedItem("required");
			if (null != node)
				required_ = "true".equals(node.getNodeValue()) ? true : false;						
							
			/* ignored value */
			node = (Node) pattributesMap.getNamedItem("ignoredValue");
			if (null != node)
				ignoredValue_ = node.getNodeValue();						
		}
		
		logger_.debug("Field : end(" + this.toString() +")");	
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
	 * @return the name of the attribute in the form
	 */
	public String getName() { return this.name_; }
	
	/** 
	 * 
	 * @return the name of the label
	 */
	public String getLabel() { return this.label_; }	
	
	/** @return the name of attribute in an object */
	public String getObjectField() { return objectField_; }
	
	/** @return the name of attribute in a sql query */
	public String getSqlName() { return sqlName_; }
	
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
		return (name_ + " | " +
				label_ + " | " +
				sqlName_ + " | " +
				objectField_ + " | " + 
				type_ + " | " + 
				required_ + " | " +
				ignoredValue_ + " | ");
	}
}
