/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 16 mars 2004
 *
 * Author: did
 */
package com.papyrus.data.type;

import com.papyrus.common.*;
import com.papyrus.data.ItemBean;

/**
 * @author did
 *	Type class
 *
 */
public class TypeBean extends ItemBean {
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(TypeBean.class.getName());
	
	/** id */
	public String id_ = null;
	
	/** key */
	public String key_ = null;
	
	/** value */
	public String value_ = null;

	public TypeBean() {
		logger_.debug("TypeBean : begin");
		
		logger_.debug("TypeBean : end");
	}

	/**
	 * @return Id
	 */
	public String getId() {
		return id_;
	}

	/**
	 * @return Key
	 */
	public String getKey() {
		return key_;
	}

	/**
	 * @return Value
	 */
	public String getValue() {
		return value_;
	}

	/**
	 * @param string
	 */
	public void setId(String string) {
		id_ = string;
	}

	/**
	 * @param string
	 */
	public void setKey(String string) {
		key_ = string;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value_ = string;
	}

}
