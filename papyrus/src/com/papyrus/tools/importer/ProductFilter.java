/*
 * Created on 15 juin 2004
 *
 */
package com.papyrus.tools.importer;

import com.papyrus.data.material.CatalogueItemBean;
import com.papyrus.common.PapyrusException;

import java.sql.Types;

/**
 * @author did
 * 
 * Import Customers from a csv file
 */

public class ProductFilter extends Item {
	
	/** stored procedure */
	private static String ADD_PRODUCT_QUERY = "{ ? = call import_product(CAST(? AS varchar), CAST(? AS varchar), CAST(? AS varchar), CAST(? as varchar), CAST(? AS int2)) }";
		
	/** Column id */
	private final static int REFERENCE_COLUMN = 0;
	private final static int DESIGNATION_COLUMN = 1;
	private final static int BRAND_COLUMN = 2;
	private final static int CATEGORY_COLUMN = 3;
	
	private final static int NB_COLUMNS = 4;
	
	/** default constructor */
	public ProductFilter() throws PapyrusException {
		/* call the constructor of mother class */
		super();
		
		System.out.println("ProductFilter created (" + connection_ + ")");
		
		try {
			/* create the PreparedStatement */
			cstmt_ = connection_.prepareCall(ADD_PRODUCT_QUERY);
			cstmt_.registerOutParameter(1, Types.BIGINT);
			System.out.println("CallableStatement created (" + cstmt_ + ")");
		} catch (Exception e) {
			throw new PapyrusException(e.getMessage());
		}
	}
	
	/**
	 * Parse a line, create a new item with the tokens 
	 * @param pline the line to parse
	 * @return a new object corresponding to the tokens 
	 * parsed from the line
	 */
	public Object parseLine(String pline) throws PapyrusException {
		CatalogueItemBean product = null;
		
		/* split the string */
		String items[] = pline.split(";");
		
		if (NB_COLUMNS == items.length) {
			System.out.println("Product found !!!");
			
			/* nb of columns OK */
			product = new CatalogueItemBean();
			
			product.setReference(items[REFERENCE_COLUMN].trim());
			product.setDesignation(items[DESIGNATION_COLUMN].trim());
			product.setBrand(items[BRAND_COLUMN].trim());
			product.setCategory(items[CATEGORY_COLUMN].trim());
			
			this.addIntoBatch(product);
		}
		return product;
	}

	public void addIntoBatch(Object pobject) throws PapyrusException {
		CatalogueItemBean product = (CatalogueItemBean) pobject;
	
		try {
			/* fill the prepared statement */
			cstmt_.setString(2, product.getReference());
			cstmt_.setString(3, product.getDesignation());
			cstmt_.setString(4, product.getBrand());
			cstmt_.setString(5, product.getCategory());
			cstmt_.setShort(6, (short) 1);
			
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
