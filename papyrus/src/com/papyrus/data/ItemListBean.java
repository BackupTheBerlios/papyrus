/*
 * Papyrus Gestion Commerciale
 * 
 * Created on Feb 28, 2004
 *
 * Author: did
  */
  
package com.papyrus.data;

import java.sql.*;
import java.util.LinkedList;
import com.sun.rowset.*;

import com.papyrus.common.*;
import com.papyrus.data.mapping.db.DBMappingFactory;
import com.papyrus.data.mapping.db.DBMappingObject;
import com.papyrus.data.mapping.form.FormMappingObject;
import com.papyrus.data.mapping.form.Field;

/**
 * @author did
 *
 * This class permits to store ItemBean (like AgencyBean for example) 
 * in order to create a multipage navigation system. ItemListBean uses any
 * bean that extends the ItemBean class.
 */
public class ItemListBean {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(ItemListBean.class.getName());

	/** 
	 * List of ItemBean object 
	 */
	private LinkedList list_ = null;
	
	/** 
	 * class of a basic element of the list 
	 */
    private Class itemClass_;
    
    /** DBMappingObject used to create object from a resultset */
    private DBMappingObject dbMappingObject_ = null;
    
	/** 
	 * Rowset object used to create the navigation system 
	 */
	private CachedRowSetImpl rowSet_ = null;
	
	/**
	 * Number of lines per page.
	 */
	private int maxLinesPerPage_ = 10;
	
	/**
	 * Index of the current results page 
	 */
	private int currentPageIndex_ = 1;
	
	/**
	 * Number of results for the search
	 */
	private int nbResults_ = 0;
	
	/**
	 * next page displayable ??? 
	 */
	private boolean isNextPagesExists_ = true;
	
	/**
	 * next page displayable ??? 
	 */
	private boolean isPreviousPagesExists_ = false;
	
	/** Default constructor */
	public ItemListBean() {
		logger_.debug("ItemListBean : begin (DEFAULT CONSTRUCTOR)");
		
		/* Do nothing */
		
		logger_.debug("ItemListBean : end");
	}
		
	/** Constructor */
	public ItemListBean(Class pitemClass) {
		logger_.debug("ItemListBean : begin(" + pitemClass.getName() + ")");
		
		itemClass_ = pitemClass;
		
		dbMappingObject_ = (DBMappingFactory.getInstance()).getDBMappingObject(itemClass_.getName());
		
		/**
		 * TODO: find the maxLinesPerPage value in a properties file
		 */
		
		logger_.debug("ItemListBean : end");
	}
	
	/**
	 * Construct a sql query from a form and its xml descriptor
	 * @param form
	 * @return a complete sql query
	 */
	public static String constructSearchQuery(FormMappingObject pform, String pqueryTemplate) {
		logger_.debug("constructSearchQuery : begin");
		
		StringBuffer query = new StringBuffer();
		
		/* get the SQL attributes list */
		LinkedList fieldsList = pform.getFieldsList();
		
		for (int i = 0; i < fieldsList.size(); i++) {
			Field field = (Field) fieldsList.get(i);
			
			/* to be accepted in the sql query, the attribute must be not null and different from the ignored value */
			/* in the example of a agency search, if id_agency == 0 == ignoredValue, then no criter */
			if (null != pform.getValue(field.getName())) {
				logger_.debug("constructSearchQuery : field value in form = " + pform.getValue(field.getName()) + ", ignored value = " + field.getIgnoredValue());
				if (null == field.getIgnoredValue() || !field.getIgnoredValue().equals(pform.getValue(field.getName()).toString())) {
					/* for the search, in the case of a string parameter, we use ~* operator */
					if ("String".equals(field.getType()))
						query.append("AND (\"" + field.getSqlName() + "\" ~* ?) ");			
					else
						query.append("AND (\"" + field.getSqlName() + "\" = ?) ");
				}
			}
		}
		
		if (query.length() != 0) {
			/* remove the AND keyword from the beginning */
			query.delete(0, 4);
			/* insert the WHERE clause */
			query.insert(0, "WHERE ");
		}
		logger_.debug("constructSearchQuery : end(" + query + ")");
		return(Utilities.replaceString(pqueryTemplate, "<CONDITIONS>", query.toString()));
	}	

