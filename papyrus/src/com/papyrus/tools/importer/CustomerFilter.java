/*
 * Created on 15 juin 2004
 *
 */
package com.papyrus.tools.importer;

import com.papyrus.data.management.customer.CustomerBean;
import com.papyrus.common.PapyrusException;

import java.sql.Types;

/**
 * @author did
 * 
 * Import Customers from a csv file
 */

public class CustomerFilter extends Item {
	
	/** stored procedure */
	private static String ADD_CUSTOMER_QUERY = "{ ? = call add_customer(CAST(? AS int8), CAST(? AS int2), CAST(? AS varchar), CAST(? AS varchar), CAST(? AS varchar), CAST(? as varchar), CAST(? as varchar), CAST(? as varchar), CAST(? as varchar), CAST(? as varchar), CAST(? as varchar)) }";
		
	/** Column id */
	private final static int AGENCY_COLUMN = 0;
	private final static int CIVILITY_COLUMN = 1;
	private final static int LAST_NAME_COLUMN = 2;
	private final static int FIRST_NAME_COLUMN = 3;
	private final static int ADDRESS_COLUMN = 4;
	private final static int CITY_COLUMN = 5;
	private final static int POSTAL_CODE_COLUMN = 6;
	private final static int PHONE_COLUMN = 7;
	private final static int CELL_PHONE_COLUMN = 8;
	private final static int FAX_COLUMN = 9;
	private final static int EMAIL_COLUMN = 10;
	
	private final static int NB_COLUMNS = 11;
	
	/** default constructor */
	public CustomerFilter() throws PapyrusException {
		/* call the constructor of mother class */
		super();
		
		System.out.println("CustomerFilter created (" + connection_ + ")");
		
		try {
			/* create the PreparedStatement */
			cstmt_ = connection_.prepareCall(ADD_CUSTOMER_QUERY);
			cstmt_.registerOutParameter(1, Types.BIGINT);
			System.out.println("CallableStatement created (" + cstmt_ + ")");
		} catch (Exception e) {
			throw new PapyrusException(e.getMessage());
		}
	}
	
	/**
	 * Convert old civility id to new one
	 * @param pcivilityId the old civility id
	 * @return the new civility id
	 */
	public short convertCivility(short pcivilityId) {
		short result = 0;
		
		/* Mme */
		result = (0 == pcivilityId) ? 2 : result;
		/* Mlle */
		result = (1 == pcivilityId) ? 3 : result;
		/* M. */
		result = (2 == pcivilityId) ? 1 : result;
		/* M. et Mme */
		result = (3 == pcivilityId) ? 5 : result;
		/* STE */
		result = (4 == pcivilityId) ? 4 : result;
		/* SARL */
		result = (5 == pcivilityId) ? 6 : result;
		/* SCI */
		result = (6 == pcivilityId) ? 7 : result;
		
		return result;
	}
	
	/**
	 * Convert old agency id to new one 
	 * @param pagencyId the old agency id
	 * @return the new agency id
	 */
	public int convertAgency(int pagencyId) {
		int result = 0;
		
		/* Purpan */
		result = (0 == pagencyId ) ? 1 : result;
		
		return result;
	}
	
	/**
	 * Parse a line, create a new customer with the tokens 
	 * @param pline the line to parse
	 * @return a new object corresponding to the tokens 
	 * parsed from the line
	 */
	public Object parseLine(String pline) throws PapyrusException {
		CustomerBean customer = null;
		
		/* split the string */
		//System.out.println("line = " + pline);
		String items[] = pline.split(";");
		
		//System.out.println("length = " + items.length);
		
		if (NB_COLUMNS == items.length) {
			/* nb of columns OK */
			customer = new CustomerBean();
			
			customer.setAgencyId(convertAgency(Integer.parseInt(items[AGENCY_COLUMN].trim())));
			customer.setCivilityId(convertCivility(Short.parseShort(items[CIVILITY_COLUMN].trim())));
			customer.setLastName(items[LAST_NAME_COLUMN].trim());
			customer.setFirstName(items[FIRST_NAME_COLUMN].trim());
			customer.setAddress(items[ADDRESS_COLUMN].trim());
			customer.setPostalCode(items[POSTAL_CODE_COLUMN].trim());
			customer.setCity(items[CITY_COLUMN].trim());
			customer.setPhone(items[PHONE_COLUMN].trim());
			customer.setCellPhone(items[CELL_PHONE_COLUMN].trim());
			customer.setFax(items[FAX_COLUMN].trim());
			customer.setEmail(items[EMAIL_COLUMN].trim());
			
			this.addIntoBatch(customer);
		}
		return customer;
	}

	public void addIntoBatch(Object pobject) throws PapyrusException {
		CustomerBean customer = (CustomerBean) pobject;
	
		try {
			/* fill the prepared statement */
			cstmt_.setInt(2, customer.getAgencyId());
			cstmt_.setShort(3, customer.getCivilityId());
			cstmt_.setString(4, customer.getLastName());
			cstmt_.setString(5, customer.getFirstName());
			cstmt_.setString(6, customer.getAddress());
			cstmt_.setString(7, customer.getCity());
			cstmt_.setString(8, customer.getPostalCode());
			cstmt_.setString(9, customer.getPhone());
			cstmt_.setString(10, customer.getCellPhone());
			cstmt_.setString(11, customer.getFax());
			cstmt_.setString(12, customer.getEmail());
			
			/* add into batch */
			cstmt_.executeQuery();
			//connection_.commit();
			
			cstmt_.clearParameters();
			//cstmt_.addBatch();
			System.out.println("Cstmt = " + cstmt_);
		} catch (Exception e) {
			System.out.println("ERROR : " + e.getMessage());
			throw new PapyrusException(e.getMessage());
		}
	}
}
