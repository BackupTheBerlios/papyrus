/**
 * Papyrus Gestion Commerciale
 * 
 * Created on 8 mai 2004
 *
 * Author: did
 */
package com.papyrus.data.mapping.db;

import org.w3c.dom.*;

import com.papyrus.common.Logger;

/**
 * @author did
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Property {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(DBMappingObject.class.getName());

	/** name of the property (object side) */
	private String name_ = null;
	
	/** name of the column (table side) */
	private String column_ = null;
	
	/** type: String, Integer, Float, Boolean, Date */
	private String type_ = null;
	
	/** optional: place of the property in the "addQuery" stored procedure */
	private int add_ = 0;
	
	/** optional: place of the property in the "updateQuery" stored procedure */
	private int update_ = 0;
		
	/** optional: place of the property in the "deleteQuery" stored procedure */
	private int delete_ = 0;
	
	/**
	 * Create property from a list of attributes of a node
	 * @param pattributesMap attributes of the node
	 */
	public Property(NamedNodeMap pattributesMap) {
		logger_.debug("Property : begin");
		
		/* set the attribute */
		if (null != pattributesMap) {
			/* name */
			Node node = (Node) pattributesMap.getNamedItem("name");
			if (null != node)
				name_ = node.getNodeValue();	
		
			/* column */
			node = (Node) pattributesMap.getNamedItem("column");			
			if (null != node)
				column_ = node.getNodeValue();		
				
			/* type */
			node = (Node) pattributesMap.getNamedItem("type");			
			if (null != node)
				type_ = node.getNodeValue();
				
			/* add */
			node = (Node) pattributesMap.getNamedItem("add");			
			if (null != node)
				add_ = Integer.parseInt(node.getNodeValue());
				
			/* update */
			node = (Node) pattributesMap.getNamedItem("update");			
			if (null != node)
				update_ = Integer.parseInt(node.getNodeValue());
				
			/* delete */
			node = (Node) pattributesMap.getNamedItem("delete");			
			if (null != node)
				delete_ = Integer.parseInt(node.getNodeValue());	
		}
		
		logger_.debug("Property : values = " + name_ + ", " +
						column_ + ", " +
						type_ + ", " +
						add_ + ", " +
						update_ + ", " +
						delete_);
		
		logger_.debug("Property : end");
	}
	
	/** return the name of the property (object side) */
	public String getName() { return name_; }
	
	/** return the name of the column (table side) */
	public String getColumn() { return column_; }
	
	/** return the type of the property: String, Integer, Float, Boolean, Date */
	public String getType() { return type_; }
	
	/** return the place of the property in an add stored procedure */
	public int getAdd() { return add_; }
	
	/** return the place of the property in an update stored procedure */
	public int getUpdate() { return update_; }
	
	/** return the place of the property in a delete stored procedure */
	public int getDelete() { return delete_; }
	
	public String toString() {
		return (name_ + ", " +
				column_ + ", " +
				type_ + ", " +
				add_ + ", " +
				update_ + ", " +
				delete_);
	}
}
