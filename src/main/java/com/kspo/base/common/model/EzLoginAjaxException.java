package com.kspo.base.common.model;

/**
 * 
* <pre>
* com.kspo.base.common.model
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

public class EzLoginAjaxException extends EzException {

    /**
     * 
     */
    private static final long serialVersionUID = 7867419743700887725L;

    public EzLoginAjaxException() {
        super();
    }

    public EzLoginAjaxException( String message ) {
        super( message );
    }

    public EzLoginAjaxException( String message, Throwable cause ) {
        super( message, cause );
    }

    public EzLoginAjaxException( Throwable cause ) {
        super( cause );
    }

    protected EzLoginAjaxException(
                                            String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace
    ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }

    public EzLoginAjaxException( int errorCode ) {
        super( errorCode );
    }

    public EzLoginAjaxException( int errorCode, String message ) {
        super( errorCode, message );
    }

    public EzLoginAjaxException( int errorCode, String message, Throwable cause ) {
        super( errorCode, message, cause );
    }

    public EzLoginAjaxException( int errorCode, Throwable cause ) {
        super( errorCode, cause );
    }

    protected EzLoginAjaxException(
                                            int errorCode,
                                            String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace
    ) {
        super( errorCode, message, cause, enableSuppression, writableStackTrace );
    }
}
