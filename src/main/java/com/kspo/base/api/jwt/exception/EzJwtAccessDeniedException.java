package com.kspo.base.api.jwt.exception;

/** 
* <pre>
* com.ceragem.crm.common.jwt.exception
*	- EzJwtException.java
* </pre>
*
* @ClassName	: EzJwtException 
* @Description	: 인증오류 
* @author 		: 김성태
* @date 		: 2021. 1. 22.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
*/

public class EzJwtAccessDeniedException extends EzJwtException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6327728825759667613L;

	/**
	* Desc : Constructor of EzJwtAccessDeniedException.java class
	* @param Exception
	*/
	
	public EzJwtAccessDeniedException(Exception exception) {
		super(exception);
		
		
	}

	
}
