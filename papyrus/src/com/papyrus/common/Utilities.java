
package com.papyrus.common;

import java.text.*;
import java.util.*;
import java.util.LinkedList;
import java.lang.reflect.*;

/**
 * Utilities offers a set of utility functions used in all projects
 * @author Didier Lafforgue
 */
public class Utilities {

    /**
     * logger object used to log activity in this object
     */
    private static Logger logger_ = Logger.getInstance(Utilities.class.getName());


    /**
     * @return the current time as a string, in the YYYYMMDD format
     */
    static public String getCurrentTimeYYYYMMDD () {
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
        return dateFormatYYYYMMDD.format (new Date());
    }

    /**
     * @return the date given in parameter as a string, in the YYYYMMDDHHMMSS format
     */
    static public String getStringYYYYMMDDHHMMSS (Date date) {
    	if (null == date)
    		return null;
    		
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format (date);
    }

    /**
     * @return the date given in parameter as a string, in the YYYYMMDDHHMMSS format
     */
    static public String getStringMMDDYYYYHHMMSS (Date date) {
    	if (null == date)
    		return null;
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	return dateFormat.format (date);
    }
    
    /**
     * @return the date given in parameter as a string, in the YYYY-MM-DD_HH_MM format
     */
    static public String getStringFileName (Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm");
        return dateFormat.format (date);
    }

	/**
	* @return the date given in parameter as a string, in the YYYY-MM-DD_HH_MM format
	*/
	static public String getShortStringFileName (Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
		return dateFormat.format (date);
	}


    /**
     * @return the date given in parameter as a Date, in the YYYYMMDDHHMMSS format
     */
    static public Date getDateYYYYMMDDHHMMSS (String dateString) throws ParseException{
        if (null == dateString)
        	return null;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        return dateFormat.parse (dateString);
    }


