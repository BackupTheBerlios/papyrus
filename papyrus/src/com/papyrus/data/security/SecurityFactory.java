/*
 * Created on 10 mai 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.papyrus.data.security;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.HashMap;

import com.papyrus.common.*;

/**
 * @author did
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SecurityFactory {
	
	/* Const for the different rights of user */
	public static int GUEST_RIGTHS_ID = 1;
	public static int MARKETINGMAN_RIGTHS_ID = 2;
	public static int LEADER_RIGTHS_ID = 3;
	public static int ADMINISTRATOR_RIGTHS_ID = 4;
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(SecurityFactory.class.getName());

	/** singleton */
	private static SecurityFactory instance_ = null;

	/** store all the rights (guest, admin, ...etc) for each domain and action */
	private static HashMap securityMap_ = null;

	/**
	 * Create a SecurityFactory.
	 */
	public SecurityFactory() throws PapyrusException {
		logger_.debug("SecurityFactory : begin");
		
		/* create the securityMap */
		securityMap_ = new HashMap();
		
		logger_.debug("SecurityFactory : end");
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
			is = SecurityFactory.class.getResourceAsStream(pfileName);
			
			/* get the root */
			Document root = db.parse(is);
			
			Node node = (Node) root;
			NodeList domainChildren = ((Node) root).getChildNodes();
			
			/* loop on each child: domain */
			for (int i = 0; i < domainChildren.getLength(); i++) { 
				/* domain scope */				
				Node domainNode = (Node) domainChildren.item(i);
				
				/* if the current node is an element, go on */
				if (Node.ELEMENT_NODE == domainNode.getNodeType()) {
					String domainName = ((Element) domainNode).getAttribute("name");
					logger_.debug("addFile : domain name=" + domainName);
					
					/* Children nodes */
					NodeList actionChildren = ((Node) domainNode).getChildNodes();
			
					/* loop on each child: action */
					for (int j = 0; j < actionChildren.getLength(); j++) {
						/* action scope */				
						Node actionNode = (Node) actionChildren.item(j);
				
						/* if the current node is an element, go on */
						if (Node.ELEMENT_NODE == actionNode.getNodeType()) {
							String actionName = ((Element) actionNode).getAttribute("name");
							logger_.debug("addFile : action name=" + actionName);
							
							ActionSecurity actionSecurity_ = new ActionSecurity(actionNode);
							/* add to the hashmap */
							securityMap_.put(domainName + "|" + actionName, actionSecurity_);
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
			logger_.info("init : SecurityFactory not instancied yet...");
			instance_ = new SecurityFactory();
		}
		
		logger_.debug("init : end");
	}
	
	/** get the unique instance */
	public static SecurityFactory getInstance() {
		if (null == instance_) {
			logger_.info("getInstance : SecurityFactory instance not created !!!");
		}
		return instance_;
	}	
	
	/** answer according to the domain and the action if the user is authorized to access to it */
	public boolean isAuthorized(String pdomain, String paction, short puserRightsId) {
		logger_.debug("isAuthorized : begin (" + pdomain + ", " + paction + ", " + puserRightsId);	
		boolean result = false;
		
		ActionSecurity security = (ActionSecurity) securityMap_.get(pdomain + "|" + paction);
		
		if (null != security)
			result = security.getRights(puserRightsId);
		
		logger_.debug("isAuthorized : end (" + result + ")");
		return result;
	}
	
}
