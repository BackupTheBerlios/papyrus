/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 3 mai 2004
 *
 * Author: did
 */
 
package com.papyrus.data.form;

import org.w3c.dom.NamedNodeMap;
import com.papyrus.common.Logger;

/**
 * @author did
 *
 * Attribute for Boolean type
 * 
 */
public class BooleanAttribute extends Attribute {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(BooleanAttribute.class.getName());
	
	/**
	 * @param pattributesMap
	 * @param pformName
	 */
	public BooleanAttribute(NamedNodeMap pattributesMap, String pformName) {
		super(pattributesMap, pformName);
		
		logger_.debug("BooleanAttribute : begin(" + pformName + ")");
		
		logger_.debug("BooleanAttribute : end");
	}
	
	/**
	 * Check if the value specified by the parameter is correct or not
	 * @return null if the value does not match with the attribute type
	 * the value otherwise
	 */
	public Object getValue(String pstringValue) {
		logger_.debug("getValue : begin(" + pstringValue + ")");
	
		Boolean result = Boolean.valueOf(false);
		
		if ("true".equals(pstringValue.toLowerCase()) ||
			"yes".equals(pstringValue.toLowerCase()) ||
			"ok".equals(pstringValue.toLowerCase()) ||
			"on".equals(pstringValue.toLowerCase()))
			result = Boolean.valueOf(true);
	
		logger_.debug("getValue : end(" + result + ")");
		return result;
	}		
}
