/**
 * Papyrus Gestion Commerciale
 * 
 * Created on 8 mai 2004
 *
 * Author: did
 */
package com.papyrus.data.mapping.form;

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
import java.util.LinkedList;

/**
 * @author did
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FormMappingFactory {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(FormMappingFactory.class.getName());

	/** singleton */
	private static FormMappingFactory instance_ = null;

	/** store all the fields for each form */
	private static HashMap fieldsMap_ = null;

	/**
	 * Create a FormMappingFactory.
	 */
	public FormMappingFactory() throws PapyrusException {
		logger_.debug("FormMappingFactory : begin");
		
		/* create the fieldsMap */
		fieldsMap_ = new HashMap();
		
		logger_.debug("FormMappingFactory : end");
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
			is = FormMappingFactory.class.getResourceAsStream(pfileName);
			
			/* get the root */
			Document root = db.parse(is);
			
			/* fill the objectMap_ attribute */
			Node node = (Node) root;
			NodeList objectChildren = ((Node) root).getChildNodes();
			
			/* loop on each child(in fact, object like Employee or Agency) and store infos in the objectMap */
			for (int i = 0; i < objectChildren.getLength(); i++) { 
				/* object scope */				
				Node objectNode = (Node) objectChildren.item(i);
				
				/* if the current node is an element, go on */
				if (Node.ELEMENT_NODE == objectNode.getNodeType()) {
					/* Children nodes: ACTION */
					NodeList actionChildren = ((Node) objectNode).getChildNodes();
			
					/* name of the object (OBJECT TAG) */
					String object = ((Element) objectNode).getAttribute("name");
					
					logger_.debug("addFile : object name = " + object);
					
					/* loop on each action */
					for (int j = 0; j < actionChildren.getLength(); j++) {
						/* action scope */				
						Node actionNode = (Node) actionChildren.item(j);
							
						/* if the current node is an element, go on */
						if (Node.ELEMENT_NODE == actionNode.getNodeType()) {
							/* Children nodes: FIELDS */
							NodeList fieldsChildren = ((Node) actionNode).getChildNodes();
			
							/* name of the action (OBJECT TAG) */
							String action = ((Element) actionNode).getAttribute("name");	
					
							logger_.debug("addFile : action name = " + action);
						
							/* get the fields of the form */
							LinkedList fieldsList = new LinkedList();
							for (int k = 0; k < fieldsChildren.getLength(); k++) {
								Node fieldNode = (Node) fieldsChildren.item(k);
						
								/* if it is an element, OK */
								if (Node.ELEMENT_NODE == fieldNode.getNodeType()) {
									logger_.debug("addFile : name = " + fieldNode.getNodeName() + ", value = " + fieldNode.getNodeValue());
									Field field = null;
									String type = ((Element) fieldNode).getAttribute("type");
							
									/* Integer or Float attribute */
									if ("Long".equals(type) ||
											"Integer".equals(type) ||
											"Short".equals(type) || 
											"Float".equals(type))
										field = new NumberField(fieldNode.getAttributes());
					
									/* String attribute */
									if ("String".equals(type))
										field = new StringField(fieldNode.getAttributes());
					
									/* Date attribute */
									if ("Date".equals(type))
										field = new DateField(fieldNode.getAttributes());
					
									/* Boolean attribute */
									if ("Boolean".equals(type))
										field = new BooleanField(fieldNode.getAttributes());
		
									if (null != field)
										fieldsList.add(field);
								}
							}
					
							/* add to the hashmap */
							fieldsMap_.put(object + "|" + action, fieldsList);
						}
					}
				}	
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
			logger_.info("init : FormMappingFactory not instancied yet...");
			instance_ = new FormMappingFactory();
		}
		
		logger_.debug("init : end");
	}
	
	/** get the unique instance */
	public static FormMappingFactory getInstance() {
		if (null == instance_) {
			logger_.info("getInstance : FormMappingFactory instance not created !!!");
		}
		return instance_;
	}
	
	/** get a new FormMappingObject corresponding to the name of the object and the name of the action */
	public FormMappingObject getFormMappingObject(String pformName, String pactionName) {
		logger_.debug("getFormMappingObject : begin -> get form (" + pformName + ", " + pactionName + ")");
		
		FormMappingObject result = new FormMappingObject(pformName + "|" + pactionName, ((LinkedList) fieldsMap_.get(pformName + "|" + pactionName)));
		
		logger_.debug("getFormMappingObject : end");
		return (result);
	}
}

