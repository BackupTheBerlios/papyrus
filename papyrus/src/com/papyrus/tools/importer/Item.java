/*
 * Created on 14 juin 2004
 *
 */
package com.papyrus.tools.importer;

import com.papyrus.common.*;

import java.sql.*;

/**
 * @author did
 *
 * All the objects which will be imported must be implemented
 * this interface.
 * The methods are used to parse each line to convert the date into an adequat object
 * and to store it in the database 
 */
public abstract class Item {
	
	/** SQL connexion */
	protected Connection connection_ = null;
	
	/** PreparedStatement for the batch */
	protected CallableStatement cstmt_ = null;
	
	/** Default constructor */
	public Item() throws PapyrusDbException {
		System.out.println("Item created");
		try {
			connection_ = PapyrusDatabasePool.getInstance().getConnection();
			connection_.setAutoCommit(false);
		} catch (Exception e) {
			System.out.println("ERROR : msg = " + e.getMessage());
			throw new PapyrusDbException(e.getMessage());
		}
	}
	
	/**
	 * Parse a line, create a new object with the tokens 
	 * @param pline the line to parse
	 * @return a new object corresponding to the tokens 
	 * parsed from the line
	 */
	public abstract Object parseLine(String pline) throws PapyrusException;

	/**
	 * Add the object in the PreparedStatement
	 */
	public abstract void addIntoBatch(Object pobject) throws PapyrusException;

	/**
	 * Save the object previously parsed into the database
	 * Use a PreparedStatement in order to have the addBatch method.
	 * addBatch method is very important to insert all the objects 
	 * in only one sql operation.
	 * @return the new preparedStatement
	 */
	public boolean saveToDB() throws PapyrusException {
		try {
			System.out.println(cstmt_);
			/* execute the batch */
			//int [] result = cstmt_.executeBatch();
			/* commit the inserts */
			connection_.commit();
			
			//System.out.println("nb adds = " + result.length);
		} catch (Exception e) {
			System.out.println("ERROR: " + ((SQLException) e).getNextException().getMessage());
			System.out.println("ERROR: " + e.getMessage());
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection_ != null) {
					cstmt_.close();
					connection_.close();
				}
				return true;
			} catch (Exception e) { return false; }
		}	
	}
}
