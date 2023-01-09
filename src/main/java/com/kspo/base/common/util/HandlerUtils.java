package com.kspo.base.common.util;

import org.springframework.web.method.HandlerMethod;

/**
 * 
* <pre>
* com.kspo.base.common.util
*	- HandlerUtils.java
* </pre>
*
* @ClassName	: HandlerUtils 
* @Description	: 핸들러 유틸리티 
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public class HandlerUtils {
	public static boolean isInstance(Object handler, Class<?> clazz) {
		if (!(handler instanceof HandlerMethod)) {
			return false;
		}
		
		Object object = ((HandlerMethod) handler).getBean();
		if (clazz.isInstance(object)) {
			return true;
		}
		
		return false;
	}
	
	// for spring 3.1.x
	@SuppressWarnings("unchecked")
	public static <T> T toHandlerClass(Object handler, Class<T> t) {
		if (handler instanceof HandlerMethod) {
			Object object = ((HandlerMethod) handler).getBean();
			
			if (t.isInstance(object)) {
				return (T)object;
			}
		}
		
		return null;
	}
}
