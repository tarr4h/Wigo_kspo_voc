package com.kspo.base.api.jwt;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.kspo.base.api.jwt.entity.JwtUser;
import com.kspo.base.api.model.ApiErrorResultVo;
import com.kspo.base.api.model.ApiResultVo;
import com.kspo.base.api.service.UrlMatchService;
import com.kspo.base.common.jwt.JwtUtility;
import com.kspo.base.common.model.EzApiException;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Constants;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.ApiExecHstDao;
import com.kspo.voc.sys.dao.ApiInfoBaseDao;
import com.kspo.voc.sys.model.ApiExecHstVo;
import com.kspo.voc.sys.model.ApiInfoBaseVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.kspo.base.api.jwt - EzJwtToken.java
 * </pre>
 *
 * @ClassName : EzJwtToken
 * @Description : JWT Provider
 * @author : MKH
 * @date : 2021. 1. 22.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EzJwtService {

	static private final String _HEADER_AUTHORIZATION = "Authorization";
	private static final String LOCAL_IP = "0;0;0;0;0;0;0;1".replace(";", ":");
	private static final String LOCAL_IP_ADDR = "127;0;0;1".replace(";", ".");

	@Value("${spring.jwt.auth-validation}")
	boolean checkApi = false;

	@Value("${springdoc.swagger-ui.path:/swagger-ui/}")
	String swaggerUIPath;

	@Autowired
	ApiInfoBaseDao apiDao;

	@Autowired
	ApiExecHstDao apiHstDao;

	@Autowired
	UrlMatchService matchService;

	// Jwt 토큰으로 인증 정보를 조회
	public Authentication getAuthentication(String token, HttpServletRequest request) throws RuntimeException {

		Map<String, Object> payload = verifyJWT(token, request);
		request.setAttribute(Constants._APIKEY_PAYLOAD, payload);
		UserDetails userDetails = new JwtUser("API");
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		return auth;
	}

	/**
	 * @Title : resolveToken
	 * @Description : 토큰정보를 decode
	 * @param @param  request
	 * @param @return
	 * @return String
	 */

	public static String resolveToken(HttpServletRequest request) {
		String token = request.getHeader(_HEADER_AUTHORIZATION);

		if (token != null) {
			if (token.toLowerCase().startsWith("bearer "))
				token = token.substring(7);
			token = token.trim();
		}
		return token;
	}

	// 토큰 검증
	public Map<String, Object> verifyJWT(String token, HttpServletRequest request) throws RuntimeException {
		ApiInfoBaseVo apiInfo = hasAuth(token, request);
		if (apiInfo == null)
			throw new EzApiException(Constants._API_CODE_NO_TOKEN, "인증정보가 존재하지 않습니다.");
		try {
			return JwtUtility.verifyJWT(token);
		} catch (RuntimeException e) {
			if (!checkApi || "N".equals(apiInfo.getApiAuthYn()))
				return null;
			else
				throw e;
		} catch (Exception e) {
			if (!checkApi || "N".equals(apiInfo.getApiAuthYn()))
				return null;
			throw new EzApiException(Constants._API_CODE_NO_TOKEN, "인증정보가 존재하지 않습니다.");
		}
	}

	private ApiInfoBaseVo hasAuth(String token, HttpServletRequest request) throws RuntimeException {
		String reqUrl = request.getRequestURI();
		String reqMethod = request.getMethod();
		EzMap so = new EzMap();
		so.setString("reqUrl", reqUrl);
		so.setString("reqMethod", reqMethod);

		request.removeAttribute(Constants._API_INFO_KEY);
		ApiExecHstVo apiInfo = null;
		AntPathMatcher matcher = new AntPathMatcher();

		if (matcher.match(swaggerUIPath + "/**", reqUrl)) {
			validSwaggerUi(request);
		} else {
			apiInfo = matchService.getMatchUrl(reqUrl, reqMethod);
			if (checkApi && apiInfo == null) {
				throw new EzApiException(Constants._API_CODE_NO_REG, "등록되지 않은 API 주소입니다.");
			}
			if (checkApi && apiInfo != null && !"Y".equals(apiInfo.getUseYn())) {
				throw new EzApiException(Constants._API_CODE_EXPIRED_URL, "사용 종료된 API 주소입니다.");
			}
		}

		if (apiInfo == null) {
			apiInfo = new ApiExecHstVo();
		}
		apiInfo.setApiCallDt(Utilities.getDateTimeString());
		apiInfo.setApiCallIpAddr(request.getRemoteAddr().replaceAll(LOCAL_IP, LOCAL_IP_ADDR));
		apiInfo.setApiCallUrl(reqUrl);
		apiInfo.setApiCallToken(token);
		request.setAttribute(Constants._API_INFO_KEY, apiInfo);
		if (checkApi && Utilities.isEmpty(token) && "Y".equals(apiInfo.getApiAuthYn())) {
			throw new EzApiException(Constants._API_CODE_NO_TOKEN, "토큰정보가 존재하지 않습니다.");
		}

		return apiInfo;
	}

	private void validSwaggerUi(HttpServletRequest request) {
		boolean checkToken = false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("voc-token".equals(cookie.getName())) {
					String tokenCookie = cookie.getValue();
					try {
						JwtUtility.verifyJWT(tokenCookie);
						checkToken = true;
					} catch (Exception e) {
						log.info(e.getMessage());
					}
				}
			}
		}

		if (!checkToken) {
			throw new EzApiException(Constants._API_CODE_NO_RIGHT, Constants._API_CODE_NO_RIGHT_MSG);
		}
	}

	@SuppressWarnings("unchecked")
	public static String getSystemCd() {
		HttpServletRequest req = Utilities.getRequest();
		if (req == null)
			return null;
		Map<String, Object> payload = (Map<String, Object>) req.getAttribute(Constants._APIKEY_PAYLOAD);
		if (payload == null) {
			String token = resolveToken(req);
			if (Utilities.isEmpty(token)) {
				try {
					Map<String, Object> clm = JwtUtility.verifyJWT(token);
					String s = (String) clm.get(Constants._APIKEY_PAYLOAD_KEY_SYSTEM);
					if (Utilities.isNotEmpty(s))
						return s;
				} catch (Exception ex) {
					log.debug(ex.getMessage());
				}

			}
			return Constants._UNKNOWN_SYSTEM_CD;
		}
		String sysCd = (String) payload.get(Constants._APIKEY_PAYLOAD_KEY_SYSTEM);
		if (Utilities.isEmpty(sysCd))
			return Constants._UNKNOWN_SYSTEM_CD;
		else
			return sysCd;

	}

	public ApiExecHstVo getApiExecInfo() {
		HttpServletRequest req = Utilities.getRequest();
		if (req == null)
			return null;
		ApiExecHstVo apiInfo = (ApiExecHstVo) req.getAttribute(Constants._API_INFO_KEY);
		return apiInfo;
	}

	public <T> void addExecHist(ApiResultVo<T> apiResult) {
		try {
			ApiExecHstVo apiInfo = getApiExecInfo();
			if (apiInfo == null)
				return;
//			String param = getParamString();
//			if (apiResult.getPayload() != null)
//				apiInfo.setApiRsltTxn(Utilities.getJsonString(apiResult.getPayload()));
			apiInfo.setApiRsltTxn(Utilities.getJsonString(apiResult));
//			apiInfo.setApiParamTxn(param);
			apiInfo.setApiRsltCd(apiResult.getCode());
			apiInfo.setApiRsltMsgTxn(apiResult.getMessage());
			Date dt = Utilities.parseDate(apiInfo.getApiCallDt());
			int apiExecMsec = 0;
			if (dt != null) {
				apiExecMsec = (int) (new Date().getTime() - dt.getTime());
			}
			apiInfo.setApiExecMsec(apiExecMsec);
			apiHstDao.insert(apiInfo);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	public void addErrorHist(ApiErrorResultVo apiResult) {
		if (apiResult == null)
			return;
		Throwable throwable = apiResult.thrownError();
		apiResult.thrownError(null);
		try {
			String attr = (String) Utilities.getRequest().getAttribute(Constants._API_CALL_URL_KEY);
			if (Utilities.isEmpty(attr)) {
				return;
			}
			ApiExecHstVo apiInfo = getApiExecInfo();
			if (apiInfo == null) {
				apiInfo = new ApiExecHstVo();
				apiInfo.setApiCallIpAddr(Utilities.getPeerIp());

				apiInfo.setApiCallUrl(attr);

			} else {
				apiInfo.setApiCallUrl(attr);
			}
//			String param = getParamString();
			if (throwable != null && Constants._API_CODE_FAIL.equals(apiResult.getCode())) {
				StringWriter sw = new StringWriter();
				throwable.printStackTrace(new PrintWriter(sw));
				apiInfo.setApiRsltTxn(sw.toString());
			} else {
				apiInfo.setApiRsltTxn(Utilities.getJsonString(apiResult));
			}
//			apiInfo.setApiParamTxn(param);
			apiInfo.setApiRsltCd(apiResult.getCode());
			apiInfo.setApiRsltMsgTxn(apiResult.getMessage());
			Date dt = Utilities.parseDate(apiInfo.getApiCallDt());
			int apiExecMsec = 0;
			if (dt != null) {
				apiExecMsec = (int) (new Date().getTime() - dt.getTime());
			}
			if (apiExecMsec < 0)
				apiExecMsec = 0;
			apiInfo.setApiExecMsec(apiExecMsec);
//			apiInfo.setApiExecMsec(0);
			if (!"/".equals(apiInfo.getApiCallUrl()))
				apiHstDao.insert(apiInfo);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

//	private String getParamString() {
//		HttpServletRequest req = Utilities.getRequest();
//		if (req == null)
//			return null;
////		String contentsType = Utilities.nullCheck(req.getContentType());
////		if (contentsType.toUpperCase().indexOf("JSON") > -1) {
////			return Utilities.getRequestBody();
////		}
//		ApiExecHstVo apiInfo = (ApiExecHstVo) req.getAttribute(Constants._API_INFO_KEY);
//		Enumeration<String> params = req.getParameterNames();
//		String parameters = "token : \n" + apiInfo.getApiCallToken() + "\n\nparam : \n";
//		while (params.hasMoreElements()) {
//			String name = (String) params.nextElement();
//			if (Utilities.isNotEmpty(parameters))
//				parameters += "&";
//			parameters += name + "=" + Utilities.nullCheck(req.getParameter(name));
//
//		}
//
//		return parameters;
//
//	}

}
