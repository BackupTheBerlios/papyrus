/**
 * Papyrus Gestion Commerciale
 * 
 * Created on 16 mars 2004
 *
 * Author: did
 */
package com.papyrus.data.form;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.papyrus.common.Logger;

/**
 * @author did
 *
 * This class obliges user to implement setData and validateData methods when they work
 * with a formular (HTML)
 */
public class FormBean {
	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(FormBean.class.getName());
	
	/** sentences to create an error message */
	private static String HEADER_ERROR_MESSAGE = "Les données suivantes ne sont pas correctes: ";
	private static String SEPARATOR_ERROR_MESSAGE = "<br>";
	private static String MANDATORY_ERROR_MESSAGE = " obligatoire";
	private static String INCORRECT_ERROR_MESSAGE = " incorrect";

	/** error message type */
	private static int MANDATORY = 0;
	private static int INCORRECT = 1;

	/** Error Message to display to the client side */	
	private StringBuffer errorMessage_ = null;

	/** Action form */
	private String actionName_ = null;
	
	/** Object form */
	private String objectName_ = null;

	/** form data*/
	private HashMap data_ = null;

	/** Error associated to the fields of the form */
	private HashMap errorFields_ = null;

	/** Attributes list */
	private LinkedList attributesList_ = null;

	/** values list (in the right order) */
	private LinkedList valueList_ = null;

	/**
	 * Default constructor
	 */
	public FormBean() {
		logger_.debug("ItemFormBean : begin");
		
		/* create the HashMap will contain the data of the form */
		data_ = new HashMap(); 
		
		/* create the Hashmap will contain the error for each field (if there is error) */
		errorFields_ = new HashMap();
		
		/* create the values list (for export to SQL) */
		valueList_ = new LinkedList();
		
		logger_.debug("ItemFormBean : end");
	}

	/** 
	 * Constructor
	 * @param pobjectName represents the domain (employee, agency, ...etc)
	 * @param pactionName action when the form will be submitted (LIST, ADD, MODIFY)
	 */
	public FormBean(String pobjectName, String pactionName) {
		logger_.debug("ItemFormBean : begin(" + pobjectName + ", " + pactionName + ")");
		
		/* get an instance of a XMLFormFactory */
		XMLFormFactory formFactory = XMLFormFactory.getInstance();
		
		objectName_ =  pobjectName;
		actionName_ = pactionName;
		
		/* get all attributes */
		attributesList_ = formFactory.getAllFormAttributes(objectName_, actionName_, "html");
		
		/* create the HashMap will contain the data of the form */
		data_ = new HashMap(); 
		
		/* create the values list (for export to SQL) */
		valueList_ = new LinkedList();
		
		logger_.debug("ItemFormBean : end(" + (null != attributesList_ ? attributesList_.size() : -1) + ")");
	}

	/**
	 * Fill the attributes with data stored into a map
	 * @param pmap
	 */
	public void setData(Map pmap) {
		logger_.debug("setData : begin");
		
		/* check if each element of the attributesList_ exists in the pmap, check also the type */
		for (int i = 0; i < attributesList_.size(); i++) {
			Attribute attribute = (Attribute) attributesList_.get(i);
			Object finalValue = null;
			String value = null;
			 
			logger_.debug("setData : current attribute = " + attribute.getFormName() + ", " + attribute.getName() + ", " + attribute.getType());
			 
			 /* is there an attribute which corresponds with one in the form */
			 if (true == pmap.containsKey(attribute.getFormName())) {
			 	logger_.debug("setData : tmp formName = " + pmap.get(attribute.getFormName()));
			 	
			 	/* verify that the pmap does not contain values under array forms like the http map (with the getParameterMap() method) */ 
			 	/* if it is an array, then get the first element */
			 	Object objTmp = pmap.get(attribute.getFormName());
			 	if (objTmp instanceof String[])
			 		value = (String) ((String[]) objTmp)[0];
			 	else
			 		value = (String) objTmp;
			 	
				logger_.debug("setData : value = " + value);
			 	
			 	/* no entry in the pmap => error message */
			 	if (null != value && 0 != value.length()) {
					if (null == attribute.getIgnoredValue() || !attribute.getIgnoredValue().equals(value)) {
			 			/* convert the string value into its correct type (Integer, Boolean, ...etc) */
						finalValue = attribute.getValue(value);
						
						/* if the finalValue is equal to null, it means that it is not a correct value */
						if (null != finalValue)
							valueList_.add(finalValue);
						else {
							addError(INCORRECT, attribute.getName());
							errorFields_.put(attribute.getFormName(), INCORRECT_ERROR_MESSAGE);
						}
					} else
						logger_.debug("setData : ignored value (" + value + ") for the " + attribute.getName() + " attribute");
			 	} else {
					/* the attribute is absolutely required ? */
					if (attribute.isRequired()) {
						addError(MANDATORY, attribute.getName());
						errorFields_.put(attribute.getFormName(), MANDATORY_ERROR_MESSAGE);
					}
		 		}	
			 } else {
			 		/* the attribute is absolutely required ? */
			 		if (attribute.isRequired()) {
			 			addError(MANDATORY, attribute.getName());
						errorFields_.put(attribute.getFormName(), MANDATORY_ERROR_MESSAGE);
			 		}
			 }
			 
			 /* insert the couple attribute name and value into the hashmap */	
			 data_.put(attribute.getName(), finalValue);
		}
		
		logger_.debug("setData : end");
	}
	
