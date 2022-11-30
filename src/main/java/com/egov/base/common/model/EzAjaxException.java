package com.egov.base.common.model;

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

public class EzAjaxException extends EzException {

    /**
     * 
     */
    private static final long serialVersionUID = 7867419743700887725L;

    public EzAjaxException() {
        super();
    }

    public EzAjaxException( String message ) {
        super( message );
    }

    public EzAjaxException( String message, Throwable cause ) {
        super( message, cause );
    }

    public EzAjaxException( Throwable cause ) {
        super( cause );
    }

    protected EzAjaxException(
                                            String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace
    ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }

    public EzAjaxException( int errorCode ) {
        super( errorCode );
    }

    public EzAjaxException( int errorCode, String message ) {
        super( errorCode, message );
    }

    public EzAjaxException( int errorCode, String message, Throwable cause ) {
        super( errorCode, message, cause );
    }

    public EzAjaxException( int errorCode, Throwable cause ) {
        super( errorCode, cause );
    }

    protected EzAjaxException(
                                            int errorCode,
                                            String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace
    ) {
        super( errorCode, message, cause, enableSuppression, writableStackTrace );
    }
}
