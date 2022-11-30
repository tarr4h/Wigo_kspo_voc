package com.egov.voc.sys.interceptor;

import com.egov.base.common.model.EzLoginAjaxException;
import com.egov.base.common.model.EzLoginException;
import com.egov.base.common.model.EzMap;
import com.egov.voc.comn.util.Constants;
import com.egov.base.common.util.HandlerUtils;
import com.egov.voc.comn.util.SessionUtil;
import com.egov.voc.sys.controller.CrmMainController;
import com.egov.voc.sys.service.CrmLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <pre>
 * com.wigo.crm.interceptor - AuthInterceptor.java
 * </pre>
 *
 * @ClassName : AuthInterceptor
 * @Description : 권한 인터셉터
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

// public class AuthCheckInterceptor extends HandlerInterceptorAdapter {
@Component("authInterceptor")
public class AuthInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Autowired
    CrmLoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod))
			return true;
		if (HandlerUtils.isInstance(handler, ErrorController.class))
			return true;

		String targetURI = request.getServletPath();

		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();

			// request 정보
			sb.append("\n[preHandle] <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			sb.append("\n#remoteIp   : ").append(request.getRemoteAddr());
			sb.append("\n#targetURI  : ").append(targetURI);
			sb.append("\n#reqUrl     : ").append(request.getRequestURL().toString());
			sb.append("\n#userAgent  : ").append(request.getHeader("User-Agent"));
			sb.append("\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			logger.debug(sb.toString());
		}

		// if ( HandlerUtils.isInstance( handler, ErrorController.class ) ) return true;
		// if ( HandlerUtils.isInstance( handler, LoginController.class ) ) return true;

		boolean isLogin = SessionUtil.isLogin();

		if (!isLogin) {
			if (SessionUtil.isAjaxRequest()) {
				throw new EzLoginAjaxException("need login");
//				response.sendError(Constants._ERROR_NEED_LOGIN, "need login");
			} else {
//				String url = "/login/"; // + Utilities.nullCheck(request.getAttribute("urlSuffix")) ;
//				request.getRequestDispatcher(url).forward(request, response);
				throw new EzLoginException();
			}
			/*
			
			*/
		} else {
			if (HandlerUtils.isInstance(handler, CrmMainController.class))
				return true;

			String menuCd = (String) request.getAttribute("currentMenuCd");
			EzMap menuMap = (EzMap) request.getAttribute("menuMap");
			if (menuMap.containsKey(menuCd)) {
				return true;
			} else {
				response.sendError(Constants._ERROR_HAS_NO_RIGHT, "권한이 없습니다.");
			}
		}

		return true;
	}

}
