package com.kspo.voc.comn.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.ITreeVo;
import com.kspo.voc.sys.model.CrmLoginUserVo;

import java.util.Calendar;
import java.util.List;

/**
 * 
 * <pre>
 * com.wigo.crm.common.util - SessionUtil.java
 * </pre>
 *
 * @ClassName : SessionUtil
 * @Description : 세션유틸리티
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public class SessionUtil {
	public static final String HEADER_X_REQ_WITH = "x-requested-with";

	public static void logOut() {
		HttpSession session = Utilities.getSession();
		if (session == null)
			return;
		session.removeAttribute(Constants._USER_MENU_LIST_NAME);
		session.removeAttribute(Constants._USER_MENU_MAP_NAME);
		session.removeAttribute(Constants._LOGIN_SESSION_NAME);
		session.removeAttribute("userId");
		Utilities.setCookie("lumode", "0");

	}

	public static void setLoginUser(CrmLoginUserVo user) {
		HttpSession session = Utilities.getSession();
		if (session == null)
			return;
		session.removeAttribute(Constants._USER_MENU_LIST_NAME);
		session.removeAttribute(Constants._USER_MENU_MAP_NAME);
		session.setAttribute(Constants._LOGIN_SESSION_NAME, user);
		Utilities.setCookie("lumode", "1");
	}

	public static CrmLoginUserVo getLoginUser() {
		HttpSession session = Utilities.getSession();
		if (session == null)
			return null;
		return (CrmLoginUserVo) session.getAttribute(Constants._LOGIN_SESSION_NAME);
	}

	public static String getLoginUserId() {
		HttpSession session = Utilities.getSession();
		if (session == null)
			return null;
		CrmLoginUserVo user = (CrmLoginUserVo) session.getAttribute(Constants._LOGIN_SESSION_NAME);
		if (user == null)
			return null;
		return (String) user.getUserId();
	}

	public static boolean isAjaxRequest() {
		return isAjaxRequest(Utilities.getRequest());
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		if (request == null)
			return false;
		return "XMLHttpRequest".equals(request.getHeader(HEADER_X_REQ_WITH));
	}

	/**
	 * @Title : getUserMenu
	 * @Description : 세션에서 사용자 메뉴를 가져옵니다.
	 * @param @return
	 * @return List<ITreeVo>
	 */

	@SuppressWarnings("unchecked")
	public static List<ITreeVo> getUserMenuList() {
		HttpSession session = Utilities.getSession();
		if (session == null)
			return null;
		List<ITreeVo> tree = (List<ITreeVo>) session.getAttribute(Constants._USER_MENU_LIST_NAME);
		return tree;
	}

	public static void setUserMenuList(List<ITreeVo> tree) {
		HttpSession session = Utilities.getSession();
		if (session == null)
			return;
		if (tree == null)
			session.removeAttribute(Constants._USER_MENU_LIST_NAME);
		else
			session.setAttribute(Constants._USER_MENU_LIST_NAME, tree);
	}

	public static EzMap getUserMenuMap() {
		HttpSession session = Utilities.getSession();
		if (session == null)
			return null;
		EzMap map = (EzMap) session.getAttribute(Constants._USER_MENU_MAP_NAME);
		return map;
	}

	public static void setUserMenuMap(EzMap map) {
		HttpSession session = Utilities.getSession();
		if (session == null)
			return;
		if (map == null)
			session.removeAttribute(Constants._USER_MENU_MAP_NAME);
		else
			session.setAttribute(Constants._USER_MENU_MAP_NAME, map);
	}

	public static boolean isLogin() {
		return Utilities.isNotEmpty(getLoginUserId());
	}

	public static void touch(HttpServletResponse response) {
		HttpSession session = Utilities.getSession();
		if (session == null)
			return;
		long touch = Calendar.getInstance().getTimeInMillis();
		session.setAttribute("lastTouch", touch);
		if (response == null)
			return;
		Cookie cookie = new Cookie("lastTouch", touch + "");
		cookie.setPath("/");
		response.addCookie(cookie);
	}

}
