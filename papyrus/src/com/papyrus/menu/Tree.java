/* Classe pour l'arborescence du site */
/* Auteur: Didier Lafforgue	*/
/* Date: 14/08/2003		*/

package com.papyrus.menu;

import java.util.*;
import java.sql.*;

import com.papyrus.common.*;

public class Tree {
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(Tree.class.getName());	
	
    private Node root_;
    private boolean built_ = false;
    
    private static String SQL_QUERY = "SELECT page.id AS id, " +  
							    		"page.label AS label, " + 
							    		"page.url AS url, " +
							    		"page.id_rights AS rights " +
							    		"FROM page " +
										"WHERE page.id_father = ?";
    
    /* charge a partir de la bd, le menu complet */
    public Tree() throws PapyrusException {
    	logger_.debug("Tree : begin");
    	
    	Connection connection = null;
    	PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query;
		
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			
			pstmt = connection.prepareStatement(SQL_QUERY);
			
			pstmt.setInt(1, 0);
			
			rs = pstmt.executeQuery();
		
			// creation du premier noeud 
			if (rs.next()) {
				root_ = new Node(rs.getLong("id"), null, new Page(rs.getLong("id"), 
																  rs.getString("url"),
																  rs.getString("label"),
																  rs.getShort("rights")));
			}
		
			// construction de l arbre
			root_ = buildTree(root_);
		
			built_ = true;
		
		} catch (Exception e) {
			throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) { }
		}	
		logger_.debug("Tree : end");
 	}

    public void searchSons(Node pfathernode) throws PapyrusException {
    	Connection connection = null;
    	PreparedStatement pstmt = null; 
		ResultSet rs = null;
		String query;
	
		try {
			connection = PapyrusDatabasePool.getInstance().getConnection();
			
			pstmt = connection.prepareStatement(SQL_QUERY);
			
			pstmt.setLong(1, pfathernode.getId());
			
			rs = pstmt.executeQuery();
		
			// creation du noeud 
			while (rs.next()) {
				pfathernode.addNode(new Node(rs.getLong("id"), pfathernode, new Page(rs.getLong("id"),
							     													 rs.getString("url"),
							     													 rs.getString("label"),
							     													 rs.getShort("rights"))));
			}
		} catch (Exception e) {
			 throw new PapyrusException(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) { }		
    	}
    }

    public Node buildTree(Node proot) throws PapyrusException {
		logger_.debug("buildTree : begin (" + proot + ")");
		
		Node		current;
		Iterator	i;
	
		// recherche et attache tous les fils du noeud courant
		searchSons(proot);
			
		// parcours de tous ses fils  
		for (i = proot.getSons().iterator(); i.hasNext();) {
			current = (Node) i.next();
			buildTree(current);
	    }
	
		built_ = true;
		logger_.debug("buildTree : end");
		return (proot);
    }

    public String toJavaScript(Node proot, short prights, int pdepth) {
		logger_.debug("toJavaScript : begin (" + prights + ", " + pdepth + ")");
		
		StringBuffer sb = new StringBuffer();
		String item;
		Iterator i;
		Node current;
		Page rootData;
		
		rootData = (Page) proot.getData();
		
		/* first iter: perform the right name of the node */
		item = (proot == root_) ? "menu" : "menu" + proot.getId();
	
		/* construct a submenu if the current node is not the root and not a leaf */
		if (!proot.isLeaf() && proot != root_) {
			/* the first level does not need to have a menu because it will be linked to the menu bar */
			if (1 != pdepth)
				sb.append("var " + item + " = new WebFXMenu;\n");
			/* create the submenu */
			sb.append("var sub" + item + " = new WebFXMenu;\n");
		}
		
		/* loop on each son */
		for (i = proot.getSons().iterator(); i.hasNext();) {
			current = (Node) i.next();
			Page data = (Page) current.getData();
				
			/* check the rights */
			if (prights >= data.getRights()) {
				/* relaunch on the son if it is not a leaf */ 
				if (!current.isLeaf())
				    sb.append(toJavaScript(current, prights, pdepth + 1));
				else {
				    sb.append(data.toJavaScript(item));
				    /* add to the current node only if it is not the root node */
				    if (proot != root_)
				    	sb.append("sub" + item + ".add(menu" + data.getId() + ");\n");
				}
			}
		}
		
		/* link the submenu */
		if (!proot.isLeaf() && proot != root_) {
			if (1 == pdepth)
				sb.append("menuBar.add(new WebFXMenuButton('" + rootData.getLabel() + "', null, '" + rootData.getLabel() + "', sub" + item + "));\n");
			else
				sb.append(item + ".add(new WebFXMenuItem('" + rootData.getLabel() + "', null, '" + rootData.getLabel() + "', sub" + item + "));\n");
		}
		
		logger_.debug("toJavaScript : end");
		return (sb.toString());
    }

    public boolean	isBuilt() { return (this.built_); }
    
    public Node		getRoot() { return (this.root_); }
    public void		setRoot(Node proot) { this.root_ = proot; }
}
