package com.papyrus.common;



/**
 * Bean used to store an error label that
 * is internationlized through configuration file
 */
public class ErrorBean 
{
    /**
     * Bean name when the bean is stored in request or session
     */
    static public final String ERROR_BEAN   = "errorBean";

    /**
     * Specific label that means that no error is used
     */
    static public final String NO_ERROR     = "NO_ERROR";

    /**
     * Error label
     */
    private String code_;

         
    /**
     * Default constructeur (error label initialized to NO_ERROR)
     */
    public ErrorBean() {
        code_ = NO_ERROR;
    }

    /**
     * @param code the error label in configuration file
     */
    public ErrorBean(String code) {
        code_ = code;
    }

    /**
     * @param error code 
     */
    public void setCode (String code) {
        code_ = code;
    }

    /*
     * @return the error label
     */
    public String getCode() {
        return code_;
    }
    
    /**
     * @return true if there is no error
     */
    public boolean isError() {
        boolean error;
        if(code_.equals(NO_ERROR) )
            error = false;
        else
            error = true;
        
        return error;
    }

    /**
     * @return the object as String
     */
    public String toString()
    {
        return code_;
    }
}

