/**
 * Papyrus Gestion Commerciale
 * 
 * Created on 8 mai 2004
 *
 * Author: did
 */
package com.papyrus.data.mapping.form;

import com.papyrus.common.PapyrusException;
import com.papyrus.common.Utilities;
import com.papyrus.common.Logger;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author did
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FormMappingObject {

	/**
	 * logger object used to log activity in this object
	 */
	private static Logger logger_ = Logger.getInstance(FormMappingObject.class.getName());
	
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

	/** name of the form */
	private String name_ = null;

	/** form data*/
	private HashMap htmlData_ = null;

	/** Error associated to the fields of the form */
	private HashMap errorFields_ = null;

	/** list of fields */
	private LinkedList fieldsList_ = null;

	/** Default constructor */
	public FormMappingObject() {
		logger_.debug("FormMappingObject : begin (DEFAULT CONSTRUCTOR)");
		
		/* create the HashMap will contain the data of the HTML form */
		htmlData_ = new HashMap(); 
		
		/* create the Hashmap will contain the error for each field (if there is error) */
		errorFields_ = new HashMap();
		
		logger_.debug("FormMappingObject : end");
	}

	/** Create a FormMappingObject */
	public FormMappingObject(String pname, LinkedList pfieldsList) {
		logger_.debug("FormMappingObject : begin (" + pname + ")");
		
		name_ = pname;
		
		/* create the HashMap will contain the data of the HTML form */
		htmlData_ = new HashMap(); 
		
		/* create the Hashmap will contain the error for each field (if there is error) */
		errorFields_ = new HashMap();
		
		/* set the list of fields */
		fieldsList_ = new LinkedList(pfieldsList);
		
		logger_.debug("FormMappingObject : end");
	}
	
	/**
	 * Fill the attributes with data stored into a map
	 * @param pmap
	 */
	public void setData(Map pmap) {
		logger_.debug("setData : begin");
		
		/* check if each element of the fieldsList_ exists in the pmap, check also the type */
		for (int i = 0; i < fieldsList_.size(); i++) {
			Field field = (Field) fieldsList_.get(i);
			Object finalValue = null;
			String value = null;
			 
			logger_.debug("setData : current field = " + field.getLabel() + ", " + field.getName() + ", " + field.getType());
			 
			 /* is there a field which corresponds with one in the form */
			 if (true == pmap.containsKey(field.getName())) {
				logger_.debug("setData : field found");
			 	
				/* verify that the pmap does not contain values under array forms like the http map (with the getParameterMap() method) */ 
				/* if it is an array, then get the first element */
				Object objTmp = pmap.get(field.getName());
				if (objTmp instanceof String[])
					value = (String) ((String[]) objTmp)[0];
				else
					value = (String) objTmp;
			 	
				logger_.debug("setData : value of the field in the form = " + value);
			 	
				/* no entry in the pmap => error message */
				if (null != value && 0 != value.length()) {
					if (null == field.getIgnoredValue() || !field.getIgnoredValue().equals(value)) {
						/* convert the string value into its correct type (Integer, Boolean, ...etc) */
						finalValue = field.getValue(value);
						
						/* if the finalValue is equal to null, it means that it is not a correct value */
						if (null == finalValue) {
							addError(INCORRECT, field.getLabel());
							errorFields_.put(field.getName(), INCORRECT_ERROR_MESSAGE);
							logger_.debug("setData : incorrect value (" + value + ") for the " + field.getName() + " field");
						} 
					} else
						logger_.debug("setData : ignored value (" + value + ") for the " + field.getName() + " field");
				} else {
					/* is the field absolutely required ? */
					if (field.isRequired()) {
						addError(MANDATORY, field.getLabel());
						errorFields_.put(field.getName(), MANDATORY_ERROR_MESSAGE);
					}
				}	
			 } else {
					/* the field is absolutely required ? */
					if (field.isRequired()) {
						addError(MANDATORY, field.getLabel());
						errorFields_.put(field.getName(), MANDATORY_ERROR_MESSAGE);
					}
			 }
			 
			 /* insert the couple field name and value into the hashmap */	
			htmlData_.put(field.getName(), finalValue);
		}
		logger_.debug("setData : end");
	}	
	
	/**
	 * Fill the html data for the form with
	 * the fields values of the object
	 * @param pobject
	 */
	public void setData(Object pobject) {
		logger_.debug("setData : begin (" + pobject.getClass().getName() + ")");
		
		/* loop the field to search object propertie */
		for (int i = 0; i < fieldsList_.size(); i++) {
			Field field = (Field) fieldsList_.get(i);
			
			if (null != field.getObjectField()) {
				logger_.debug("setData : " + field.getObjectField() + " field found"); 
				htmlData_.put(field.getName(), Utilities.getObjectData(pobject, field.getObjectField()));
			}
		}
		
		logger_.debug("setData : end");
	}
	
	public Object createNewObject(Class pclass) throws PapyrusException {
		logger_.debug("createNewObject : begin");
		Object object = null;
		
		try { 
			object = pclass.newInstance(); 
		} catch (IllegalAccessException e) {
			logger_.debug("createNewObject : it is not possible to access to the class " + pclass.getName());
			throw new PapyrusException(e.getMessage()); 
		} catch (InstantiationException e) {
			logger_.debug("createNewObject : it is not possible to instanciate the class " + pclass.getName());
			throw new PapyrusException(e.getMessage()); 
		}
		
		/* loop each field of the form to search a objectName property */
		for (int i = 0; i < fieldsList_.size(); i++) {
			Field field = (Field) fieldsList_.get(i);
		
			/* objectName property found */
			if (null != field.getObjectField()) {
				/* only if value corresponding */
				Object value = htmlData_.get(field.getName());
				
				if (null != value)
					Utilities.setObjectData(object, field.getObjectField(), value);
			} 
		}
		logger_.debug("createNewObject : end");
		return object;
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
	 * Reset the form by setting the attributes to null
	 */
	public void reset() {
		logger_.debug("reset : begin");
		
		/* empty the string attribute */
		errorMessage_ = null;
		
		/* empty all the list */
		htmlData_.clear();
			
		if (null != errorFields_)
			errorFields_.clear();	
			
		logger_.debug("reset : end");
	}
	
	/** @return the name of the form */
	public String getName() { return name_; }

	/**
	 * Answer the value of the field
	 * @param pname
	 * @return an object 
	 */
	public Object getValue(String pname) {
		return (htmlData_.get(pname));
	}

	/**
	 * @return the complete error message
	 */
	public String getErrorMessage() {
		if (null != errorMessage_) 
			return (errorMessage_.toString());
			
		return null; 
	}
	
	/** @return the list of fields */
	public LinkedList getFieldsList() {
		return fieldsList_;
	}
	
	/**
	 * @return the HashMap representing the data posted in the form 
	 */
	public HashMap getHtmlData() {
		return htmlData_;
	}
	
	/**
	 * @return the HashMap containing a possible error message for each field
	 */
	public HashMap getErrorFields() {
		return errorFields_;
	}
	
	public void setErrorFields(String pfield, String perrorMessage) {
		errorFields_.put(pfield, perrorMessage);	
	}
}
