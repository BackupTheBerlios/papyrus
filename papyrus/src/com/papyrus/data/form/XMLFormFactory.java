/*
 * Created on 25 mars 2004
 *
 */

/**
 * @author dlafforg
 *
 * Parse a FormDescriptor file in order to create a map of attributes.
 * Attributes properties:
 * 	- name
 *  - type (String, Boolean, Integer)
 * 	- required (true or false)
 * 	- ignoredValue (optionnal)
 */

package com.papyrus.data.form;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import com.papyrus.common.*;

public class XMLFormFactory {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(XMLFormFactory.class.getName());

	/** attribute to avoid searching from the root a specific object */
	private static HashMap objectMap_ = null;
	
	/** root document */
	private Document root_ = null;
	
	/** singleton */
	private static XMLFormFactory instance_ = null;
	
	/** constructor */
	public XMLFormFactory() throws PapyrusException {
		logger_.debug("XMLFormFactory : begin");
		
		/* new instance of the objectMap */
		objectMap_ = new HashMap();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			java.io.InputStream is = null;
			is = XMLFormFactory.class.getResourceAsStream("/properties/FormDescriptor.xml");
			
			/* get the root */
			root_ = db.parse(is);
			
			/* fill the objectMap_ attribute */
			Node node = (Node) root_;
			NodeList objectChildren = ((Node) root_).getChildNodes();
			
			/* loop on each child(in fact, object like Employee or Agency) and store infos in the objectMap */
			for (int i = 0; i < objectChildren.getLength(); i++) { 
				/* object scope */				
				Node objectNode = (Node) objectChildren.item(i);
				
				/* if the current node is an element, go on */
				if (Node.ELEMENT_NODE == objectNode.getNodeType()) {
					String objectName = ((Element) objectNode).getAttribute("name");
				
					logger_.debug("XMLFormFactory : object = " + objectName);
				
					NodeList queryChildren = ((Node) objectNode).getChildNodes();
					/* loop on each child */
					for (int j = 0; j < queryChildren.getLength(); j++) {
						/* action scope */				
						Node queryNode = (Node) queryChildren.item(j);
						
						/* it is an element node, so add to the hashMap */
						if (Node.ELEMENT_NODE == queryNode.getNodeType()) {
							objectMap_.put(objectName + "+" + ((Element) queryNode).getAttribute("name"), queryNode);
							logger_.debug("XMLFormFactory : couple found(" + objectName + "+" + ((Element) queryNode).getAttribute("name") + ", " + queryNode.hashCode() + ")");
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
		
		logger_.debug("XMLFormFactory : end");
	}
	
	/** init component */
	public static synchronized void init() throws PapyrusException {
		logger_.debug("init : begin");
		
		if (null == instance_) {
			logger_.info("init : XMLFormFactory not instancied yet...");
			instance_ = new XMLFormFactory();
		}
		
		logger_.debug("init : end");
	}
	
	/** get the unique instance */
	public static XMLFormFactory getInstance() {
		if (null == instance_) {
			logger_.info("getInstance : XMLFormFactory instance not created !!!");
		}
		return instance_;
	}
	
	public LinkedList getAllFormAttributes(String pobject, String paction, String pformType) {
		logger_.debug("getAllFormAttributes : begin");
		
		if (null == objectMap_)
			return null;

		LinkedList result = new LinkedList();
		
		/* go to the correct query node */
		Node currentNode = (Node) objectMap_.get(pobject + "+" + paction);
		
		/* loop attributes */
		NodeList attributeNodeList = currentNode.getChildNodes();
		
		logger_.debug("getAllFormAttributes : currentNode = " + currentNode.hashCode() + ", nb child = " + attributeNodeList.getLength());
		
		/* attributes */
		for (int i = 0; i < attributeNodeList.getLength(); i++) {
			Attribute attribute = null;
			String type = null;
			
			Node attributeNode = (Node) attributeNodeList.item(i);
			
			/* if the current node is an element, go on */
			if (Node.ELEMENT_NODE == attributeNode.getNodeType()) {
				logger_.debug("getAllFormAttributes : attributeNode = " + attributeNode);
			
				/* Get the properties of the current attribute */
				NamedNodeMap attributesMap = attributeNode.getAttributes();
				if (null != attributesMap) 
					type = ((Node) attributesMap.getNamedItem("type")).getNodeValue();
					
				/* search for the correct formType */
				Element element = (Element) attributeNode;
				NodeList formNodeList = element.getElementsByTagName(pformType);
				
				logger_.debug("getAllFormAttributes : formNodeList = " + formNodeList.getLength());
				
				/* only one element must be returned: either HTML or SQL for example */
				if (1 == formNodeList.getLength() && null != type) {
					/* Integer or Float attribute */
					if ("Integer".equals(type) || "Float".equals(type))
					 	attribute = new NumberAttribute(attributesMap, ((Node) formNodeList.item(0)).getFirstChild().getNodeValue());
					
					/* String attribute */
					if ("String".equals(type))
						attribute = new StringAttribute(attributesMap, ((Node) formNodeList.item(0)).getFirstChild().getNodeValue());
					
					/* Date attribute */
					if ("Date".equals(type))
						attribute = new DateAttribute(attributesMap, ((Node) formNodeList.item(0)).getFirstChild().getNodeValue());
					
					/* Boolean attribute */
					if ("Boolean".equals(type))
						attribute = new BooleanAttribute(attributesMap, ((Node) formNodeList.item(0)).getFirstChild().getNodeValue());
					
					result.add(attribute);
					logger_.debug("getAllFormAttributes : attribute found(" + attribute + ")");
				} 
			}
		}
			
		logger_.debug("getAllFormAttributes : end");
		return result;
	}
}
