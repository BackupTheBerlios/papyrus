/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 16 mars 2004
 *
 * Author: did
 */
package com.papyrus.data.type;

import java.util.LinkedList;
import java.util.HashMap;

import com.papyrus.data.*;
import com.papyrus.common.*;

/**
 * @author did
 *
 */
public class TypeListBean {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(TypeListBean.class.getName());

	/** SQL Query to load all records */
	private static String LOAD_SQL_QUERY = "SELECT type.id, type.key, type.value FROM type ORDER BY id, key;";

	/** list of all types */
	public static LinkedList list_ = null;

	/** hashmap: id for the key and the list of attributes as value */
	public static HashMap hashMap_ = null;

	public TypeListBean() throws PapyrusException {
		logger_.debug("TypeListBean : begin");
	
		try {
			if (null == list_) {
				ItemBeanFactory typeBeanFactory = new ItemBeanFactory(TypeBean.class);
				logger_.debug("TypeListBean : ItemBeanFactory create");
			
				list_ = typeBeanFactory.loadAll(LOAD_SQL_QUERY);
				logger_.debug("TypeListBean : load All type OK(" + list_.size() + ")");
				
				hashMap_ = constructHashMap();
			}
		} catch (PapyrusException e) {
			throw new PapyrusException(e.getMessage());
		}
		logger_.debug("TypeListBean : end(" + list_ + ")"); 
	}

	/** return the value of a type from an id and a key */
	public String getType(String pid, String pkey) {
		TypeBean typeBean = null;
		
		for (int i = 0; i < list_.size(); i++) {
			typeBean = (TypeBean) list_.get(i);
			
			if (pid.equals(typeBean.getId()) && pkey.equals(typeBean.getKey()))
				return (typeBean.getValue());
		}
		
		return null;
	}
	
	/**
	 * From an id, return all the elements 
	 * @param pid
	 * @return the list containing all elements which the id specified in param
	 */
	public HashMap constructHashMap() {
		logger_.debug("constructHashMap : begin");
		
		HashMap hashMap = new HashMap();
		
		for (int i = 0; i < list_.size(); i++) {
			TypeBean typeBean = (TypeBean) list_.get(i);
			
			if (hashMap.containsKey((String) typeBean.getId())) {
				logger_.debug("constructHashMap : add entry (" + typeBean.getId() + ")");
				/* no new entry so we add the current typeBean to the existing list */	
				LinkedList list = (LinkedList) hashMap.get((String) typeBean.getId());
				
				list.add(typeBean);
			} else {
				logger_.debug("constructHashMap : create new entry (" + typeBean.getId() + ")");
				/* create a new entry */
				LinkedList list = new LinkedList();
				list.add(typeBean);
				
				hashMap.put(typeBean.getId(), list);
			}	
		}
		logger_.debug("constructHashMap : end");
		
		return hashMap;
	}
	
	public HashMap getHashMap() {
		return hashMap_;
	}
}
