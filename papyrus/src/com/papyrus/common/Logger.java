package com.papyrus.common;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* Wraps a log4j.Category to format the log according to the project specifications.<BR>
* The log methods arguments are those defined in the log message dictionary of the project - XLS sheet.<BR><BR>
*
* For each class, the logger instanciation should be done like this :<BR>
* private static com.papyrus.common.Logger logger = com.papyrus.common.Logger.getInstance( MyClass.class.getName());<BR><BR>
*
* The main method (or the init servlet) module should call the init method after the Log4j has been configurate.<BR>
* (see the method initConfigAndLogs in com.papyrus.common.Config)
*
* The format of each line outputed by this logger is specified in formatLogLine interface
*
* @version $Revision: 1.1 $
*/
public class Logger {

    /** Size of the StringBuffer used for each log method. */
    private static final int STRINGBUFFER_SIZE = 120;

    /** Internal reference to the Log4J Logger. */
    private org.apache.log4j.Logger logger_;

    /** Internal reference to the beginning tag. */
    private String className_;

    /** string identifying blocking state (error level) */
    public static final String ERROR_TYPE_BLOCKING     = "blocking    " ;

    /** string identifying non blocking state (warn level) */
    public static final String ERROR_TYPE_NON_BLOCKING = "non-blocking" ;

    /** string identifying non filled in field */
    private static final String VOID_FIELD = "void" ;

    /** separator between each field */
    private static final String FIELD_SEPARATOR = " | ";

    /** host name */
    private static String hostname_ = null;



    /**
    * Internal constructor.
    */
    private Logger ( String className ) throws NullPointerException {
        if ( className == null ) {
            throw new NullPointerException("Logger : the className cannot be null.");
        }
 
        if (hostname_ == null) {
            try {
                hostname_ = InetAddress.getLocalHost().getHostName();
            }
            catch (java.net.UnknownHostException e) {
                hostname_ = "Unknown";
            }
        }
        
        logger_ = org.apache.log4j.Logger.getLogger(className);
 
        /*
         * Removes the package of the className and concats it to the module tag
         * Example : if className is "com.Papyrus.common.Config", then we just
         * want to keep "Config|", this is used for supervision logs ( error & warnings)
         */
        className_ = className.substring(className.lastIndexOf(".") + 1);
        
    }

    /**
    * Return the Logger instance that maps the className.
    *
    * @param className - exemple : MyClass.class.getName().
    *
    * @return Logger instance.
    *
    * @throws NullPointerException if the className is null
    */
    public static Logger getInstance ( String className ) throws NullPointerException {
        return new Logger (className);
    }

    /**
    * Calls the Log4j method.
    *
    * @param debug log message 
    */
    public final void debug ( String message ) {
        if (logger_.isDebugEnabled())
            logger_.debug( message );
    }

    /**
    * Calls the Log4j method. FOR BENCH ONLY
    *
    * @param info log message 
    */
    public final void info ( String message ) {
        logger_.info( message );
    }


    /**
    * Calls the Log4j method and wraps it in the project format.
    *
    * @param message unique identifier of the log as defined in the project dictionary.
    * @param log message - as defined in the project dictionary.( see SP68001V001 - Log messages.xls)
    */
    public final void warn ( int messageId, String message ) {
        logger_.warn( formatEndLogLine( ERROR_TYPE_NON_BLOCKING, messageId, message) );
    }

    /**
    * Calls the Log4j method and wraps it in the project format.
    *
    * @param message unique identifier of the log as defined in the project dictionary.
    * @param log message - as defined in the project dictionary.( see SP68001V001 - Log messages.xls)
    */
    public final void error ( int messageId, String message ) {
        logger_.error( formatEndLogLine( ERROR_TYPE_BLOCKING, messageId, message) );
    }

    /**
    * Internal method to format the log, for supervision logs (called in warn & error method)
    */
    private final String formatEndLogLine ( String errorType, int messageId, String message  ) {
        StringBuffer strbf = new StringBuffer( STRINGBUFFER_SIZE );

        strbf.append( errorType );
        strbf.append( FIELD_SEPARATOR );

        strbf.append( messageId );
        strbf.append( FIELD_SEPARATOR );

        strbf.append( hostname_ );
        strbf.append( FIELD_SEPARATOR );

        /* void field : client platform */
        strbf.append( VOID_FIELD );
        strbf.append( FIELD_SEPARATOR );

        /* void field : user login */
        strbf.append( VOID_FIELD );
        strbf.append( FIELD_SEPARATOR );

        strbf.append( className_ );
        strbf.append( ":" );
        strbf.append( message );
        return strbf.toString();

    }


    /**
    * method to format the log if log not written by log4j
    * this method is used at initialization time (before log4j is configured), when 
    * error log is then produced by doing a System.out.println, that ends up in 
    * catalina.out file... 
    * 
    * Date au format jj/mm/aaaa hh:mm:ss | KJAVA | void | kjava | void | void | WARN ou ERROR | blocking si niveau = ERROR ou non-blocking si niveau = WARN | code erreur | nom de la machine serveur | void | void | nom de la classe ou du composant: message d'erreur
    */
    public static final String formatCompleteLogLine ( String errorType, int messageId, String message  ) {
        StringBuffer strbf = new StringBuffer( STRINGBUFFER_SIZE );

        SimpleDateFormat sf = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        strbf.append (sf.format (new Date()));
        strbf.append( FIELD_SEPARATOR );

        strbf.append ("Papyrus");
        strbf.append( FIELD_SEPARATOR );

        strbf.append( VOID_FIELD );
        strbf.append( FIELD_SEPARATOR );

        strbf.append( "Papyrus" );
        strbf.append( FIELD_SEPARATOR );

        strbf.append( VOID_FIELD );
        strbf.append( FIELD_SEPARATOR );
        
        strbf.append( VOID_FIELD );
        strbf.append( FIELD_SEPARATOR );

        if (errorType == ERROR_TYPE_BLOCKING) {
            strbf.append( "ERROR" );
        }
        else {
            strbf.append( "WARN " );
        }
        strbf.append( FIELD_SEPARATOR );
        

        strbf.append( errorType );
        strbf.append( FIELD_SEPARATOR );

        strbf.append( messageId );
        strbf.append( FIELD_SEPARATOR );

        strbf.append( hostname_ );
        strbf.append( FIELD_SEPARATOR );

        /* void field : client platform */
        strbf.append( VOID_FIELD );
        strbf.append( FIELD_SEPARATOR );

        /* void field : user login */
        strbf.append( VOID_FIELD );
        strbf.append( FIELD_SEPARATOR );

        strbf.append( message );

        return strbf.toString();

    }
}