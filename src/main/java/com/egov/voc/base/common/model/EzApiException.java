package com.egov.voc.base.common.model;

/**
 * 
* <pre>
* com.wigo.crm.common.model
*	- EzAjaxException.java
* </pre>
*
* @ClassName	: EzApiException 
* @Description	: EzApiException
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public class EzApiException extends EzAjaxException {

    private static final long serialVersionUID = 7867419743700887725L;
    private String apiErrorCode;
    public EzApiException() {
        super();
    }

    public EzApiException( String message ) {
        super( message );
    }

    public EzApiException( String message, Throwable cause ) {
        super( message, cause );
    }

    public EzApiException( Throwable cause ) {
        super( cause );
    }
    public EzApiException(String errorCode, String message, Throwable cause ) {
        super( message, cause );
        setApiErrorCode(errorCode);
    }
	protected EzApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super( message, cause, enableSuppression, writableStackTrace );
    }


    public EzApiException( String errorCode, String message ) {
        super(  message );
        apiErrorCode = errorCode;
    }

    public String getApiErrorCode() {
    	return apiErrorCode;
    }
    public void setApiErrorCode(String errorCode) {
    	this.apiErrorCode = errorCode;
    }

    protected EzApiException(
                                            int errorCode,
                                            String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace
    ) {
        super( errorCode, message, cause, enableSuppression, writableStackTrace );
    }
    
   
}
