/* Classe pour gerer les noeuds des arbres (pour l'arborescence du site par exemple) */
/* Auteur: Didier Lafforgue	*/
/* Date: 14/08/2003		*/

package com.papyrus.menu;

import java.util.*;

public class Node 
{
    private long id_;
    private Node father_;
    private Vector listSons_;
    private Object data_;
	
    public	Node(long pid, Node pfather, Object pdata) {
		this.id_ = pid;
		this.father_ = pfather;
		this.listSons_ = new Vector();
		this.data_ = pdata;
    }

    public void addNode(Node pnode) { this.listSons_.add(pnode); }
    public boolean isLeaf() { return ((listSons_.size() == 0) ? true : false); } 

    public long getId() { return (this.id_); }
    public Node getFather() { return (this.father_); }
    public Vector	getSons() { return (this.listSons_); }
    public Object	getData() { return (this.data_); }
}
