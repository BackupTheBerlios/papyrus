/*
 * Papyrus Gestion Commerciale
 * 
 * Created on 13 mars 2004
 *
 * Author: did
 */
package com.papyrus.data;

import java.sql.*;
import java.util.LinkedList;

import com.papyrus.common.*;

/**
 * @author did
 *
 * Permit to manage itembean (employee, agency, ...etc) with the simplest actions like add, delete or load.
 * It is designated to be very simple to use. Moreover, the access to the database is transparent to the developper.
 */
public class ItemBeanFactory {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(ItemBeanFactory.class.getName());

	/** 
	 * class of a basic element of the list 
	 */
	public Class itemClass_;
   
   	/* Constructors */
   	public ItemBeanFactory(Class pitemClass) {
	   logger_.debug("ItemBeanFactory : begin(" + pitemClass.getName() + ")");
		
	   itemClass_ = pitemClass;
		
	   logger_.debug("ItemBeanFactory : end");
   	}

	public LinkedList loadAll(String psqlQuery) throws PapyrusException {
		logger_.debug("loadAll : begin");
		
		/* variable needed for SQL */ 
		Connection connection = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LinkedList result = new LinkedList();
		
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			pstmt = connection.prepareStatement(psqlQuery);
			
			/* execute the query */
			rs = pstmt.executeQuery();		
			
			/* loop on each result */
			while (rs.next()) {
				/* create a new instance of the object */	
				ItemBean itemObject;
				
				itemObject = (ItemBean) itemClass_.newInstance();
					
				/* fill it with data */
				itemObject.setData(rs);
				
				/* add to the list */
				result.add(itemObject);
			}
		
			logger_.debug("loadAll : end(" + result.size() + ", " + rs.getRow() + ")");
			
			return result;
		} catch (Exception e) {
			logger_.debug("loadAll : Error(" + e.getMessage());
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					rs.close();
					connection.close();
				}
			} catch (Exception e) { }
		}
	}

	/**
	 * Wrapper to the load method. The user uses it with a simple int.
	 * @param psqlQuery
	 * @param id
	 * @return
	 * @throws PapyrusException
	 */
	public ItemBean load(String psqlQuery, int id) throws PapyrusException {
		
		LinkedList parameters = new LinkedList();
		parameters.add(new Integer(id));
		
		return (load(psqlQuery, parameters));
	}

	/**
	 * Load an unique object from parameters. Return the first record.
	 * @param psqlQuery the SQL query to load 
	 * @param pparameters the list of parameters
	 * @return
	 * @throws PapyrusException
	 */
	public ItemBean load(String psqlQuery, LinkedList pparameters) throws PapyrusException {
	logger_.debug("load : begin");
		
	/* variable needed for SQL */ 
	Connection connection = null; 
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ItemBean itemObject = null;
		
	try {
		connection = PapyrusDatabasePool.getInstance().getConnection();
		pstmt = connection.prepareStatement(psqlQuery);
			
		/* set all the parameters in the prepared statement 
		 * WARNING: the index of the array is different from the prepareStatement one 
		 */
		for (int i = 0; i < pparameters.size(); i++) {
			Object param = pparameters.get(i);
			String className = param.getClass().getName().substring(param.getClass().getName().lastIndexOf(".") + 1);
						
			logger_.debug("load : className=" + className + ", value=" + param);
						
			/* String Object */
			if ("String".equals(className))
				pstmt.setString(i + 1, (String) param);
						
			/* Integer */
			if ("Integer".equals(className))
				pstmt.setInt(i + 1, ((Integer) param).intValue());
							
			/* Long */
			if ("Long".equals(className))
				pstmt.setLong(i + 1, ((Long) param).longValue());
							
			/* Float */
			if ("Float".equals(className))
				pstmt.setFloat(i + 1, ((Float) param).floatValue());	
							
			/* Float */
			if ("Boolean".equals(className))
				pstmt.setBoolean(i + 1, ((Boolean) param).booleanValue());	
		}
				
		/* execute the query */
		rs = pstmt.executeQuery();
				
		if (rs.next()) {
			/* create a new instance of the object */	
			itemObject = (ItemBean) itemClass_.newInstance();
					
			/* fill it with data */
			itemObject.setData(rs);
		}
		logger_.debug("load : end");
				
		return itemObject;
		} catch (Exception e) {
			logger_.debug("load : Error(" + e.getMessage());
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					rs.close();
					connection.close();
				}
			} catch (Exception e) { }
		}	
	}
}
