/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 1 mai 2004
 *
 * Author: did
 */

package com.papyrus.data.mapping.form;

import org.w3c.dom.*;
import java.util.regex.*;
import com.papyrus.common.Logger;

/**
 * @author did
 *
 * Attribute for the String type
 */
public class StringField extends Field {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(StringField.class.getName());
	
	/**
	 * Pattern used to verify if the value matches
	 */
	private Pattern pattern_ = null; 

	/**
	 * @param pattributesMap
	 * @param pformName
	 */
	public StringField(NamedNodeMap pattributesMap) {
		super(pattributesMap);
		
		logger_.debug("StringField : begin");
		
		/* Set the pattern */
		if (null != pattributesMap) {
			Node node = (Node) pattributesMap.getNamedItem("pattern");
			String tmp = null;
			
			if (null != node)
				 tmp = node.getNodeValue();
			
			if (null != tmp)
				pattern_ = Pattern.compile(tmp);
		}
		
		logger_.debug("StringField : end, pattern = " + pattern_);
	}

	public Object getValue(String pstringValue) {
		logger_.debug("getValue : begin(" + pstringValue + ")");
		
		Object result = null;
		
		if (null != pattern_) {
			Matcher matcher = pattern_.matcher(pstringValue);
			if (true == matcher.matches())
				result = pstringValue;
		} else
			result = pstringValue;
		
		logger_.debug("getValue : end(" + result + ")");
		return result;
	}

}
