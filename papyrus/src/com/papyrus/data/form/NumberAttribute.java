/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 1 mai 2004
 *
 * Author: did
 */
 
package com.papyrus.data.form;

import org.w3c.dom.*;
import com.papyrus.common.Logger;

/**
 * @author did
 *
 * Attribute for the Float and Integer types
 * 
 */
public class NumberAttribute extends Attribute {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(NumberAttribute.class.getName());
	
	/**
	 * answers the fact that the attribute value is limited (min or/and max values).
	 */
	private boolean isMinLimited_ = false;
	private boolean isMaxLimited_ = false;
	
	/** limited values: int and float */
	private Number min_ = null;
	private Number max_ = null;

	/**
	 * @param pattributesMap
	 * @param pformName
	 */
	public NumberAttribute(NamedNodeMap pattributesMap, String pformName) {
		super(pattributesMap, pformName);
			
		logger_.debug("NumberAttribute : begin(" + pformName + ")");
		
		/* Set the limited values */
		if (null != pattributesMap) {
			String min = null;
			String max = null;
			Node nodeMin = (Node) pattributesMap.getNamedItem("min");
			Node nodeMax = (Node) pattributesMap.getNamedItem("max");
						
			if (null != nodeMin)
				min = nodeMin.getNodeValue();
			
			if (null != nodeMax)
				max = nodeMax.getNodeValue();
			
			/* min value */
			if (null != min) {
				try {
					if ("Integer".equals(type_))
						min_ = new Integer(min);
					if ("Float".equals(type_))
						min_ = new Float(min);
						
					isMinLimited_ = true;
				} catch (NumberFormatException e) {
					logger_.warn(0, "NumberAttribute : Min --> Bad Format Number(" + e.getMessage() + ")");
				}
			}
			
			/* max value */
			if (null != max) {
				try {
					if ("Integer".equals(type_))
						max_ = new Integer(max);
					if ("Float".equals(type_))
						max_ = new Float(max);
						
					isMaxLimited_ = true;
				} catch (NumberFormatException e) {
					logger_.warn(0, "NumberAttribute : Max --> Bad Format Number(" + e.getMessage() + ")");
				}						
			}
			
		}
		
		logger_.debug("NumberAttribute : end, min = " + min_ + "; max = " + max_);
	}
	
	/**
	 * Check if the value specified by the parameter is correct or not
	 * @return null if the value does not match with the attribute type
	 * the value otherwise
	 */
	public Object getValue(String pstringValue) {
		logger_.debug("getValue : begin(" + pstringValue + ")");
	
		Object result = null;
		
		/* must the number be between min and/or max ??? */
		if (true == isMinLimited_ || true == isMaxLimited_) {
			/* Minimum value */
			if (true == isMinLimited_) {
				/* Integer */
				if (min_ instanceof Integer) {
					try { 
						if (min_.intValue() <= Integer.parseInt(pstringValue))
							result = new Integer(pstringValue);
					}
					catch (NumberFormatException e) { logger_.warn(0, "getValue : '" + pstringValue + "' is not an integer"); }	
				}
				/* Float */
				if (min_ instanceof Float) {
					try { 
						if (min_.floatValue() <= Float.parseFloat(pstringValue))
							result = new Float(pstringValue);
					}
					catch (NumberFormatException e) { logger_.debug("getValue : '" + pstringValue + "' is not a float"); }	
				}
			} 
		
			/* Maximum value */
			if (true == isMaxLimited_) {
				/* Integer */
				if (max_ instanceof Integer) {
					try { 
						if (max_.intValue() >= Integer.parseInt(pstringValue))
							if (null == result)
								result = new Integer(pstringValue);
					}
					catch (NumberFormatException e) { logger_.debug("getValue : '" + pstringValue + "' is not an integer"); }	
				}
				/* Float */
				if (max_ instanceof Float) {
					try { 
						if (max_.floatValue() >= Float.parseFloat(pstringValue))
							if (null == result)
								result = new Float(pstringValue);
					}
					catch (NumberFormatException e) { logger_.debug("getValue : '" + pstringValue + "' is not a float"); }	
				}
			}
		} else {
			if ("Integer".equals(type_))
				result = new Integer(pstringValue);
			if ("Float".equals(type_))
				result = new Float(pstringValue);
		}
			
		logger_.debug("getValue : end(" + result + ")");
		return result;
	}

}