    /** 
     * Convert a String in java.sql.Date using a French short format date.
     * @param stringDate format DD/MM/YYYY.
     * @return the time given in milliseconds value.
     */
    public static long frenchShortFormatStringToTime(String stringDate) 
    throws PapyrusException
    {
        logger_.debug("frenchShortFormatStringToTime : start " + stringDate);
        try {
            java.util.Date date;
            java.text.DateFormat shortFrenchDateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT, java.util.Locale.FRANCE);
            date = shortFrenchDateFormat.parse(stringDate);
            return date.getTime();
        } catch (Exception e) {
            throw new PapyrusException("Convert.frenchShortFormatStringToTime : exception " + e);
        }
    }


    /** 
     * Convert a String in java.sql.Date using a French short format date.
     * @param  stringDate format DD/MM/YYYY.
     * @return java.sql.Date instance.
     */
    public static java.sql.Date frenchShortFormatStringToSqlDate(String stringDate) 
    throws PapyrusException
    {
        return new java.sql.Date( frenchShortFormatStringToTime(stringDate));
    }

    /**
     * Convert a string representing bytes in hexa
     * to a byte array.
     * @param value the 
     * @return the byte array
     */
    public static byte[] hexaString2byte(String input) 
    throws PapyrusException
    {
        //logger.debug("hexaString2byte : start input = " + input);

        if (input.length() % 2 != 0) {
            throw new PapyrusException("Convert.hexaString2byte : input none odd " + input);
        }
        byte[] output = new byte[input.length() / 2];
        int indexOutput = 0;

        try {
            for (int indexInput = 0; indexInput < input.length(); indexInput = indexInput + 2) {
                Integer dec = Integer.valueOf(input.substring(indexInput, indexInput + 2), 16);
                output[indexOutput] = dec.byteValue();

                indexOutput++;
            }

        } catch (Exception e) {
            throw new PapyrusException("Convert.hexaString2byte : exception " + e);
        }
        return output;
    }

    /**
     * Convert a string representing bytes in hexa
     * to a byte array.
     * @param value the buffer
     * @return the byte array
     */
    public static String byte2hexaString(byte[] input) 
    {
        return byte2hexaString(input, input.length);
    }

    /**
     * Convert a string representing bytes in hexa
     * to a byte array.
     * @param value the buffer
     * @param length length to convert
     * @return the byte array
     */
    public static String byte2hexaString(byte[] input, int length) 
    {
        //logger.debug("byte2hexaString : start, length = " + length);
        StringBuffer output = new StringBuffer();

        for (int indexInput = 0; indexInput < length; indexInput++) {
            Byte inputByte = new Byte(input[indexInput]);

            String inputByteString = Integer.toHexString(inputByte.intValue());
            if (inputByteString.length() == 1) {
                output.append(0);
            }
            output.append(inputByteString);
        }

        //logger.debug("byte2hexaString : end, output = " + output);
        return output.toString().toUpperCase();
    }

    /**
     * @param _value an int value.
     * @return the hexa representation of the provided param.
     */
    public static String int2hexaString(int _value) 
    {
        //logger.debug("int2hexaString : start");                                                                                             
        String output = Integer.toHexString(_value);
        if (output.length() == 1) {
            output = "0".concat(output);
        }
        //logger.debug("byte2hexaString : end, output = " + output);
        return output.toUpperCase();
    }

    /**
     * Verify if string contanins only digits 
     * @param String to be tested 
     * @return true if only digits or false else
     */
    public static boolean isDigitString(String input)
    {
        for (int i = 0; i < input.length(); i++) {
            if (false == Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * Test if the String is made of length digits.
     * @return false if the String is not made of digit or if the length is different from the length parameter.
     */
    public static boolean isDigitString(String input, int length) {
        if (input.length() != length) {
            return false;
        }
        return isDigitString( input );
    }


    /**
     * This methods computes the first millisecond of the week
     * to which belongs a given date
     * @param date the given date for which we want to know the
     *             beginning of the corresponding week
     * @return a date corresponding to monday morning 0 am
     */
    static public Date getBeginingOfTheWeekDate(Date date)
    {               

        SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");


        logger_.debug ("getBeginingOfTheWeekDate : begin (" + logDateFormat.format (date) + ")");

        Calendar calendar = Calendar.getInstance();

        // put calendar to current time
        calendar.setTime(date);

        // the 'week count' begin by a monday
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        // set to last monday at 00:00:00
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date result = calendar.getTime();
        logger_.debug ("getBeginingOfTheWeekDate : end (" + logDateFormat.format (result) + ")");
        return result;
    }


    /**
     * This methods computes the last second of the week
     * to which belongs a given date
     * Sunday is considered as the last day of the week
     * @param date the given date for which we want to know the
     *             beginning of the corresponding week
     * @return a date corresponding to monday morning 0 am
     */
    static public Date getEndOfTheWeekDate(Date date)
    {               


        SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        logger_.debug ("getEndOfTheWeekDate : begin (" + logDateFormat.format (date) + ")");

        Calendar calendar = Calendar.getInstance();

        // the 'week count' begin by a monday
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        // set to last sunday at 23:59:59
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date result = calendar.getTime();
        logger_.debug ("getEndOfTheWeekDate : end (" + logDateFormat.format (result) + ")");
        return result;
    }



    /**
     * This methods computes the first millisecond of the month
     * to which belongs a given date
     * @param pDate the given date for which we want to know the
     *             beginning of the corresponding month
     * @return a date corresponding to 1st morning 0 am
     */
    static public Date getBeginingOfTheMonthDate(Date pDate)
    {               


        SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        logger_.debug ("getBeginingOfTheMonthDate : begin (" + logDateFormat.format (pDate) + ")");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date result = calendar.getTime();
        logger_.debug ("getBeginingOfTheMonthDate : end (" + logDateFormat.format (result) + ")");
        return result;
    }


    /**
     * This methods computes the last millisecond of the month
     * to which belongs a given date
     * @param pDate the given date for which we want to know the
     *             ending of the corresponding month
     * @return a date corresponding to the last second of the month 
     */
    static public Date getEndOfTheMonthDate(Date pDate)
    {               


        SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        logger_.debug ("getEndOfTheMonthDate : begin (" + logDateFormat.format (pDate) + ")");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) );
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date result = calendar.getTime();
        logger_.debug ("getEndOfTheMonthDate : end (" + logDateFormat.format (result) + ")");
        return result;
    }

    /** 
     * Add pMinutes at the pDate
     * @param pDate the date used as reference
     * @return pDate + pMinutes 
     */
    public static java.util.Date addMinutes( int pMinutes, java.util.Date pDate ){

        logger_.debug("addMinutes : begin ( pDate="+pDate+", pMinutes="+pMinutes+" )");

        java.util.Date result = null;
        Calendar calendar = Calendar.getInstance();

        // set calendar to pDate
        calendar.setTime(pDate);

        // get pDate + pSecondes
        calendar.add( Calendar.MINUTE, pMinutes );
        result = calendar.getTime();  

        logger_.debug("addMinutes : end (return="+result+")");
        return result;
    }

    /** 
     * Add pSeconds to the pDate
     * @param pSeconds the seconds to add
     * @param pDate the reference date
     * @return pDate + pSeconds 
     */
    public static java.util.Date addSeconds( int pSeconds, java.util.Date pDate ){

        logger_.debug("addSeconds : begin (pDate="+pDate+", pSeconds="+pSeconds+")");

        java.util.Date result = null;
        Calendar calendar = Calendar.getInstance();

        // set calendar to pDate
        calendar.setTime(pDate);

        // get pDate + pSecondes
        calendar.add( Calendar.SECOND, pSeconds );
        result = calendar.getTime();  

        logger_.debug("addSeconds : end (return="+result+")");
        return result;
    }

    /**
     * To know if the date is a monday or not 
     * @param pDate the date to process
     * @return true if the pDate is a monday otherwise false
     */
    public static boolean isMonday( java.util.Date pDate ){

        logger_.debug("isMonday : begin (pDate="+pDate+")");
        boolean result = false;

        // set calendar to pDate
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pDate);

        // is it a monday ?
        if (  Calendar.MONDAY == calendar.get( Calendar.DAY_OF_WEEK) ) {
            result = true;
        } else {
            result = false;
        }

        logger_.debug("isMonday : end (return="+result+")");
        return result;
    }

    /**
     * To know if the date is the first of a month or not 
     * @param pDate the date to process
     * @return true if the pDate is the first of a month otherwise false
     */
    public static boolean isFirstOfMonth( java.util.Date pDate ){

        logger_.debug("isFirstOfMonth : begin");
        boolean result = false;

        // set calendar to pDate
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pDate);

        // is it the first day of the month ?
        if (  1 == calendar.get( Calendar.DAY_OF_MONTH ) ) {
            result = true;
        } else {
            result = false;
        }

        logger_.debug("isFirstOfMonth : end (return="+result+")");
        return result;
    }


    /** 
     * Add pYears at the pDate
     *
     * @param pYears the number of years to be added
     * @param pDate the date used as reference
     * @return pDate + pYears or null if the pDate is null
     */
    public static java.util.Date addYears(int pYears, java.util.Date pDate) {

        logger_.debug("addYears : begin ( pDate="+pDate+", pYears="+pYears+" )");
        java.util.Date result = null;

        if ( null != pDate ) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(pDate);

            // add the years
            cal.add(Calendar.YEAR, pYears);

            result = cal.getTime();
        }
        logger_.debug("addDays : end (return="+result+")");
        return result;
    }

	/** 
	 * Add pMonths at the pDate
	 *
	 * @param pMonths the number of month to be added
	 * @param pDate the date used as reference
	 * @return pDate + pMonths or null if the pDate is null
	 */
	public static java.util.Date addMonths(int pMonths, java.util.Date pDate) {

		logger_.debug("addMonths : begin ( pDate="+pDate+", pMonths="+pMonths+" )");
		java.util.Date result = null;

		if ( null != pDate ) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(pDate);

			cal.add(Calendar.MONTH, pMonths);

			result = cal.getTime();
		}

		logger_.debug("addMonths : end (return="+result+")");
		return result;
	}

    /** 
     * Add pDays at the pDate
     *
     * @param pDays the numebr of days to be added
     * @param pDate the date used as reference
     * @return pDate + pDays or null if the pDate is null
     */
    public static java.util.Date addDays(int pDays, java.util.Date pDate) {

        logger_.debug("addDays : begin ( pDate="+pDate+", pDays="+pDays+" )");
        java.util.Date result = null;

        if ( null != pDate ) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(pDate);

            cal.add(Calendar.DATE, pDays);

            result = cal.getTime();
        }

        logger_.debug("addDays : end (return="+result+")");
        return result;
    }

    /**
     * This methods computes the first millisecond of the day
     * to which belongs a given date
     * @param date the given date for which we want to know the
     *             beginning of the day
     * @return a date corresponding to the day at midnight
     */
    static public Date getBeginningOfTheDay(java.util.Date pDate)
    {               

        logger_.debug ("getBeginingOfTheDay : begin (" + pDate + ")");

        Calendar calendar = Calendar.getInstance();

        // put calendar to current time
        calendar.setTime(pDate);

        // set time at 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        java.util.Date result = calendar.getTime();
        logger_.debug ("getBeginingOfTheDay : end (" + result + ")");
        return result;
    }

    /**
     * This method computes the last millisecond of the day
     * to which belongs a given date
     * @param date the given date for which we want to know the
     *             ending of the day
     * @return a date corresponding to the day at the last millisecond of the day
     */
    static public Date getEndingOfTheDay(java.util.Date pDate)
    {               

        logger_.debug ("getEndingOfTheDay : begin (" + pDate + ")");

        Calendar calendar = Calendar.getInstance();

        // put calendar to current time
        calendar.setTime(pDate);

        // set time at 23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        java.util.Date result = calendar.getTime();
        logger_.debug ("getEndingOfTheDay : end (" + result + ")");
        return result;
    }
    
    /**
     * This method computes the last millisecond of the month
     * to which belongs a given date
     * @param date the given date for which we want to know the
     *             ending of the month
     * @return a date corresponding to the day at the last millisecond of the month
     */
    static public Date getBeginningOfTheMonth(java.util.Date pDate)
	{               

    	logger_.debug ("getBeginningOfTheMonth : begin (" + pDate + ")");

    	Calendar calendar = Calendar.getInstance();

    	// put calendar to current time
    	calendar.setTime(pDate);

    	// set time at 00:00:00
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	java.util.Date result = calendar.getTime();
    	logger_.debug ("getBeginningOfTheMonth : end (" + result + ")");
    	return result;
    }
    
    /*
     * This method is called to know if the begin date is anterior or not to the end date
     * 
     * We don't use getTimeInMillis() because this method is protected in the jdk 1.3.1 and
     * so unusable with this version.
     * 
     * @param pbeginDate is the begin date
     * @param pendDate is the end date
     * @return true if begin date is anterior to end date
     */
    public static boolean isBeginDateAnteriorToEndDate(Date pbeginDate, Date pendDate) {
    	GregorianCalendar startCalendar = new GregorianCalendar();
    	GregorianCalendar endCalendar = new GregorianCalendar();
    	
    	startCalendar.setTime(pbeginDate);
    	endCalendar.setTime(pendDate);
    	
    	return (startCalendar.getTime().getTime() <= endCalendar.getTime().getTime()); 
    }
    
    /*
     * This method is called to know if the begin date is anterior or not to the end date
     * @param pbeginDate is the begin date
     * @param pendDate is the end date
     * @return true if begin date is anterior to end date
     */
    public static boolean isDatesEqual(Date pdate1, Date pdate2) {
    	GregorianCalendar firstCalendar = new GregorianCalendar();
    	GregorianCalendar secondCalendar = new GregorianCalendar();
    	
    	firstCalendar.setTime(pdate1);
    	secondCalendar.setTime(pdate2);
    	
    	return (firstCalendar.getTime().getTime() == secondCalendar.getTime().getTime()); 
    }

    /**
     * This method adds '0' if necessary to reach 4 digits
     * @param pServiceId to set to 4 digits
     * @return service id on 4 digits if pServiceId is < 0 return null
     */
    static public String setFourDigits( int pServiceId ){

        logger_.debug ("setFourDigits : begin (pServiceId=" + pServiceId + ")");
        String result = null;
        String serviceIdIn = ""+pServiceId;

        if ( null != serviceIdIn && pServiceId >= 0 ) {

            switch ( serviceIdIn.length() ) {
            case 1 :
                // add 3 zeros
                result = "000" + serviceIdIn;
                break;

            case 2 :
                // add 2 zeros
                result = "00" + serviceIdIn;
                break;

            case 3 :
                // add 1 zero
                result = "0" + serviceIdIn;
                break;

            case 4 : // nothing done, length is already = 4 digits
                result = serviceIdIn;
                break;

            default: result = null;
            }

        } else {

            // bad pServiceId
            result = null;
        }

        logger_.debug ("setFourDigits : end (return=" + result + ")");
        return result;
    }
    
    /* Format number with 2 digits in the decimal part */
	public static Number toNumber(String numString, String numFormatPattern)
	throws ParseException {
		DecimalFormat numberFormat = new DecimalFormat();
		Number number = null;
		
		if (numFormatPattern == null)
			numFormatPattern = "####.00";
			
		numberFormat.applyPattern(numFormatPattern);
		number = numberFormat.parse(numString);
		return number;
	}
	
	/* truncate String to a new String with n characters */
	public static String truncateString(String pstr, int n)
	{
		if (null == pstr || n >= pstr.length())
			return pstr;
		return (pstr.substring(0, n) + "...");
	}
	
	/**
	 * Reverse a linked list, 
	 * ie the first element becomes the last of the new list ...
	 * @return a new list
	 */
	public static LinkedList reverseLinkedList(LinkedList plist) {
		LinkedList newList;
		Object obj;
		
		if (null == plist || 0 == plist.size())	
			return plist;
		
		newList = new LinkedList();
		for (int i = plist.size() - 1; i >= 0; i--) {
			obj = (Object) plist.get(i);
			
			newList.add(obj);
		}
		return newList;
	}
	
	/**
	 * Replace a string by another string (all occurrences)
	 * Used to format queries by replacing some key words by values
	 * @param pString to process
	 * @param pToRemove the string to remove into pString
	 * @param pToPut the string to put in the place of pToRemove
	 * @return the string with pToPut instead of pToRemove occurrences or initial string if pToRemove not found
	 */
	public static String replaceString( String pString, String pToRemove, String pToPut){

		logger_.debug("replaceString : begin (pToRemove=" + pToRemove + ", pToPut=" + pToPut + ", pString=" + pString + ")");

		String result = pString;
		int beginIndex = result.lastIndexOf(pToRemove);
		while (-1 != beginIndex) {
			int lastIndex  = beginIndex + pToRemove.length();
			result = result.substring(0,beginIndex) + pToPut + result.substring(lastIndex, result.length());
			beginIndex = result.lastIndexOf(pToRemove);
		}

		logger_.debug("replaceString : end (return=" + result + ")");
		return result;
	}
	
	/**
	 * Set to the corresponding attribute the value
	 * @param pobject
	 * @param pfieldName
	 * @param pvalue
	 */
	public static void setObjectData(Object pobject, String pfieldName, Object pvalue) {
		logger_.debug("setObjectData : begin (" + pfieldName + " | " + pvalue + ")");
		
		/* Warning: the pvalue must not be null */ 
		if (null == pvalue) {
			logger_.debug("setObjectData : end --> pvalue is null");
			return;
		}
		
		char[] methodNameTmp = pfieldName.toCharArray();
		Class[] parameterTypes = new Class[1];
						
		/* Find the correct set method */
		if (97 <= methodNameTmp[0] && 122 >= methodNameTmp[0]) {
			/* the first letter is converted into lowercase */
			methodNameTmp[0] -= (char) 32;
		}
			
		String methodName = "set" + new String(methodNameTmp);
		logger_.debug("setObjectData : method name = " + methodName);
		logger_.debug("setObjectData : value = " + pvalue + " (" + pvalue.getClass().getName() + ")");
		
		/* Search for the type 
		 * WARNING: convert Integer, Float, Boolean into its primitive type
		 */
		parameterTypes[0] = pvalue.getClass();
				
		if (pvalue instanceof Short)
			parameterTypes[0] = short.class;		
				 
		if (pvalue instanceof Integer)
			parameterTypes[0] = int.class;
					
		if (pvalue instanceof Float)
			parameterTypes[0] = float.class;
					
		if (pvalue instanceof Boolean)
			parameterTypes[0] = boolean.class;	
						
		try {
			/* load the method */
			Object[] args = { pvalue };
			Method method = pobject.getClass().getMethod(methodName, parameterTypes);
					
			/* execute it */
			method.invoke(pobject, args);
					
			logger_.debug("setObjectData : call method OK");
					
		} catch (NoSuchMethodException e) { 
			logger_.info("setObjectData : No such method (" + e.getMessage() + ")");
		} catch (InvocationTargetException e) {
			logger_.info("setObjectData : Invocation Target Error (" + e.getMessage() + ")");
		} catch (IllegalAccessException e) {
			logger_.info("setObjectData : Method is inaccessible (" + e.getMessage() + ")");
		}
		
		logger_.debug("setObjectData : end");
	}
	
	public static Object getObjectData(Object pobject, String pfieldName) {
		logger_.debug("getObjectData : begin (" + pfieldName + ")");
		
		Object result = null;
		Class params[] = {};
		Object args[] = {};
		char[] methodNameTmp = pfieldName.toCharArray();
		
		/* Find the correct get method */
		if (97 <= methodNameTmp[0] && 122 >= methodNameTmp[0]) {
			/* the first letter is converted into lowercase */
			methodNameTmp[0] -= (char) 32;
		}
		
		String methodName = "get" + new String(methodNameTmp);
		logger_.debug("getObjectData : method name = " + methodName);
		
		try {
			/* load the method */
			Method method = pobject.getClass().getMethod(methodName, params);
					
			/* execute it */
			result = method.invoke(pobject, args);
					
			logger_.debug("getObjectData : call method OK");
		} catch (NoSuchMethodException e) { 
			logger_.info("setObjectData : No such method (" + e.getMessage() + ")");
		} catch (InvocationTargetException e) {
			logger_.info("setObjectData : Invocation Target Error (" + e.getMessage() + ")");
		} catch (IllegalAccessException e) {
			logger_.info("setObjectData : Method is inaccessible (" + e.getMessage() + ")");
		}
		
		logger_.debug("getObjectData : end (" + result + ")");
		return result;
	}
}




