package com.kspo.base.api.jwt.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.kspo.base.common.model.EzApiException;
import com.kspo.voc.comn.util.Constants;

/**
 * <pre>
 * com.kspo.base.api.jwt.security - EzJwtAccessDeniedHandler.java
 * </pre>
 *
 * @ClassName : EzJwtAccessDeniedHandler
 * @Description : 인증에러 핸들러
 * @author : 김성태
 * @date : 2021. 1. 22.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public class EzJwtAccessHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		throw new EzApiException(Constants._API_CODE_NO_RIGHT, "권한이 없습니다.", accessDeniedException);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		throw new EzApiException(Constants._API_CODE_NOT_FOUND, "해당 API가 존재하지 않습니다.", authException);
	}

}
