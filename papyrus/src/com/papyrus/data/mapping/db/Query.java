/**
 * Papyrus Gestion Commerciale
 * 
 * Created on 8 mai 2004
 *
 * Author: did
 */
  
package com.papyrus.data.mapping.db;

import java.sql.Types;

import org.w3c.dom.*;

import com.papyrus.common.Logger;

/**
 * @author did
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Query {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(DBMappingObject.class.getName());
	
	/** Const: possible name of the query: ADD / UPDATE / DELETE */
	public static final int NO_NAME = 0;
	public static final int ADD_NAME = 1;
	public static final int UPDATE_NAME = 2;
	public static final int DELETE_NAME = 3;
	
	/** name of the query (in association with the type in fact) */
	private String typeName_ = null;
	
	/** query */
	private String query_ = null;
	
	/** type of the return value (in case of a stored procedure) */
	private String returnType_ = null;
	
	/** stored procedure flag */
	private boolean storedProcedure_ = false;
	
	/**
	 * Create query from a list of attributes of a node
	 * @param pattributesMap attributes of the node
	 */
	public Query(NamedNodeMap pattributesMap) {
		logger_.debug("Query : begin");
		
		/* set the attribute */
		if (null != pattributesMap) {
			/* name */
			Node node = (Node) pattributesMap.getNamedItem("name");
			if (null != node)
				typeName_ = node.getNodeValue();	
		
			/* query */
			node = (Node) pattributesMap.getNamedItem("value");			
			if (null != node)
				query_ = node.getNodeValue();		
				
			/* return type */
			node = (Node) pattributesMap.getNamedItem("returnType");			
			if (null != node)
				returnType_ = node.getNodeValue();
				
			/* stored procedure */
			node = (Node) pattributesMap.getNamedItem("storedProcedure");			
			if (null != node)
				if ("true".equals((String) node.getNodeValue()))
					storedProcedure_ = true;
		}
		
		logger_.debug("Query : values = " + typeName_ + ", " +
						query_ + ", " +
						returnType_ + ", " +
						storedProcedure_);
		
		logger_.debug("Query : end");
	}
	
	/**
	 * Construct a complete stored procedure
	 * from the properties of the query
	 * @return
	 */
	public String getStoredProcedure() {
		if (!storedProcedure_)
			return null;
			
		StringBuffer sb = new StringBuffer("{");
		
		/* out parameter */
		if (null != returnType_)
			sb.append(" ? = ");
		
		/* main part */	
		sb.append("call ");
		sb.append(query_);		
		sb.append(" }");
		
		return sb.toString();
	}

	public int getSQLReturnType() {
		/* Integer */
		if ("Integer".equals(returnType_))
			return (Types.INTEGER);
		
		/* Integer */
		if ("Short".equals(returnType_))
			return (Types.SMALLINT);
		
		/* Long */
		if ("Long".equals(returnType_))
			return (Types.BIGINT);
			
		/* Float */
		if ("Float".equals(returnType_))
			return (Types.FLOAT);
			
		/* String */
		if ("String".equals(returnType_))
			return (Types.VARCHAR);
			
		/* Boolean */
		/* With postgreSQL database, the type for boolean is "bit" !!! */
		if ("Boolean".equals(returnType_))
			return (Types.BIT);
			
		/* Date */
		if ("Date".equals(returnType_))
			return (Types.DATE);
			
		return Types.NULL;
	}

	/**
	 * @return
	 */
	public String getQuery() {
		return query_;
	}

	/**
	 * @return
	 */
	public String getReturnType() {
		return returnType_;
	}

	/**
	 * @return
	 */
	public boolean isStoredProcedure() {
		return storedProcedure_;
	}

	/**
	 * @return
	 */
	public String getTypeName() {
		return typeName_;
	}

}
