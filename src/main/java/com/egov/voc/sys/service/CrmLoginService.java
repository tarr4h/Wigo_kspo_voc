package com.egov.voc.sys.service;


import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.comn.util.Constants;
import com.egov.voc.comn.util.SessionUtil;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.dao.CrmUserBaseDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmLoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@Service("loginService")
public class CrmLoginService extends AbstractCrmService {
	@Autowired
	CrmUserBaseDao dao;
	@Autowired
	CrmLoginService service;
	@Value("${spring.sso.auth-url}")
	private String authUrl;

//	@Value("${spring.sso.logout-url}")
	private String logoutUrl = "/";

	@Value("${spring.sso.profile-url}")
	private String profileUrl;

	@Value("${spring.sso.token-url}")
	private String accessTokenUrl;

	@Value("${spring.sso.callback-url}")
	private String callbackUrl;

	@Value("${spring.sso.client-id}")
	private String clientId;

	@Value("${spring.sso.client-secret}")
	private String clientSecret;

	private boolean ssoMode = false;
	@Override
	public ICrmDao getDao() {
		return dao;
	}

	public Object updatelogin(CrmLoginUserVo param) throws Exception {
		EzMap result = new EzMap();
		CrmLoginUserVo user = dao.selectUser(param);
		if (user == null) {
			result.put("errorMsg", "사용자 아이디 또는 암호를 확인해 주세요");
			result.put("errorCode", "FAILED LOGIN");
			return result;
		}

//		if (!Utilities.passwordMatch(param.getLoginPwd(), user.getLoginPwd())) {
//			result.put("errorMsg", "사용자 아이디 또는 암호를 확인해 주세요");
//			result.put("errorCode", "FAILED LOGIN");
//			dao.updateLoginFail(param);
//			return result;
//		}
//		if (user.getLoginId().equals(param.getSaveId())) {
//			Utilities.setCookie("saveId", Utilities.encrypt(user.getLoginId()));
//		}
		if(Utilities.isEmpty(user.getMobileNo())) {
			String ph = user.getMphonNoEncVal();
			if (Utilities.isNotEmpty(ph)) {
				String phone = Utilities.decrypt(ph);
				user.setMobileNo(phone);
			}	
		}
		
		result.put("success", true);
		processLogin(user);
		return result;
	}

	public Object updatelogout() throws Exception {
		EzMap result = new EzMap();
		result.put("success", true);
		result.put("url", logoutUrl);
		processLogout();
		return result;
	}

	/**
	 * 사용자의 Login 이력을 갱신하고, Session 에 사용자 정보를 저장한다.
	 * 
	 * @param user
	 * @throws Exception
	 */
	private void processLogin(CrmLoginUserVo user) throws Exception {
		SessionUtil.setLoginUser(user);
		setLoginHist(user);
	}

	public void setLoginHist(CrmLoginUserVo user) throws Exception {
		dao.updateLogin(user);
		dao.insertLoginHist(user);
	};

	private void processLogout() throws Exception {
		SessionUtil.logOut();
//		response.sendRedirect(logoutUrl);
		
	}

	public String goSso(HttpServletResponse res) throws IOException {
		if(ssoMode) {
			String state = Utilities.getUniqNumberID(50);
			HttpSession session = Utilities.getSession();
			session.setAttribute(Constants._LOGIN_STATE_KEY, state);
			String url = authUrl + "?response_type=code&client_id=" + clientId + "&redirect_uri="
					+ URLEncoder.encode(callbackUrl, "UTF-8") + "&state=" + state;
			res.sendRedirect(url);	
		}
		
		return "login";
	}

	public void processSso(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String returl = "/";
//		
		String code = (String) param.get("code");
		if (Utilities.isEmpty(code)) {
			request.getRequestDispatcher(returl).forward(request, response);
			return;
		}

		String state = (String) param.get("state");
		String aUrl = accessTokenUrl;
		String aParam = "grant_type=authorization_code&client_id=" + clientId + "&client_secret=" + clientSecret
				+ "&code=" + code + "&state=" + state;
		String json = Utilities.wget(aUrl, aParam, null);
		if (Utilities.isEmpty(json)) {
			request.getRequestDispatcher(returl).forward(request, response);
			return;
		}
		Map<String, Object> map = Utilities.getJson(json);
		if (map == null) {
			request.getRequestDispatcher(returl).forward(request, response);
			return;
		}
		String token = (String) map.get("access_token");
		if (Utilities.isEmpty(token)) {
			request.getRequestDispatcher(returl).forward(request, response);
			return;
		}

		String pm = "client_id=" + clientId + "&client_secret=" + clientSecret + "&access_token=" + token;
		String profile = Utilities.wget(profileUrl, pm);
		if (Utilities.isEmpty(profile)) {
			request.getRequestDispatcher(returl).forward(request, response);
			return;
		}
		Map<String, Object> pf = Utilities.getJson(profile);
		if (pf == null) {
			request.getRequestDispatcher(returl).forward(request, response);
			return;
		}

		String loginId = (String) pf.get("login_id");
		if (Utilities.isEmpty(loginId)) {
			request.getRequestDispatcher(returl).forward(request, response);
			return;
		}

		CrmLoginUserVo user = new CrmLoginUserVo();
		user.setLoginId(loginId);
		updatelogin(user);
		request.getRequestDispatcher(returl).forward(request, response);
		return;
	}
}
