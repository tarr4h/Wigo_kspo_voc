package com.kspo.voc.sys.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kspo.base.common.model.EzPropertyServiceImpl;
import com.kspo.voc.comn.util.SessionUtil;
import com.kspo.voc.sys.service.MenuService;

/**
 * 
 * <pre>
 * com.wigo.crm.interceptor - BasicInterceptor.java
 * </pre>
 *
 * @ClassName : BasicInterceptor
 * @Description : 메뉴 인터셉터
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

@Component("basicInterceptor")
public class BasicInterceptor implements HandlerInterceptor {

	@Autowired
	MenuService menuService;

	@Resource(name = "propertiesService")
	EzPropertyServiceImpl propertiesService;

	@Value("${server.servlet.session.timeout:1800}")
	private int sessionTime;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod))
			return true;
		String urlSuffix = propertiesService.getString("urlSuffix", "");
		request.setAttribute("urlSuffix", urlSuffix);
		request.setAttribute("httpServletResponse", response);
		SessionUtil.touch(response);
		return true;
	}

}
