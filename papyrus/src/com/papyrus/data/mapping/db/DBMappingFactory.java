/**
 * Papyrus Gestion Commerciale
 * 
 * Created on 8 mai 2004
 *
 * Author: did
 */
package com.papyrus.data.mapping.db;

import com.papyrus.common.Logger;
import com.papyrus.common.PapyrusException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author did
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DBMappingFactory {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(DBMappingFactory.class.getName());

	/** singleton */
	private static DBMappingFactory instance_ = null;

	/** store all the DBMapping objects */
	private static HashMap classMap_ = null;

	/**
	 * Create a DBMappingFactory.
	 */
	public DBMappingFactory() throws PapyrusException {
		logger_.debug("DBMappingFactory : begin");
		
		/* create the classMap */
		classMap_ = new HashMap();
		
		logger_.debug("DBMappingFactory : end");
	}

	/**
	 * Add a XML file to the DBMappingFactory
	 * @param pfileName the file name containing the data  
	 * @throws PapyrusException
	 */
	public static void addFile(String pfileName) throws PapyrusException {
		logger_.debug("addFile : begin(filename = " + pfileName + ")");
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			java.io.InputStream is = null;
			is = DBMappingFactory.class.getResourceAsStream(pfileName);
			
			/* get the root */
			Document root = db.parse(is);
			
			/* fill the objectMap_ attribute */
			Node node = (Node) root;
			NodeList objectChildren = ((Node) root).getChildNodes();
			
			/* loop on each child(in fact, object like Employee or Agency) and store infos in the objectMap */
			for (int i = 0; i < objectChildren.getLength(); i++) { 
				/* object scope */				
				Node objectNode = (Node) objectChildren.item(i);
				
				/* create the current object */
				DBMappingObject dbMappingObject = new DBMappingObject(objectNode);
						
				/* and add it to the HashMap */
				classMap_.put(dbMappingObject.getNameClass(), dbMappingObject);
			}
		} catch (SAXParseException e) {
			throw new PapyrusException(e.getMessage());
		} catch (SAXException e) {
			throw new PapyrusException(e.getMessage());
		} catch (IOException e) {
			throw new PapyrusException(e.getMessage());
		} catch (ParserConfigurationException e) {
			throw new PapyrusException(e.getMessage());
		}				
		logger_.debug("addFile : end");
	}

	/** init component */
	public static synchronized void init() throws PapyrusException {
		logger_.debug("init : begin");
		
		if (null == instance_) {
			logger_.info("init : DBMappingFactory not instancied yet...");
			instance_ = new DBMappingFactory();
		}
		
		logger_.debug("init : end");
	}
	
	/** get the unique instance */
	public static DBMappingFactory getInstance() {
		if (null == instance_) {
			logger_.info("getInstance : DBMappingFactory instance not created !!!");
		}
		return instance_;
	}
	
	/** get the DBMappingObject corresponding to the class */
	public DBMappingObject getDBMappingObject(String pclassName) {
		return ((DBMappingObject) classMap_.get(pclassName));
	}
}