	/**
	 * Create the rowset from a from corresponding to the query 
	 * @param pform
	 * @param pqueryTemplate
	 * @throws PapyrusException
	 */
	public void search(FormMappingObject pform, String pqueryTemplate) throws PapyrusException {
		logger_.debug("search : begin");
		
		/* variable needed for SQL */ 
		Connection connection = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			pstmt = connection.prepareStatement(constructSearchQuery(pform, pqueryTemplate), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			//pstmt = connection.prepareStatement("SELECT * FROM view_employee WHERE (\"agencyId\" = ?)  ORDER BY \"agencyId\", \"lastName\"");
			int index = 1;
			
			/* set all the parameters in the prepared statement 
			 * WARNING: the index of the array is different from the prepareStatement one 
			 */
			for (int i = 0; i < pform.getFieldsList().size(); i++) {
				Field field = (Field) pform.getFieldsList().get(i);
				Object value = pform.getValue(field.getName());
				
				if (null != field.getSqlName() && null != value) {
					logger_.debug("search : className=" + value.getClass().getName() + ", value=" + value + ", index=" + index);
					
					/* String Object */
					if (value instanceof java.lang.String)
						pstmt.setString(index, (String) value);
						
					/* Short */
					if (value instanceof java.lang.Short)
						pstmt.setShort(index, ((Short) value).shortValue());	
						
					/* Integer */
					if (value instanceof java.lang.Integer)
						pstmt.setInt(index, ((Integer) value).intValue());
						
					/* Long */
					if (value instanceof java.lang.Long)
						pstmt.setLong(index, ((Long) value).longValue());
						
					/* Float */
					if (value instanceof java.lang.Float)
						pstmt.setFloat(index, ((Float) value).floatValue());
						
					/* Boolean */
					if (value instanceof java.lang.Boolean)
						pstmt.setBoolean(index, ((Boolean) value).booleanValue());
						
					/* Date */
					if (value instanceof java.util.Date)
						pstmt.setDate(index, new java.sql.Date(((java.util.Date) value).getTime()));
					
					index++;	
				}
			}
				
			/* execute the query */
			rs = pstmt.executeQuery();
			logger_.debug("search : ResultSet created (" + rs + ")");
		
			/* create the RowSet object */
			rowSet_ = new CachedRowSetImpl();
			logger_.debug("search : RowSet created (" + rowSet_ + ")");
					
			rowSet_.populate(rs);
			logger_.debug("search : RowSet populated OK");
					
			nbResults_ =  rowSet_.size();		
		} catch (Exception e) {
			logger_.debug("search : Error(" + e.getMessage() + ")");
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					rs.close();
					connection.close();
				}
			} catch (Exception e) { }
		}	
		logger_.debug("search : end(" + nbResults_ + ")");
	}

	/**
	 * @DEPRECATED
	 * @param psqlQuery
	 * @param pparameters
	 * @throws PapyrusException
	 */
	public void search(String psqlQuery, LinkedList pparameters) throws PapyrusException {
		logger_.debug("search : begin");
		
		/* variable needed for SQL */ 
		Connection connection = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
					connection = PapyrusDatabasePool.getInstance().getConnection();
					pstmt = connection.prepareStatement(psqlQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
					/* set all the parameters in the prepared statement 
					 * WARNING: the index of the array is different from the prepareStatement one 
					 */
					for (int i = 0; i < pparameters.size(); i++) {
						Object param = pparameters.get(i);
						
						logger_.debug("search : className=" + param.getClass().getName() + ", value=" + param);
						
						/* String Object */
						if (param instanceof Short)
							pstmt.setShort(i + 1, ((Short) param).shortValue());
						
						/* String Object */
						if (param instanceof String)
							pstmt.setString(i + 1, (String) param);
						
						/* Integer */
						if (param instanceof Integer)
							pstmt.setInt(i + 1, ((Integer) param).intValue());
							
						/* Long */
						if (param instanceof Long)
							pstmt.setLong(i + 1, ((Long) param).longValue());
							
						/* Float */
						if (param instanceof Float)
							pstmt.setFloat(i + 1, ((Float) param).floatValue());	
							
						/* Boolean */
						if (param instanceof Boolean)
							pstmt.setBoolean(i + 1, ((Boolean) param).booleanValue());	
							
						/* Date */
						if (param instanceof Date)
							pstmt.setDate(i + 1, (Date) param);
					}
				
					/* execute the query */
					rs = pstmt.executeQuery();
					logger_.debug("search : ResultSet created (" + rs + ")");
		
					/* create the RowSet object */
					rowSet_ = new CachedRowSetImpl();
					logger_.debug("search : RowSet created (" + rowSet_ + ")");
					
					rowSet_.populate(rs);
					logger_.debug("search : RowSet populated OK");
					
					nbResults_ =  rowSet_.size();		
		} catch (Exception e) {
			logger_.debug("search : Error(" + e.getMessage() + ")");
			throw new PapyrusException(e.getMessage());
		} finally {
				try {
					if (connection != null) {
						rs.close();
						connection.close();
					}
				} catch (Exception e) { }
		}	
		
		logger_.debug("search : end(" + nbResults_ + ")");
	}	

	
	/**
	 * Go to the page indicated by the n index
	 * @throws PapyrusException
	 */
	public boolean gotoPage(int ppageIndex) throws PapyrusException {
		logger_.debug("gotoPage : begin(" + ppageIndex + ")");
		
		int n = 1; 
		
		/* only positive values are accepted and rowset with at least one result */
		if (0 >= ppageIndex || 0 == nbResults_)
			return false;
				
		ppageIndex = (ppageIndex > getMaxNbPages()) ? getMaxNbPages() : ppageIndex;	
				
		/* perform the row index */
		n = ((ppageIndex - 1) * maxLinesPerPage_) + 1;
		
		/* go to this row */
		try {
			logger_.debug("gotoPage : n = " + n);
		
			rowSet_.absolute(n);	
			
			/* update the current attributes (nextPageExists, ...etc) */
			isNextPagesExists_ = (n + maxLinesPerPage_ <= nbResults_) ? true : false;
			
			isPreviousPagesExists_ = (1 != ppageIndex) ? true : false;	
		
			currentPageIndex_ = ppageIndex;
				
		} catch (SQLException e) {
			logger_.debug("gotoPage : ERROR(" + e.getMessage() + ")");
			throw new PapyrusException(e.getMessage()); 
		}
		
		logger_.debug("gotoPage : end");
		return true;	
	}
	
	
	public void loadResultsFromPage(int ppageIndex) throws PapyrusException {
		logger_.debug("loadResultsFromPage : begin(page Index = " + ppageIndex + ")");
		
		if (null == list_)
			list_ = new LinkedList();
		else
			list_.clear();
			
		int count = 0;
		
		try {
			/* go to the indicated page */
			if (true == gotoPage(ppageIndex)) {
				/* insert the next maxLinesPerPage records if possible */ 
				do {
					//ItemBean itemObject = (ItemBean) itemClass_.newInstance();
					//itemObject.setData(rowSet_);
			
					list_.addLast(dbMappingObject_.createNewObject(rowSet_));
			
					count++;
				} while (count < maxLinesPerPage_ && rowSet_.next());
			}
		} catch (Exception e) {
			throw new PapyrusException(e.getMessage());
		}
		
		logger_.debug("loadResultsFromPage : end(nb results = " + list_.size() + ")");
	}
	
	/** 
	 * Method created to be use with taglib
	 * @return true if going to a previous page is possible
	 * @throws PapyrusException
	 */
	public boolean getPreviousPageOK() throws PapyrusException {
		return (isPreviousPagesExists_); 
	}
	
	/** 
	 * Method created to be use with taglib
	 * @return true if going to a next page is possible
	 * @throws PapyrusException
	 */
	public boolean getNextPageOK() throws PapyrusException {
		logger_.debug("getNextPageOK : begin(" + isNextPagesExists_ + ")");
		
		return (isNextPagesExists_);
	}	

	/**
	 * Determinate if a multipage system can be used
	 * @return true if it is possible
	 */
	public boolean isMultiPageOK() throws PapyrusException {
		return (isPreviousPagesExists_ || isNextPagesExists_);
	}	
	
	/**
	 * Avoid to do an ItemListBean.getList().get(i) 
	 * @return the object located in the i position in the LinkedList
	 */
	public Object getElement(int pindex) {
		return (list_.get(pindex));
	}
	
	/**
	 * Answer the list of element
	 * @return a LinkedList 
	 */
	public LinkedList getList() {
		return (list_);
	}
	
	/**
	 * Get the total number of lines returned by the sql call
	 * @return total number of items
	 */
	public int getNbResults() {
		return nbResults_;
	}
	
	/**
	 * Get the current page index
	 * @return an integer corresponding to the current page index
	 */
	public int getCurrentPageIndex() {
		return currentPageIndex_;
	}
	
	/**
	 * Get the maximum number of pages
	 * @return the maximum number of pages
	 */
	public int getMaxNbPages() {
		int maxNbPages = nbResults_ / maxLinesPerPage_ + ((0 != maxLinesPerPage_ % nbResults_) ? 1 : 0);
		
		return (0 != maxNbPages ? maxNbPages : 1);
	}
	
	/**
	 * Avoid to do an ItemListBean.getList().size()
	 * @return the size of the LinkedList
	 */
	public int size() {
		/* if the list is null, then we consider that it contains no element */
		if (null == list_)
			return 0;
			
		return (list_.size());
	}
	
}
