package com.kspo.voc.sys.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.SessionUtil;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.dao.UserBaseDao;
import com.kspo.voc.sys.model.GrpBaseVo;
import com.kspo.voc.sys.model.LoginUserVo;

@Service("loginService")
//@Slf4j
public class LoginService extends AbstractVocService {
	@Autowired
	UserBaseDao dao;
//	@Autowired
//	LoginService service;
//	@Value("${spring.sso.auth-url}")
//	private String authUrl;

//	@Value("${spring.sso.logout-url}")
	private static final String logoutUrl = "/";

//	@Value("${spring.sso.profile-url}")
//	private String profileUrl;
//
//	@Value("${spring.sso.token-url}")
//	private String accessTokenUrl;
//
//	@Value("${spring.sso.callback-url}")
//	private String callbackUrl;
//
//	@Value("${spring.sso.client-id}")
//	private String clientId;
//
//	@Value("${spring.sso.client-secret}")
//	private String clientSecret;

//	private boolean ssoMode = false;
	@Override
	public IVocDao getDao() {
		return dao;
	}

	public Object updatelogin(LoginUserVo param) throws EgovBizException {
		EzMap result = new EzMap();
		LoginUserVo user = dao.selectUser(param);
		if (user == null) {
			result.put("errorMsg", "사용자 아이디 또는 암호를 확인해 주세요");
			result.put("errorCode", "FAILED LOGIN");
			result.put("success", false);
			return result;
		}
		List<GrpBaseVo> groupList = getGroupList(user);
		if (Utilities.isEmpty(groupList)) {
			result.put("errorMsg", "권한없는 사용자 입니다.");
			result.put("errorCode", "FAILED LOGIN");
			result.put("success", false);
			return result;
		}
		result.put("success", true);
		processLogin(user);
		return result;
	}

	public List<GrpBaseVo> getGroupList(LoginUserVo user) throws EgovBizException {
		List<GrpBaseVo> list = dao.selectUserGroupList(user);
		Map<String, Object> grpMap = new HashMap<>();
		String ip = Utilities.getPeerIp();
		user.setGroupList(new ArrayList<>());
		for (int i = 0; i < list.size(); i++) {
			GrpBaseVo vo = list.get(i);
			if (grpMap.containsKey(vo.getGrpId()))
				continue;
			if ("127.0.0.1".equals(ip)) {
				grpMap.put(vo.getGrpId(), vo);
				user.getGroupList().add(vo);
				continue;
			}
			if (Utilities.isEmpty(vo.getIpAddr())) {
				// 시스템관리자일때만?
				continue;
			}
			if (Utilities.isInIpAddr(vo.getIpAddr(), ip)) {
				grpMap.put(vo.getGrpId(), vo);
				user.getGroupList().add(vo);
			}
		}
		return user.getGroupList();
	}

	public Object updatelogout() throws EgovBizException {
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
	 * @throws EgovBizException
	 */
	private void processLogin(LoginUserVo user) throws EgovBizException {
		SessionUtil.setLoginUser(user);
		setLoginHist(user);
	}

	public void setLoginHist(LoginUserVo user) throws EgovBizException {
		dao.updateLogin(user);
		dao.insertLoginHist(user);
	};

	private void processLogout() throws EgovBizException {
		SessionUtil.logOut();
//		response.sendRedirect(logoutUrl);

	}

	public String goSso(HttpServletResponse res) throws IOException {
//		log.debug("************************authUrl = {}***************************", authUrl);
//		if(ssoMode) {
//			String state = Utilities.getUniqNumberID(50);
//			HttpSession session = Utilities.getSession();
//			session.setAttribute(Constants._LOGIN_STATE_KEY, state);
//			String url = authUrl + "?response_type=code&client_id=" + clientId + "&redirect_uri="
//					+ URLEncoder.encode(callbackUrl, "UTF-8") + "&state=" + state;
//			res.sendRedirect(url);
//		}

		return "login";
	}

//	public void processSso(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response)
//			throws EgovBizException {
//
//		String returl = "/";
////
//		String code = (String) param.get("code");
//		if (Utilities.isEmpty(code)) {
//			request.getRequestDispatcher(returl).forward(request, response);
//			return;
//		}
//
//		String state = (String) param.get("state");
//		String aUrl = accessTokenUrl;
//		String aParam = "grant_type=authorization_code&client_id=" + clientId + "&client_secret=" + clientSecret
//				+ "&code=" + code + "&state=" + state;
//		String json = Utilities.wget(aUrl, aParam, null);
//		if (Utilities.isEmpty(json)) {
//			request.getRequestDispatcher(returl).forward(request, response);
//			return;
//		}
//		Map<String, Object> map = Utilities.getJson(json);
//		if (map == null) {
//			request.getRequestDispatcher(returl).forward(request, response);
//			return;
//		}
//		String token = (String) map.get("access_token");
//		if (Utilities.isEmpty(token)) {
//			request.getRequestDispatcher(returl).forward(request, response);
//			return;
//		}
//
//		String pm = "client_id=" + clientId + "&client_secret=" + clientSecret + "&access_token=" + token;
//		String profile = Utilities.wget(profileUrl, pm);
//		if (Utilities.isEmpty(profile)) {
//			request.getRequestDispatcher(returl).forward(request, response);
//			return;
//		}
//		Map<String, Object> pf = Utilities.getJson(profile);
//		if (pf == null) {
//			request.getRequestDispatcher(returl).forward(request, response);
//			return;
//		}
//
//		String loginId = (String) pf.get("login_id");
//		if (Utilities.isEmpty(loginId)) {
//			request.getRequestDispatcher(returl).forward(request, response);
//			return;
//		}
//
//		LoginUserVo user = new LoginUserVo();
//		user.setLoginId(loginId);
//		updatelogin(user);
//		request.getRequestDispatcher(returl).forward(request, response);
//		return;
//	}
}
