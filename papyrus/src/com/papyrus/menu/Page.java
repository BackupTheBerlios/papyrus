/* Classe representant les caracteristiques d'une page de l'arborescence du site */
/* Auteur: Didier Lafforgue	*/
/* Date: 14/08/2003		*/

package com.papyrus.menu;

import com.papyrus.common.*;

public class Page {
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(Page.class.getName());
	
    private long id_;
    private String	url_;
    private String	label_;
    private short rights_;

    public Page(long pid, String purl, String plabel, short prights) {
    	logger_.debug("Page : begin (" + pid + ", " + purl + ", " + plabel + ", " + prights + ")");
		this.id_ = pid;
		this.label_ = plabel;
		this.url_ = purl;
		this.rights_ = prights;
		
		logger_.debug("Page : end");
    }
    
    public String toString() {
		return (url_ + " - " + label_ + " - " + rights_);
    }
    
    public String toJavaScript(String item) {
		StringBuffer tmp = new StringBuffer();
	
		tmp.append("var menu" + id_ + " = new WebFXMenuItem('" + label_ + "', '");
		if (null != url_)
			tmp.append(url_ + "', ");
		else
			tmp.append("', ");
		tmp.append("'" + label_ + "');\n");
	
		return (tmp.toString());
    }

    public long getId() { return (this.id_); }
    public String getUrl() { return (this.url_); }
    public String getLabel() { return (this.label_); }
    public int getRights() { return (this.rights_); }
}

