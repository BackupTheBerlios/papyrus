/*
 * Created on 10 mai 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.papyrus.data.security;

import org.w3c.dom.*;

import com.papyrus.common.*;
/**
 * @author did
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ActionSecurity {
	
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(ActionSecurity.class.getName());

	/** rights for the guest */
	private boolean guestRights_ = false;
	
	/** rights for the makerting man */
	private boolean marketingManRights_ = false;

	/** rights for the leader of agency */
	private boolean leaderRights_ = false;
	
	/** rights for the admin */
	private boolean administratorRights_ = false;
	
	/** 
	 * Default constructor
	 * @param pnode
	 */
	public ActionSecurity(Node pnode) {
		logger_.debug("ActionSecurity : begin");
	
		NodeList children = pnode.getChildNodes();
	
		for (int i = 0; i < children.getLength(); i++) {
			Node rightsNode = children.item(i);
			
			/* if the current node is an element, go on */
			if (Node.ELEMENT_NODE == rightsNode.getNodeType()) {
				String nodeName = rightsNode.getNodeName();
				Object value = rightsNode.getFirstChild().getNodeValue();
			
				logger_.debug("ActionSecurity : name of the node = " + nodeName + " (" + value + ")");
			
				if ("guest".equals(nodeName))
					guestRights_ = Boolean.getBoolean((String) value);
				
				if ("marketingMan".equals(nodeName))
					marketingManRights_ = Boolean.getBoolean((String) value);
				
				if ("leader".equals(nodeName))
					leaderRights_ = Boolean.getBoolean((String) value);
				
				if ("administrator".equals(nodeName))
					administratorRights_ = Boolean.getBoolean((String) value);
			}
		}
	
		logger_.debug("ActionSecurity : end");	
	}

	/** Answer the rights of a guest (rightsId = 1) for this action */
	public boolean getGuestRights() { return guestRights_; }
	
	/** Answer the rights of a marketing man (rightsId = 2) for this action */
	public boolean getMarketingManRights() { return marketingManRights_; }

	/** Answer the rights of a leader (rightsId = 3) for this action */
	public boolean getLeaderRights() { return leaderRights_; }
	
	/** Answer the rights of a administrator (rightsId = 4) for this action */
	public boolean getAdministratorRights() { return administratorRights_; }
	
	/** Answer the rights according to the id of rights(cf database) */
	public boolean getRights(short puserRightsId) {
		boolean result = false;
		
		switch (puserRightsId) {
			case 0 :
				result = guestRights_;
				break;
			case 1 :
				result = marketingManRights_;
				break;
			case 3 :
				result = leaderRights_;
				break;
			case 4 :
				result = administratorRights_;
				break;
			default :
				break;
		}
		return result;
	}
}
