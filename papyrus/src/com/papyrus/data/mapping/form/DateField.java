/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 3 mai 2004
 *
 * Author: did
 */
 
package com.papyrus.data.mapping.form;

import org.w3c.dom.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.papyrus.common.Logger;

/**
 * @author did
 *
 * Attribute for Date type
 * 
 */
public class DateField extends Field {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(DateField.class.getName());
	
	/**
	 * Format of the date
	 */
	private SimpleDateFormat dateFormat_ = null;
	
	/**
	 * @param pattributesMap
	 * @param pformName
	 */
	public DateField(NamedNodeMap pattributesMap) {
		super(pattributesMap);
		
		logger_.debug("DateField : begin");
		
		String format = "dd/MM/yyyy";
		
		/* Set the format of the date */
		if (null != pattributesMap) {
			String tmp = ((Node) pattributesMap.getNamedItem("format")).getNodeValue();
			format = (null != tmp) ? tmp : format;
		} else
			dateFormat_ =  new SimpleDateFormat(format);
			
		logger_.debug("DateField : end(" + dateFormat_ + ")");
	}

	/**
	 * Check if the value specified by the parameter is correct or not
	 * @return null if the value does not match with the attribute type
	 * the value otherwise
	 */
	public Object getValue(String pstringValue) {
		logger_.debug("getValue : begin(" + pstringValue + ")");
	
		Date result = null;
		
		try { result = dateFormat_.parse(pstringValue); }
		catch (ParseException e) {
			logger_.debug("getValue : the date format is not correct (" + pstringValue + ")");
		}
		
		logger_.debug("getValue : end(" + result + ")");
		return result;
	}

}
