/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 1 mai 2004
 *
 * Author: did
 */
 
package com.papyrus.data.mapping.form;

import org.w3c.dom.*;
import com.papyrus.common.Logger;

/**
 * @author did
 *
 * Attribute for the Float and Integer types
 * 
 */
public class NumberField extends Field {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(NumberField.class.getName());
	
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
	public NumberField(NamedNodeMap pattributesMap) {
		super(pattributesMap);
			
		logger_.debug("NumberField : begin");
		
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
					if ("Long".equals(type_))
						min_ = new Long(min);
					if ("Integer".equals(type_))
						min_ = new Integer(min);
					if ("Float".equals(type_))
						min_ = new Float(min);
					if ("Short".equals(type_))
						min_ = new Short(min);
						
					isMinLimited_ = true;
				} catch (NumberFormatException e) {
					logger_.warn(0, "NumberField : Min --> Bad Format Number(" + e.getMessage() + ")");
				}
			}
			
			/* max value */
			if (null != max) {
				try {
					if ("Long".equals(type_))
						max_ = new Long(max);
					if ("Integer".equals(type_))
						max_ = new Integer(max);
					if ("Float".equals(type_))
						max_ = new Float(max);
					if ("Short".equals(type_))
						max_ = new Short(max);
						
					isMaxLimited_ = true;
				} catch (NumberFormatException e) {
					logger_.warn(0, "NumberField : Max --> Bad Format Number(" + e.getMessage() + ")");
				}						
			}
			
		}
		
		logger_.debug("NumberField : end, min = " + min_ + "; max = " + max_);
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
				/* Long */
				if (min_ instanceof Long) {
					try { 
						if (min_.longValue() <= Long.parseLong(pstringValue))
							result = new Long(pstringValue);
					}
					catch (NumberFormatException e) { logger_.warn(0, "getValue : '" + pstringValue + "' is not a long"); }	
				}				
				/* Integer */
				if (min_ instanceof Integer) {
					try { 
						if (min_.intValue() <= Integer.parseInt(pstringValue))
							result = new Integer(pstringValue);
					}
					catch (NumberFormatException e) { logger_.warn(0, "getValue : '" + pstringValue + "' is not an integer"); }	
				}
				/* Short */
				if (min_ instanceof Short) {
					try { 
						if (min_.shortValue() <= Short.parseShort(pstringValue))
							result = new Short(pstringValue);
					}
					catch (NumberFormatException e) { logger_.warn(0, "getValue : '" + pstringValue + "' is not a short"); }	
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
				/* Long */
				if (max_ instanceof Long) {
					try { 
						if (max_.longValue() >= Long.parseLong(pstringValue))
							if (null == result)
								result = new Long(pstringValue);
					}
					catch (NumberFormatException e) { logger_.debug("getValue : '" + pstringValue + "' is not a long"); }	
				}
				/* Integer */
				if (max_ instanceof Integer) {
					try { 
						if (max_.intValue() >= Integer.parseInt(pstringValue))
							if (null == result)
								result = new Integer(pstringValue);
					}
					catch (NumberFormatException e) { logger_.debug("getValue : '" + pstringValue + "' is not an integer"); }	
				}
				/* Short */
				if (max_ instanceof Short) {
					try { 
						if (max_.shortValue() >= Short.parseShort(pstringValue))
							if (null == result)
								result = new Integer(pstringValue);
					}
					catch (NumberFormatException e) { logger_.debug("getValue : '" + pstringValue + "' is not a short"); }	
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
			try {
				if ("Long".equals(type_))
					result = new Long(pstringValue);
				if ("Integer".equals(type_))
					result = new Integer(pstringValue);
				if ("Short".equals(type_))
					result = new Short(pstringValue);
				if ("Float".equals(type_))
					result = new Float(pstringValue);
			} catch (NumberFormatException e) { logger_.debug("getValue : '" + pstringValue + "' is not a correct number"); } 
		}
			
		logger_.debug("getValue : end(" + result + ")");
		return result;
	}

}
