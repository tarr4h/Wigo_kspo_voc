package com.kspo.base.common.model;

/**
 * 
* <pre>
* com.wigo.crm.common.model
*	- EzAjaxException.java
* </pre>
*
* @ClassName	: EzAjaxException 
* @Description	: AjaxException
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public class EzLoginException extends EzException {

    /**
     * 
     */
    private static final long serialVersionUID = 7867419743700887725L;

    public EzLoginException() {
        super();
    }

    public EzLoginException( String message ) {
        super( message );
    }

    public EzLoginException( String message, Throwable cause ) {
        super( message, cause );
    }

    public EzLoginException( Throwable cause ) {
        super( cause );
    }

    protected EzLoginException(
                                            String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace
    ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }

    public EzLoginException( int errorCode ) {
        super( errorCode );
    }

    public EzLoginException( int errorCode, String message ) {
        super( errorCode, message );
    }

    public EzLoginException( int errorCode, String message, Throwable cause ) {
        super( errorCode, message, cause );
    }

    public EzLoginException( int errorCode, Throwable cause ) {
        super( errorCode, cause );
    }

    protected EzLoginException(
                                            int errorCode,
                                            String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace
    ) {
        super( errorCode, message, cause, enableSuppression, writableStackTrace );
    }
}
