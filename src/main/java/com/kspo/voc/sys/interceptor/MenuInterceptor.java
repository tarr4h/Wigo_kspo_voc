package com.kspo.voc.sys.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPropertyServiceImpl;
import com.kspo.base.common.model.ITreeVo;
import com.kspo.base.common.util.HandlerUtils;
import com.kspo.base.common.util.NasUtil;
import com.kspo.voc.comn.util.SessionUtil;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.controller.MainController;
import com.kspo.voc.sys.model.MenuBaseVo;
import com.kspo.voc.sys.model.MenuVo;
import com.kspo.voc.sys.service.MenuService;

/**
 * 
 * <pre>
 * com.wigo.crm.interceptor - MenuInterceptor.java
 * </pre>
 *
 * @ClassName : MenuInterceptor
 * @Description : 메뉴 인터셉터
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

@Component("menuInterceptor")
public class MenuInterceptor implements HandlerInterceptor {

//	private Logger logger = LoggerFactory.getLogger(MenuInterceptor.class);

	@Autowired
	MenuService menuService;

	@Resource(name = "propertiesService")
	EzPropertyServiceImpl propertiesService;
	@Autowired
	NasUtil s3Util;

	private String setMenuId(HttpServletRequest request) throws Exception {

		if (request == null)
			return null;

		String urlSuffix = propertiesService.getString("urlSuffix", "");
		String cPath = request.getContextPath();

		if ("/".equals(cPath))
			cPath = "";

		String menuUrl = null;
		String errorUrl = (String) request.getAttribute("javax.servlet.error.request_uri");
		String rurl = Utilities.isEmpty(errorUrl) ? request.getRequestURI() : errorUrl;
		String sPath = Utilities.isEmpty(errorUrl) ? request.getServletPath() : errorUrl.substring(cPath.length());

		if (Utilities.isNotEmpty(urlSuffix)) {
			if (sPath.indexOf(urlSuffix) > 0)
				sPath = sPath.substring(0, sPath.length() - urlSuffix.length());
			if (rurl.indexOf(urlSuffix) > 0)
				rurl = rurl.substring(0, rurl.length() - urlSuffix.length());
		}

		if ("/".equals(sPath))
			sPath = "";

		if (sPath.startsWith("/"))
			sPath = sPath.substring(1);
		int idx = sPath.indexOf("/");
		if (idx > -1) {
			menuUrl = sPath.substring(0, idx);
		} else
			menuUrl = sPath;

		EzMap so = new EzMap();
		String topMenuId = "0100000000";
		so.put("topMenuId", topMenuId);
		so.put("userId", SessionUtil.getLoginUserId());

		EzMap menuMap = new EzMap();
		List<ITreeVo> menuTree = menuService.getUserMenuTree(so, menuMap);

		EzMap urlMap = new EzMap();
		for (String key : menuMap.keySet()) {
			MenuVo menu = (MenuVo) menuMap.get(key);
			String url = menu.getMenuUrl();
			if (Utilities.isNotEmpty(url))
				urlMap.put(url, menu);
		}

		MenuBaseVo currentMenu = (MenuBaseVo) urlMap.get(menuUrl);
		String menuId = currentMenu == null ? menuUrl : currentMenu.getMenuUrl();
		request.setAttribute("currentMenuId", menuUrl);
		request.setAttribute("menuMap", urlMap);

		if (menuTree != null && menuTree.size() > 0) {
			request.setAttribute("menuTree", menuTree.get(0));
			MenuVo menu = (MenuVo) urlMap.get(menuId);

			request.setAttribute("currentMenu", currentMenu);

			ITreeVo topMenu = menu;
			while (topMenu != null) {
				request.setAttribute("level" + topMenu.getLevel() + "Menu", topMenu);
				if (topMenu.getLevel() == 2) {
					request.setAttribute("level" + topMenu.parent().getLevel() + "Menu", topMenu.parent());
					break;
				}
				topMenu = topMenu.parent();
			}
			// topMenu = (ITreeVo) menuMap.get("0102000000");
			request.setAttribute("topMenu", topMenu);

		}
		String urlPrefix = "";// root.getString("menuUrl");
//		if(!urlPrefix.startsWith("/") && urlPrefix.length()>0)
//			urlPrefix = "/" + urlPrefix;
		if (Utilities.isNotEmpty(menuUrl))
			urlPrefix += "/" + menuUrl;
		else
			urlPrefix = "";
		request.setAttribute("currentSystemId", topMenuId);
//		request.setAttribute("currentRootUrl",root.getString("menuUrl"));

		request.setAttribute("currentMenu", currentMenu);
		request.setAttribute("loginUserId", SessionUtil.getLoginUserId());
		request.setAttribute("urlSuffix", urlSuffix);
		request.setAttribute("urlPrefix", urlPrefix);
		request.setAttribute("systemId", topMenuId);
		request.setAttribute("contextPath", cPath);

		String uniqId = Utilities.getUniqID(20);

		request.setAttribute("requestTicket", uniqId);
		return menuId;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {

			if (!(handler instanceof HandlerMethod))
				return true;
			if (HandlerUtils.isInstance(handler, ErrorController.class))
				return true;

			if (HandlerUtils.isInstance(handler, MainController.class)) {
				if (!SessionUtil.isAjaxRequest()) {
					if (request.getRequestURI().equals("/") || request.getRequestURI().equals("")) {
						SessionUtil.setUserMenuMap(null);
						SessionUtil.setUserMenuList(null);

					}
				}
			}

			setMenuId(request);
			if (SessionUtil.isAjaxRequest())
				return true;
			return true;
		} finally {

		}
	}

}
