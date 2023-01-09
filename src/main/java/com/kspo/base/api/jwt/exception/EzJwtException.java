package com.kspo.base.api.jwt.exception;

import java.util.Map;

import com.kspo.base.common.model.EzException;
import com.kspo.base.common.model.EzMap;

/** 
* <pre>
* com.kspo.base.api.jwt.exception
*	- EzJwtException.java
* </pre>
*
* @ClassName	: EzJwtException 
* @Description	: TODO 
* @author 		: 김성태
* @date 		: 2021. 1. 22.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
*/

public class EzJwtException extends EzException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5277330652383853309L;
	private Map<String, Object> payload = null;
	/**
	* Desc : Constructor of EzJwtException.java class
	* @param accessDeniedException
	*/
	public EzJwtException() {
		
	}
	public EzJwtException(Exception exception) {
		super(exception);
	}

	/**
	* Desc : Constructor of EzJwtException.java class
	* @param payload
	*/
	
	public EzJwtException(Map<String, Object> payload) {
		this.setPayload(payload);
	}

	/**
	 * @return the payload
	 */
	public Map<String, Object> getPayload() {
		return payload;
	}
	/**
	 * @param payload the payload to set
	 */
	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}
	@Override
	public EzMap toMap() {
		if(this.payload == null)
			return super.toMap();
		EzMap map = new EzMap();
		map.putAll(this.payload);
		return map;
	}
	

}
