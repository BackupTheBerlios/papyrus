/*
 * Papyrus Gestion Commerciale
 * 
 * Created on Feb 28, 2004
 *
 * Author: did
  */
  
package com.papyrus.data;

import java.lang.reflect.*;
import java.sql.*;
import javax.sql.rowset.RowSetMetaDataImpl;
import com.sun.rowset.*;

import com.papyrus.common.*;
import com.papyrus.data.form.*;

/**
 * @author did
 *
 * This class is used by all the beans in the application. The ItemBean class
 * permits to store data from a resultset (or a resultset) independently of the
 * class attributes. Thus, we can use different search queries which not return 
 * the same number of columns (we can have a complete search query with all fields
 * returned and a light search query with the main fields).  
 */
public class ItemBean {
		
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(ItemBean.class.getName());	
		
	public ItemBean() {
		logger_.debug("ItemBean : begin");
		
		logger_.debug("ItemBean : end");
	}

	/**
	 * Set data from a rowset and store it 
	 * @return void
	 */
	public void setData(CachedRowSetImpl prowset) throws PapyrusException {
		logger_.debug("setData : begin");
	
		Object result = null;
		java.lang.reflect.Field targetField = null;
	
		try {
			RowSetMetaDataImpl rsmd = (RowSetMetaDataImpl) prowset.getMetaData();
	
			logger_.debug("setData : Nb Column = " + rsmd.getColumnCount());
	
			/* Associate index with the attribute field */
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {	
				logger_.debug("setData : ColumnName = " + rsmd.getColumnName(i));
		
				/* we suppose that the attribut has the prototype: "nameattribute_" */
				/* WARNING: in pgsql, we don't have upper name => always lower char */
				try {
					targetField = this.getClass().getField(rsmd.getColumnName(i) + "_");
		
					if (null != targetField) {
						logger_.debug("setData : field found");
						
						switch (rsmd.getColumnType(i)) {
							/* String */
							case Types.VARCHAR: 
								targetField.set(this, prowset.getString(i));
								break;
							/* Integer */
							case Types.INTEGER:
								targetField.setInt(this, prowset.getInt(i));
								break;
							/* Integer (bigint) */
							case Types.BIGINT:
								targetField.setInt(this, prowset.getInt(i));
								break;							
							default:
								logger_.debug("setDate : type unknown (" + rsmd.getColumnType(i) + ")");
						}
					} 
				} 
				catch (NoSuchFieldException e) { logger_.debug("setData : no corresponding field(" + e.getMessage() + ")"); }
				catch (IllegalAccessException e) { logger_.debug("setData : Illegal Access to the current field(" + e.getMessage() + ")"); }
			}
		} catch (SQLException e) {
			throw new PapyrusException (e.getMessage ());
		}
	
		logger_.debug("setData : end");
	}	
	
	/**
	 * Set data from a resultset and store it 
	 * @return void
	 */
	public void setData(ResultSet presultSet) throws PapyrusException {
		logger_.debug("setData : begin");
	
		Object result = null;
		java.lang.reflect.Field targetField = null;
	
		try {
			ResultSetMetaData rsmd = (ResultSetMetaData) presultSet.getMetaData();
	
			logger_.debug("setData : Nb Column = " + rsmd.getColumnCount());
	
			/* Associate index with the attribute field */
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {	
				logger_.debug("setData : ColumnName = " + rsmd.getColumnName(i));
		
				/* we suppose that the attribut has the prototype: "nameattribute_" */
				/* WARNING: in pgsql, we don't have upper name => always lower char */
				try {
					targetField = this.getClass().getField(rsmd.getColumnName(i) + "_");
		
					if (null != targetField) {
						logger_.debug("setData : field found");
						
						switch (rsmd.getColumnType(i)) {
							/* String */
							case Types.VARCHAR: 
								targetField.set(this, presultSet.getString(i));
								break;
							/* Integer */
							case Types.INTEGER:
								targetField.setInt(this, presultSet.getInt(i));
								break;
							/* Boolean */
							case Types.BOOLEAN:
								targetField.setBoolean(this, presultSet.getBoolean(i));
								break;
							/* Integer (bigint) */
							case Types.BIGINT:
								targetField.setInt(this, presultSet.getInt(i));
								break;
							/* Integer (short) */
							case Types.SMALLINT:
								targetField.setInt(this, presultSet.getInt(i));
								break;
							default:
								logger_.debug("setDate : type unknown (" + rsmd.getColumnType(i) + ")");
						}
					} 
				} 
				catch (NoSuchFieldException e) { logger_.debug("setData : no corresponding field(" + e.getMessage() + ")"); }
				catch (IllegalAccessException e) { logger_.debug("setData : Illegal Access to the current field(" + e.getMessage() + ")"); }
			}
		} catch (SQLException e) {
			throw new PapyrusException (e.getMessage ());
		}
	
		logger_.debug("setData : end");
	}	
	
	public void setData(FormBean pitemFormBean) {
		logger_.debug("setData : begin");
		
		/* Get the set of fields of the form */
		Object[] fieldArray = pitemFormBean.getData().keySet().toArray();

		/* Set the value with the adequat set method of the bean */
		for (int i = 0; i < fieldArray.length; i++) {
			String fieldName = (String) fieldArray[i];
			Object value = pitemFormBean.getValue(fieldName);
			
			if (null != value) {
				char[] methodNameTmp = fieldName.toCharArray();
				Class[] parameterTypes = new Class[1];
				//Object[] args = new Object[1];
			
				/* Find the correct set method */
				if (97 <= methodNameTmp[0] && 122 >= methodNameTmp[0]) {
					/* the first letter is converted into lowercase */
					methodNameTmp[0] -= (char) 32;
				}
			
				String methodName = "set" + new String(methodNameTmp);
				logger_.debug("setData : method name = " + methodName);
				logger_.debug("setData : value value = " + value);
				logger_.debug("setData : value type = " + (null != value ? value.getClass().getName() : "null"));
			
				/* Search for the type 
				 * WARNING: convert Integer, Float, Boolean into its primitive type
				 */
				 parameterTypes[0] = value.getClass();
				 
				if (value instanceof Integer)
					parameterTypes[0] = int.class;
					
				if (value instanceof Float)
					parameterTypes[0] = float.class;
					
				if (value instanceof Boolean)
					parameterTypes[0] = boolean.class;	
				
				try {
					/* load the method */
					Object[] args = { value };
					Method method = this.getClass().getMethod(methodName, parameterTypes);
					
					/* execute it */
					method.invoke(this, args);
					
					logger_.debug("setData : call method OK");
					
				} catch (NoSuchMethodException e) { 
					logger_.info("setData : No such method (" + e.getMessage() + ")");
				} catch (InvocationTargetException e) {
					logger_.info("setData : Invocation Target Error (" + e.getMessage() + ")");
				} catch (IllegalAccessException e) {
					logger_.info("setData : Method is inaccessible (" + e.getMessage() + ")");
				}
				
			} else
				logger_.debug("setData : null value of the field " + fieldName);
		}
		logger_.debug("setData : end");
	}
}