	/**
	 * Answer the value of the field
	 * @param pname
	 * @return an object 
	 */
	public Object getValue(String pname) {
		return (data_.get(pname));
	}
	
	/**
	 * Cast the value into a int if it is possible
	 * @return the correct value if all is ok, -1 else
	 */
	public int getIntValue(String pname) {
		/* if the value is null, it means that it is not a correct value 
		 * or it does not exist in the form
		 */
		Object value = data_.get(pname);
		 
		if (null != data_.get(pname)) {
			return ((Integer) value).intValue();	
		} else
			return -1;
	}
	
	/**
	 * Cast the value into a String if it is possible
	 * @return the value if all is ok, null else
	 */
	public String getStringValue(String pname) {
		Object value = data_.get(pname);
		 
		if (null != data_.get(pname)) {
			return ((String) value);	
		} else
			return null;
	}	
	
	/**
	 * Check if the form is correct or not
	 * @return true if the form is correct, and not otherwise.
	 */
	public boolean isDataOk() {
		return (null == errorMessage_ ? true : false);
	}
	
	/**
	 * Construct a complete message error
	 * @param pmessage the error message to add to the final error message
	 */
	public void addError(int ptypeError, String pmessage) {
		if (null == errorMessage_) {
			errorMessage_ = new StringBuffer();
			errorMessage_.append(HEADER_ERROR_MESSAGE);	
		} else
			errorMessage_.append(SEPARATOR_ERROR_MESSAGE);
		
		errorMessage_.append("Le champ " + pmessage);
		
		if (MANDATORY == ptypeError)
			errorMessage_.append(MANDATORY_ERROR_MESSAGE);
			
		if (INCORRECT == ptypeError)
			errorMessage_.append(INCORRECT_ERROR_MESSAGE);
	}
	
	/**
	 * Initialize a new form
	 * @param pobjectName
	 * @param pactionName
	 */
	public void init(String pobjectName, String pactionName) {
		logger_.debug("init : begin(" + pobjectName + ", " + pactionName + ")");
		
		/* get an instance of a XMLFormFactory */
		XMLFormFactory formFactory = XMLFormFactory.getInstance();
		
		objectName_ =  pobjectName;
		actionName_ = pactionName;
		
		/* get all attributes */
		attributesList_ = formFactory.getAllFormAttributes(objectName_, actionName_, "html");
		
		logger_.debug("init : end(" + (null != attributesList_ ? attributesList_.size() : -1) + ")");
	}
	
	/**
	 * Reset the form by setting the attributes to null
	 */
	public void reset() {
		logger_.debug("reset : begin");
		
		/* empty the string attribute */
		errorMessage_ = null;
		objectName_ = null;
		actionName_ = null;
		
		/* empty all the list */
		data_.clear();
		
		if (null != attributesList_)
			attributesList_.clear();
			
		if (null != errorFields_)
			errorFields_.clear();	
			
		valueList_.clear();
		
		logger_.debug("reset : end");
	}
	
	/**
	 * Get the attributes in a LinkedList object.
	 * The attributes are put with a order corresponding to this used 
	 * in the search method of the ItemListBean object.
	 * @return a LinkedList
	 */
	public LinkedList toLinkedList() {
		return valueList_;
	}
	
	/**
	 * @return the complete error message
	 */
	public String getErrorMessage() {
		if (null != errorMessage_) 
			return (errorMessage_.toString());
			
		return null; 
	}
	
	/**
	 * @return the HashMap representing the data posted in the form 
	 */
	public HashMap getData() {
		return data_;
	}
	
	/**
	 * @return the HashMap containing a possible error message for each field
	 */
	public HashMap getErrorFields() {
		return errorFields_;
	}
}

