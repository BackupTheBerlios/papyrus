/**
 * Papyrus Gestion Commerciale
 * 
 * Created on 8 mai 2004
 *
 * Author: did
 */
package com.papyrus.data.mapping.db;

import com.papyrus.common.Utilities;
import com.papyrus.common.Logger;
import com.papyrus.common.PapyrusDatabasePool;
import com.papyrus.common.PapyrusException;

import org.w3c.dom.*;
import java.sql.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author did
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DBMappingObject {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(DBMappingObject.class.getName());

	/** const: type of stored procedures */
	public final static String ADD_SP_NAME = "ADD";
	public final static String UPDATE_SP_NAME = "UPDATE";
	public final static String DELETE_SP_NAME = "DELETE"; 

	/** name of the mapped class */
	private String class_ = null;
	
	/** name of the mapped table (SQL) */ 
	private String table_ = null;
	
	/** name of the view (optional) */
	private String view_ = null;
		
	/** id of the object */
	private Property id_ = null;
	
	/** list of properties of the object */
	private HashMap properties_ = null;
	
	/** list of queries */
	private HashMap queriesMap_ = null;
	
	/** use to retrieve a property from a sql column name */
	private HashMap reverseProperties_ = null;
	
	/**
	 * Create a DBMappingObject from a XML node
	 */
	public DBMappingObject(Node pnode) {
		logger_.debug("DBMappingObject : begin");
		
		/* initialize HashMap(s) */
		properties_ = new HashMap();
		reverseProperties_ = new HashMap();
		queriesMap_ = new HashMap();
		
		/* if the current node is an element, go on */
		if (Node.ELEMENT_NODE == pnode.getNodeType()) {
			/* Children nodes */
			NodeList children = ((Node) pnode).getChildNodes();
			
			/* name of the class */
			class_ = ((Element) pnode).getAttribute("name");
		
			/* name of the table */
			table_ = ((Element) pnode).getAttribute("table");
		
			/* name of the view (optional) */
			view_ = ((Element) pnode).getAttribute("view");
					
			for (int i = 0; i < children.getLength(); i++) {
				Node childNode = (Node) children.item(i);
				
				/* if it is an element, OK */
				if (Node.ELEMENT_NODE == childNode.getNodeType()) {
					logger_.debug("DBMappingObject : name = " + childNode.getNodeName());
					
					if ("id".equals(childNode.getNodeName())) {
						id_ = new Property(childNode.getAttributes());
						
						/* add to the properties hashtable and the reverseProperties too */
						properties_.put(id_.getName(), id_);
						reverseProperties_.put(id_.getColumn(), id_);
					}
					
					if ("property".equals(childNode.getNodeName())) {
						Property property = new Property(childNode.getAttributes());
						
						/* add to the properties hashtable and the reverseProperties too */
						properties_.put(property.getName(), property);
						reverseProperties_.put(property.getColumn(), property);
					}
					
					if ("query".equals(childNode.getNodeName())) {
						Query query = new Query(childNode.getAttributes());
						queriesMap_.put(query.getTypeName(), query);
					}
				}
			}
			
		}
		logger_.debug("DBMappingObject : end");
	}
	
	/**
	 * Create an object from a resultset (or a rowset with extending)
	 * @param presultSet
	 * @return a new object 
	 * @throws PapyrusException
	 */
	public Object createNewObject(ResultSet presultSet) throws PapyrusException {
		logger_.debug("createNewObject : begin");
		
		Object obj = null;
		
		try {
			/* create a new instance of this object */
			Class classTmp = Class.forName(class_);
			obj = classTmp.newInstance();
			
			ResultSetMetaData rsmd = (ResultSetMetaData) presultSet.getMetaData();
			logger_.debug("createNewObject : nb column = " + rsmd.getColumnCount());
			
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {	
				logger_.debug("createNewObject : ColumnName = " + rsmd.getColumnName(i));
				
				/* search the corresponding property */
				Property property = getReverseProperty(rsmd.getColumnName(i));
				if (null != property) {
					/* set the value to the attribute */				
					Utilities.setObjectData(obj, property.getName(), presultSet.getObject(i));
				} else {
//					/* check if it is not the id of the object */
//					if (rsmd.getColumnName(i).equals(id_.getColumn())) {
//						/* id found */
//						Utilities.setObjectData(obj, id_.getName(), presultSet.getObject(i));
//					} else
						logger_.debug("createNewObject : property not found");
				}
			}
		} catch (ClassNotFoundException e) {
			logger_.debug("createNewObject : the class " + class_ + " does not exist");
			throw new PapyrusException(e.getMessage()); 
		} catch (IllegalAccessException e) {
			logger_.debug("createNewObject : it is not possible to access to the class " + class_);
			throw new PapyrusException(e.getMessage()); 
		} catch (InstantiationException e) {
			logger_.debug("createNewObject : it is not possible to instanciate the class " + class_);
			throw new PapyrusException(e.getMessage()); 
		} catch (SQLException e) {
			logger_.debug("createNewObject : SQL ERROR (" + e.getMessage() + ")");
			throw new PapyrusException(e.getMessage()); 
		}
		
		logger_.debug("createNewObject : end");
		return obj;
	}
	
	public LinkedList loadByWhere(String pattributes[], Object pvalues[]) throws PapyrusException {
		logger_.debug("loadByWhere : begin");
		
		LinkedList result = new LinkedList();
		Collection fields = properties_.values();
		Connection connection = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM ";
		
		/* create the query */
		query += (null == view_) ? table_ : view_;
		
		if (0 != pattributes.length && pattributes.length == pvalues.length) {
			query += " WHERE ";
			
			for (int i = 0; i < pattributes.length; i++) {
				/* find the associated property */
				Property property = (Property) properties_.get(pattributes[i]);
				
				if (null != property) {
					query += "\"" + property.getColumn() + "\" = ? ";
					if ((i + 1) != pattributes.length) 
						query += "AND ";
				}
			}
		}
		logger_.debug("loadByWhere : query = " + query);
		
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			pstmt = connection.prepareStatement(query);
			
			/* set the values */
			if (0 != pattributes.length && pattributes.length == pvalues.length) 
				for (int i = 0; i < pattributes.length; i++) {
					/* find the associated property */
					Property property = (Property) properties_.get(pattributes[i]);
					Object value = pvalues[i];
					
					/* String Object */
					if (value instanceof java.lang.String)
						pstmt.setString(i + 1, (String) value);
	
					/* Short */
					else if (value instanceof java.lang.Short)
						pstmt.setShort(i + 1, ((Short) value).shortValue());	
						
					/* Integer */
					else if (value instanceof java.lang.Integer)
						pstmt.setInt(i + 1, ((Integer) value).intValue());
						
					/* Long */
					else if (value instanceof java.lang.Long)
						pstmt.setLong(i + 1, ((Long) value).longValue());
						
					/* Float */
					else if (value instanceof java.lang.Float)
						pstmt.setFloat(i + 1, ((Float) value).floatValue());
						
					/* Boolean */
					else if (value instanceof java.lang.Boolean)
						pstmt.setBoolean(i + 1, ((Boolean) value).booleanValue());
						
					/* Date */
					else if (value instanceof java.util.Date)
						pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) value).getTime()));
				}
			
			/* execute the query */
			rs = pstmt.executeQuery();
			
			/* construct the list */
			while (rs.next())
				result.add(createNewObject(rs));
			
			logger_.debug("loadByWhere : end(" + result.size() + ")");
			return result;
		} catch (Exception e) {
			logger_.debug("loadByWhere : Error(" + e.getMessage());
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
	 * Load from the database the object from an unique id
	 * @param pid the identifier in the database of the object
	 * @return the object if it exists, null otherwise
	 * @throws PapyrusException
	 */
	public Object load(Object pid) throws PapyrusException {
		logger_.debug("load : begin (" + pid + ", " + pid.getClass().getName() + ", " + view_ + ")");
		
		/* variable needed for SQL */ 
		Connection connection = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		Object result = null;
		
		/* check if there is a view to use */
		if (null == view_ || 0 == view_.length())
			query = "SELECT * FROM " + table_ + " WHERE " + id_.getName() + " = ?;";
		else
			query = "SELECT * FROM " + view_ + " WHERE " + id_.getName() + " = ?;";
		
		logger_.debug("load : query = " + query);
		
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			pstmt = connection.prepareStatement(query);
		
			/* String Object */
			if (pid instanceof String)
				pstmt.setString(1, (String) pid);
				
			/* Integer object */
			else if (pid instanceof Integer)
				pstmt.setInt(1, ((Integer) pid).intValue());
			
			/* Date */
			else if (pid instanceof Date)
				pstmt.setDate(1, (Date) pid);
							
			/* Long */
			else if (pid instanceof Long)
				pstmt.setLong(1, ((Long) pid).longValue());	
		
			/* execute the query */
			rs = pstmt.executeQuery();
				
			if (rs.next()) {
				/* create a new instance of the object */	
				result = createNewObject(rs);
			}
			logger_.debug("load : end");
				
			return result;
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
	
	/**
	 * Load all the objects from a table or a view 
	 * @return a list with all the objects
	 * @throws PapyrusException
	 */
	public LinkedList loadAll() throws PapyrusException {
		logger_.debug("loadAll : begin");
		
		/* variable needed for SQL */ 
		Connection connection = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		LinkedList result = new LinkedList();
		
		/* check if there is a loadQuery to use, else construct from properties of the mapping */
		if (null == view_)
			query = "SELECT * FROM " + table_ + ";";
		else
			query = "SELECT * FROM " + view_ + ";";
		
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			pstmt = connection.prepareStatement(query);
		
			/* execute the query */
			rs = pstmt.executeQuery();
				
			while (rs.next()) {
				/* create a new instance of the object and add it to the list */	
				result.add(createNewObject(rs));
			}
			logger_.debug("loadAll : end(" + result.size() + ")");
				
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
	 * Construct a query which is a call 
	 * to a stored procedure
	 * @param ptype add, update or delete
	 * @return the constructed query
	 */
	public String constructCallQuery(String ptype) {
		logger_.debug("constructCallQuery : begin");
		
		try {
			LinkedList fields = getPropertiesSortedList(ptype);
			StringBuffer sb = new StringBuffer(((Query) queriesMap_.get(ptype)).getStoredProcedure());
		
		sb.append("(");
		
		/* create the query from the position indicated
		 * into each column 
		 */
		for (int i = 0; i < fields.size(); i++) {
			Property property = (Property) fields.get(i);
			
			/* check the type of the column */
			sb.append("?");
			
			if ((i + 1) < fields.size())
				sb.append(", ");
		}
		
		sb.append(") }");
		logger_.debug("constructCallQuery : end(" + sb.toString() + ")");
				return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger_.debug("constructCallQuery : ERROR = " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * Construct an adding (SQL INSERT statement) query
	 * from the name of the table and all the fields 
	 * @return a SQL INSERT query
	 */
	public String contructAddQuery() {
		logger_.debug("constructQuery : begin");
		
		/* check if there is an adding query, else construct it */ 
		Collection fields = properties_.values();
		StringBuffer sb = new StringBuffer("INSERT INTO");
		StringBuffer valuesSb = new StringBuffer(" values (");
			
		sb.append(table_);
		sb.append(" (");
			
		/* from all the properties, construct the query */
		for (Iterator i = fields.iterator(); i.hasNext(); i.next()) {
			Property property = (Property) i;
		
			sb.append(property.getColumn());
			valuesSb.append("?");
			/* end of the collection ? */
			if (i.hasNext()) {
				sb.append(", ");
				valuesSb.append(", ");
			} else {
				sb.append(")");
				valuesSb.append(");");
			}
		}
		sb.append(valuesSb);
		
		logger_.debug("constructQuery : end (" + sb + ")");
		return sb.toString();
	}
	
	/**
	 * Construct an updating (SQL UPDATE statement) query
	 * from the name of the table and all the fields 
	 * @return a SQL UPDATE query
	 */
	public String contructUpdateQuery() {
		logger_.debug("contructUpdateQuery : begin");
		
		/* check if there is an adding query, else construct it */ 
		Collection fields = properties_.values();
		StringBuffer sb = new StringBuffer("UPDATE ");
		sb.append(table_);
		sb.append(" SET ");
			
		/* from all the properties, construct the query */
		for (Iterator i = fields.iterator(); i.hasNext(); i.next()) {
			Property property = (Property) i;
		
			sb.append(property.getColumn());
			sb.append(" = ?");
			/* end of the collection ? */
			if (i.hasNext())
				sb.append(", ");	
		}
		sb.append(" WHERE ");
		sb.append(id_.getColumn());
		sb.append(" = ?;");
		
		logger_.debug("contructUpdateQuery : end (" + sb + ")");
		return sb.toString();
	}
	
	/**
	 * Construct an deleting (SQL DELETE statement) query
	 * from the name of the table and all the fields 
	 * @return a SQL DELETE query
	 */
	public String contructDeleteQuery() {
		logger_.debug("contructDeleteQuery : begin");
		
		/* check if there is an adding query, else construct it */ 
		Collection fields = properties_.values();
		StringBuffer sb = new StringBuffer("DELETE FROM ");
		sb.append(table_);
		
		sb.append(" WHERE ");
		sb.append(id_.getColumn());
		sb.append(" = ?;");
		
		logger_.debug("contructDeleteQuery : end (" + sb + ")");
		return sb.toString();
	}
	
	/*************************/
	/**			ADD			**/
	/*************************/
	
	/**
	 * Execute an INSERT query from the values of an object
	 * @param pobject represents the object to save in the database
	 * @return true if the execution worked, false otherwise
	 * @throws PapyrusException
	 */
	public boolean addSimple(Object pobject) throws PapyrusException {
		logger_.debug("addSimple : begin (" + pobject + ")");
		 
		/* variable needed for SQL */ 
		Connection connection = null; 
		PreparedStatement pstmt = null; 
		 
		String query = contructAddQuery();
		Collection fields = properties_.values();
		boolean result = true;
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			pstmt = connection.prepareStatement(query);
			int count = 1;
		
			/* loop on each child to fill the prepared statement */
			for (Iterator i = fields.iterator(); i.hasNext(); i.next(), count++) {
				Property property = (Property) i;
				Object value = null;
				
				/* value to insert if it is possible */
				if (null != value) {
					if (value instanceof Integer)
						pstmt.setInt(count, ((Integer) value).intValue());
					
					if (value instanceof Float)
						pstmt.setFloat(count, ((Float) value).floatValue());
					
					if (value instanceof String)
						pstmt.setString(count, (String) value);
						
					if (value instanceof Boolean)
						pstmt.setBoolean(count, ((Boolean) value).booleanValue());
						
					if (value instanceof Date)
						pstmt.setDate(count, (Date) value);
				}
			}			
			result = pstmt.execute();

			logger_.debug("addSimple : end (" + result + ")");
			return result;
		} catch (Exception e) {
			logger_.debug("addSimple : Error(" + e.getMessage());
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) { }
		}			
	}
	
	/**
	 * Execute a stored procedure to insert object
	 * in the database
	 * @param pobject the object to insert
	 * @return a object specified in the xml config
	 * @throws PapyrusException
	 */
	public Object addWithStoredProcedure(Object pobject) throws PapyrusException {
		logger_.debug("addWithStoredProcedure : begin (" + pobject + ")");
		
		/* variable needed for SQL */ 
		Connection connection = null; 
		CallableStatement cstmt = null;
		
		Collection fields = properties_.values();
		Query query = (Query) queriesMap_.get("ADD");
		Object result = null;
		int offset = 0;
		
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			
			connection.setAutoCommit(false);
			
			logger_.debug("addWithStoredProcedure : fields size = " + fields.size());
			
			logger_.debug("addWithStoredProcedure : query = " + query.getStoredProcedure());
			
			cstmt = connection.prepareCall(constructCallQuery(ADD_SP_NAME)); //query.getStoredProcedure());
			
			logger_.debug("addWithStoredProcedure : statement = " + cstmt);
			
			/* register the out parameter */
			if (null != query.getReturnType()) {
				cstmt.registerOutParameter(1, query.getSQLReturnType());
				offset = 1;
			}	
			
			logger_.debug("addWithStoredProcedure : statement 2 = " + cstmt);
			
			/* loop on each child to fill the prepared statement */
			for (Iterator i = fields.iterator(); i.hasNext();) {
				Property property = (Property) i.next();
				
				logger_.debug("addWithStoredProcedure : property = " + property);
				
				Object value = null;
				int pos = 0;
				
				/* check where we can insert the value and in which position */
				if (0 != property.getAdd()) {
					value = Utilities.getObjectData(pobject, property.getName());
					pos = property.getAdd() + offset;
				}
					
				logger_.debug("addWithStoredProcedure : pos = " + pos + ", value = " + value + ", class = " + (null != value ? value.getClass().getName() : "NOTHING"));	
					
				/* value to insert if it is possible */
				if (0 != property.getAdd()) {
					if ("Integer".equals(property.getType()))
						cstmt.setInt(pos, ((Integer) value).intValue());
					
					if ("Long".equals(property.getType()))
						cstmt.setLong(pos, ((Long) value).longValue());
					
					if("Short".equals(property.getType()))
						cstmt.setShort(pos, ((Short) value).shortValue());
					
					if ("Float".equals(property.getType()))
						cstmt.setFloat(pos, ((Float) value).floatValue());
					
					if ("String".equals(property.getType()))
						cstmt.setObject(pos, value, Types.VARCHAR);
//							//cstmt.setString(pos, (String) value);
//						else 
//							cstmt.setObject(pos, value, Types.VARCHAR);
//							//cstmt.setString(pos, null);
//						
					if ("Boolean".equals(property.getType()))
						cstmt.setBoolean(pos, ((Boolean) value).booleanValue());
						
					if ("Date".equals(property.getType()))
						cstmt.setDate(pos, (Date) value);
							
					logger_.debug("addWithStoredProcedure : property processed OK "); 
				}
			}
			
			cstmt.executeQuery();
			
			/* get the out parameter */
			if (null != query.getReturnType()) 
				result = cstmt.getObject(1);
			
			connection.commit();
			
			logger_.debug("addWithStoredProcedure : end (" + result + ", " + result.getClass().getName() + ")");
			return result;
		} catch (Exception e) {
			logger_.debug("addWithStoredProcedure : Error(" + e.getMessage() + ")");
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					cstmt.close();
					connection.close();
				}
			} catch (Exception e) { }
		}			
	}
	
	/**
	 * Wrapper method: add with a simple query
	 * or with a stored procedure
	 * @param pobject the object to insert into the database
	 * @return Boolean or Object
	 * @throws PapyrusException
	 */
	public Object add(Object pobject) throws PapyrusException {
		logger_.debug("add : begin (" + pobject + ")");
		Object result = null;
		
		if (null == queriesMap_.get("ADD"))
			result = Boolean.valueOf(addSimple(pobject));
		else
			result = addWithStoredProcedure(pobject);
		
		logger_.debug("add : end (" + result + ")");
		return result;
	}
	
	/*************************/
	/**			UPDATE		**/
	/*************************/
	
	/**
	 * Execute an UPDATE query from the values of an object
	 * @param pobject represents the object to update in the database
	 * @return true if the execution worked, false otherwise
	 * @throws PapyrusException
	 */
	public boolean updateSimple(Object pobject) throws PapyrusException {
		logger_.debug("updateSimple : begin (" + pobject + ")");
		 
		/* variable needed for SQL */ 
		Connection connection = null; 
		PreparedStatement pstmt = null; 
		 
		String query = contructUpdateQuery();
		Collection fields = properties_.values();
		boolean result = true;
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			pstmt = connection.prepareStatement(query);
			int count = 1;
		
			/* loop on each child to fill the prepared statement */
			for (Iterator i = fields.iterator(); i.hasNext(); i.next(), count++) {
				Property property = (Property) i;
				Object value = null;
				
				/* value to insert if it is possible */
				if (null != value) {
					if (value instanceof Integer)
						pstmt.setInt(count, ((Integer) value).intValue());
					
					if (value instanceof Float)
						pstmt.setFloat(count, ((Float) value).floatValue());
					
					if (value instanceof String)
						pstmt.setString(count, (String) value);
						
					if (value instanceof Boolean)
						pstmt.setBoolean(count, ((Boolean) value).booleanValue());
						
					if (value instanceof Date)
						pstmt.setDate(count, (Date) value);
				}
			}			
			result = pstmt.execute();

			logger_.debug("updateSimple : end (" + result + ")");
			return result;
		} catch (Exception e) {
			logger_.debug("updateSimple : Error(" + e.getMessage());
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) { }
		}			
	}
	
	/**
	 * Execute a stored procedure to update object
	 * in the database
	 * @param pobject the object to update
	 * @return a object specified in the xml config
	 * @throws PapyrusException
	 */
	public Object updateWithStoredProcedure(Object pobject) throws PapyrusException {
		logger_.debug("updateWithStoredProcedure : begin (" + pobject + ")");
		
		/* variable needed for SQL */ 
		Connection connection = null; 
		CallableStatement cstmt = null;
		
		Collection fields = properties_.values();
		Query query = (Query) queriesMap_.get("UPDATE");
		Object result = null;
		int offset = 0;
		
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			
			connection.setAutoCommit(false);
			
			logger_.debug("updateWithStoredProcedure : fields size = " + fields.size());
			
			logger_.debug("updateWithStoredProcedure : query = " + query.getStoredProcedure());
			
			cstmt = connection.prepareCall(constructCallQuery(UPDATE_SP_NAME)); //query.getStoredProcedure());
			
			/* register the out parameter */
			if (null != query.getReturnType()) {
				cstmt.registerOutParameter(1, query.getSQLReturnType());
				offset = 1;
			}	
			
			/* loop on each child to fill the prepared statement */
			for (Iterator i = fields.iterator(); i.hasNext();) {
				Property property = (Property) i.next();
				
				logger_.debug("updateWithStoredProcedure : property = " + property);
				
				Object value = null;
				int pos = 0;
				
				/* check where we can insert the value and in which position */
				if (0 != property.getUpdate()) {
					value = Utilities.getObjectData(pobject, property.getName());
					pos = property.getUpdate() + offset;
				}
					
				logger_.debug("updateWithStoredProcedure : pos = " + pos + ", value = " + value + ", class = " + (null != value ? value.getClass().getName() : "NOTHING"));	
					
				/* value to insert if it is possible */
				if (0 != property.getUpdate()) {
					if ("Integer".equals(property.getType()))
						cstmt.setInt(pos, ((Integer) value).intValue());
					
					else if ("Long".equals(property.getType()))
						cstmt.setLong(pos, ((Long) value).longValue());
					
					else if("Short".equals(property.getType()))
						cstmt.setObject(pos, value, Types.SMALLINT);
					
					else if ("Float".equals(property.getType()))
						cstmt.setFloat(pos, ((Float) value).floatValue());
					
					else if ("String".equals(property.getType()))
						cstmt.setObject(pos, value, Types.VARCHAR);
						
					else if ("Boolean".equals(property.getType()))
						cstmt.setBoolean(pos, ((Boolean) value).booleanValue());
						
					else if ("Date".equals(property.getType()))
						cstmt.setDate(pos, (Date) value);
							
					logger_.debug("updateWithStoredProcedure : property processed OK "); 
				}
			}
			
			cstmt.executeQuery();
			
			/* get the out parameter */
			if (null != query.getReturnType()) 
				result = cstmt.getObject(1);
			
			connection.commit();
			
			logger_.debug("updateWithStoredProcedure : end (" + result + ", " + result.getClass().getName() + ")");
			return result;
		} catch (Exception e) {
			logger_.debug("updateWithStoredProcedure : Error(" + e.getMessage() + ")");
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					cstmt.close();
					connection.close();
				}
			} catch (Exception e) { }
		}			
	}
	
	/**
	 * Wrapper method: update with a simple query
	 * or with a stored procedure
	 * @param pobject the object to update into the database
	 * @return Boolean or Object
	 * @throws PapyrusException
	 */
	public Object update(Object pobject) throws PapyrusException {
		logger_.debug("update : begin (" + pobject + ")");
		Object result = null;
		
		if (null == queriesMap_.get("UPDATE"))
			result = Boolean.valueOf(updateSimple(pobject));
		else
			result = updateWithStoredProcedure(pobject);
		
		logger_.debug("update : end (" + result + ")");
		return result;
	}
	
	/*************************/
	/**			DELETE		**/
	/*************************/
	
	/**
	 * Execute an DELETE query from the values of an object
	 * @param pobject represents the object to delete in the database
	 * @return true if the execution worked, false otherwise
	 * @throws PapyrusException
	 */
	public boolean deleteSimple(Object pobject) throws PapyrusException {
		logger_.debug("deleteSimple : begin (" + pobject + ")");
		 
		/* variable needed for SQL */ 
		Connection connection = null; 
		PreparedStatement pstmt = null; 
		 
		String query = contructDeleteQuery();
		boolean result = true;
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			pstmt = connection.prepareStatement(query);
			
			Object value = Utilities.getObjectData(pobject, id_.getName());
			
			/* set the id of the object in the database */
			if (value instanceof Short)
				pstmt.setShort(1, ((Short) value).shortValue());
			
			if (value instanceof Integer)
				pstmt.setInt(1, ((Integer) value).intValue());
					
			if (value instanceof Float)
				pstmt.setFloat(1, ((Float) value).floatValue());
					
			if (value instanceof String)
				pstmt.setString(1, (String) value);
						
			if (value instanceof Boolean)
				pstmt.setBoolean(1, ((Boolean) value).booleanValue());
						
			if (value instanceof Date)
				pstmt.setDate(1, (Date) value);
			
			result = pstmt.execute();

			logger_.debug("deleteSimple : end (" + result + ")");
			return result;
		} catch (Exception e) {
			logger_.debug("deleteSimple : Error(" + e.getMessage());
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) { }
		}			
	}
	
	/**
	 * Execute a stored procedure to delete object
	 * in the database
	 * @param pobject the object to delete
	 * @return a object specified in the xml config
	 * @throws PapyrusException
	 */
	public Object deleteWithStoredProcedure(Object pobject) throws PapyrusException {
		logger_.debug("deleteWithStoredProcedure : begin (" + pobject + ")");
		
		/* variable needed for SQL */ 
		Connection connection = null; 
		CallableStatement cstmt = null;
		
		Collection fields = properties_.values();
		Query query = (Query) queriesMap_.get("DELETE");
		Object result = null;
		int offset = 0;
		
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			
			connection.setAutoCommit(false);
			
			logger_.debug("deleteWithStoredProcedure : fields size = " + fields.size());
			
			logger_.debug("deleteWithStoredProcedure : query = " + query.getStoredProcedure());
			
			cstmt = connection.prepareCall(constructCallQuery(ADD_SP_NAME)); //query.getStoredProcedure());
			
			/* register the out parameter */
			if (null != query.getReturnType()) {
				cstmt.registerOutParameter(1, query.getSQLReturnType());
				offset = 1;
			}	
			
			/* loop on each child to fill the prepared statement */
			for (Iterator i = fields.iterator(); i.hasNext();) {
				Property property = (Property) i.next();
				
				logger_.debug("deleteWithStoredProcedure : property = " + property);
				
				Object value = null;
				int pos = 0;
				
				/* check where we can insert the value and in which position */
				if (0 != property.getDelete()) {
					value = Utilities.getObjectData(pobject, property.getName());
					pos = property.getDelete() + offset;
				}
					
				logger_.debug("deleteWithStoredProcedure : pos = " + pos + ", value = " + value + ", class = " + (null != value ? value.getClass().getName() : "NOTHING"));	
					
				/* value to insert if it is possible */
				if (0 != property.getDelete()) {
					if ("Integer".equals(property.getType()))
						cstmt.setInt(pos, ((Integer) value).intValue());
					
					if ("Long".equals(property.getType()))
						cstmt.setLong(pos, ((Long) value).longValue());
					
					if("Short".equals(property.getType()))
						cstmt.setShort(pos, ((Short) value).shortValue());
					
					if ("Float".equals(property.getType()))
						cstmt.setFloat(pos, ((Float) value).floatValue());
					
					if ("String".equals(property.getType()))
						cstmt.setObject(pos, value, Types.VARCHAR);
						
					if ("Boolean".equals(property.getType()))
						cstmt.setBoolean(pos, ((Boolean) value).booleanValue());
						
					if ("Date".equals(property.getType()))
						cstmt.setDate(pos, (Date) value);
							
					logger_.debug("deleteWithStoredProcedure : property processed OK "); 
				}
			}
			
			cstmt.executeQuery();
			
			/* get the out parameter */
			if (null != query.getReturnType()) 
				result = cstmt.getObject(1);
			
			connection.commit();
			
			logger_.debug("deleteWithStoredProcedure : end (" + result + ", " + result.getClass().getName() + ")");
			return result;
		} catch (Exception e) {
			logger_.debug("deleteWithStoredProcedure : Error(" + e.getMessage() + ")");
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					cstmt.close();
					connection.close();
				}
			} catch (Exception e) { }
		}			
	}
	
	/**
	 * Wrapper method: delete with a simple query
	 * or with a stored procedure
	 * @param pobject the object to delete into the database
	 * @return Boolean or Object
	 * @throws PapyrusException
	 */
	public Object delete(Object pobject) throws PapyrusException {
		logger_.debug("delete : begin (" + pobject + ")");
		Object result = null;
		
		if (null == queriesMap_.get("DELETE"))
			result = Boolean.valueOf(deleteSimple(pobject));
		else
			result = deleteWithStoredProcedure(pobject);
		
		logger_.debug("delete : end (" + result + ")");
		return result;
	}
	
	/**
	 * @param pname the name of the property (object side)
	 * @return the property object
	 */
	public Property getProperty(String pname) { 
		return ((Property) properties_.get(pname));
	}
	
	/**
	 * Retrieve the property from its position
	 * in a stored procedure with the ptype type
	 * @param ptype
	 * @param pposition
	 * @return
	 */
	public Property getProperty(String ptype, int pposition) {
		logger_.debug("getProperty : begin (" + ptype + ", " + pposition + ")");
		Collection fields = properties_.values();
		
		for (Iterator i = fields.iterator(); i.hasNext();) {
			Property property = (Property) i.next();
			
			if (ptype.equals(ADD_SP_NAME)) {
				if (pposition == property.getAdd())
					return property;
			} else
				if (ptype.equals(UPDATE_SP_NAME)) {
					if (pposition == property.getUpdate())
						return property;
				} else
					if (ptype.equals(DELETE_SP_NAME)) {
						if (pposition == property.getDelete())
							return property;
					}
		}
		logger_.debug("getProperty : end (null)"); 
		return null;
	}
	
	/**
	 * @param pname the name of the property (table side)
	 * @return the property object
	 */
	public Property getReverseProperty(String pname) { 
		return ((Property) reverseProperties_.get(pname));
	}
	
	/** @return the name of the class */
	public String getNameClass() { return class_; }
	
	/** @return the name of the table */
	public String getNameTable() { return table_; }
	
	/** @return the name of the view */
	public String getNameView() { return view_; }
	
	/**
	 * 
	 * @param pname
	 * @return
	 */
	public String getQuery(String pname) {
		return (((Query) queriesMap_.get(pname)).getQuery());
	}
	
	/**
	 * @return
	 */
	public HashMap getQueriesMap() {
		return queriesMap_;
	}

	/**
	 * Return a list of properties sorted from its
	 * position in ptype stored procedure
	 * @param ptype
	 * @return a sorted list
	 */
	public LinkedList getPropertiesSortedList(String ptype) {
		logger_.debug("getPropertiesSortedList : begin (" + ptype + ")");
		
		LinkedList result = new LinkedList();
		int position = 1;
		Property property = getProperty(ptype, position);
		
		while (null != property) {
			result.add(property);
			position++;
			property = getProperty(ptype, position);
		}
			
		logger_.debug("getPropertiesSortedList : end (" + result.size() + ")");
		return result;	
	}
}
