package com.jn.kickoff.webservice;

public class RESTWebInvokeException extends RuntimeException{



    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6511580316607178981L;

    /**
     * 
     */
    public RESTWebInvokeException()
    {
    }

    /**
     * @param message
     */
    public RESTWebInvokeException(String message)
    
    {
    	super(message);
    	
    }

    /**
     * @param cause
     */
    public RESTWebInvokeException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public RESTWebInvokeException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
