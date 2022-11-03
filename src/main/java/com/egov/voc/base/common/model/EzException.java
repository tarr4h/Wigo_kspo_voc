package com.egov.voc.base.common.model;

/**
 * 
* <pre>
* com.wigo.crm.common.model
*	- EzException.java
* </pre>
*
* @ClassName	: EzException 
* @Description	: Exception
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public class EzException extends RuntimeException {

    /**
     * 
     */
    private int errorCode = 0;
    private static final long serialVersionUID = -543310737303969945L;

    public EzException() {
        super();
    }

    public EzException( String message ) {
        super( message );
    }

    public EzException( String message, Throwable cause ) {
        super( message, cause );
    }

    public EzException( Throwable cause ) {
        super( cause );
    }

    protected EzException(
                                            String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace
    ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }

    public EzException(int errorCode) {
        super();
        setErrorCode( errorCode );
    }

    public EzException(int errorCode, String message ) {
        super( message );
        setErrorCode( errorCode );
    }

    public EzException(int errorCode, String message, Throwable cause ) {
        super( message, cause );
        setErrorCode( errorCode );
    }

    public EzException(int errorCode, Throwable cause ) {
        super( cause );
        setErrorCode( errorCode );
    }

    protected EzException(int errorCode,
                                            String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace
    ) {
        super( message, cause, enableSuppression, writableStackTrace );
        setErrorCode( errorCode );
    }
    
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode( int errorCode ) {
        this.errorCode = errorCode;
    }
    
    public EzMap toMap() {
        EzMap map = new EzMap();
        map.setInt( "errorCode", errorCode );
        map.setString( "errorMsg", getMessage() );
        return map;
    }
}
