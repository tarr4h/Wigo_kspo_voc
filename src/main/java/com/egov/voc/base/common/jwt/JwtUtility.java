package com.egov.voc.base.common.jwt;


import com.egov.voc.base.common.model.EzApiException;
import com.egov.voc.base.common.util.BaseUtilities;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <pre>
 * com.wigo.crm.config - JwtUtility.java
 * </pre>
 *
 * @ClassName : JwtUtility
 * @Description : Jwt유틸리티 설정
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public abstract class JwtUtility {
	final static String key = "1qaz@WSX";
//	final static String key = "!234";

	public static String createToken(String userCd, String systemCd, long hours) {
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		// payload 부분 설정
		Map<String, Object> payloads = new HashMap<>();
		if (BaseUtilities.isNotEmpty(userCd))
			payloads.put("userCd", userCd);
		if (BaseUtilities.isNotEmpty(systemCd))
			payloads.put("systemCd", systemCd);

		Long expiredTime = 1000 * 60L * 60L * hours; // 토큰 유효 시간 (2시간)

		Date ext = new Date(); // 토큰 만료 시간
		ext.setTime(ext.getTime() + expiredTime);

		// 토큰 Builder
		String jwt = Jwts.builder().setHeader(headers) // Headers 설정
				.setClaims(payloads) // Claims 설정
				.setSubject("user") // 토큰 용도
				.setExpiration(ext) // 토큰 만료 시간 설정
				.signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256과 Key로 Sign
				.compact(); // 토큰 생성

		return jwt;
	}

	public static String createToken(String userCd, String systemCd) {
		return createToken(userCd, systemCd, 3650 * 24);
	}

	// 토큰 생성
	public static String createToken() {

		return createToken("SYSTEM", "SYSTEM");
	}

	public static String createPayload(Object payload, long hours) {
		// Header 부분 설정
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		// payload 부분 설정
		Map<String, Object> payloads = new HashMap<>();
		payloads.put("data", payload);

		Long expiredTime = 1000 * 60L * 60L * hours;

		Date ext = new Date(); // 토큰 만료 시간
		ext.setTime(ext.getTime() + expiredTime);

		// 토큰 Builder
		String jwt = Jwts.builder().setHeader(headers) // Headers 설정
				.setClaims(payloads) // Claims 설정
				.setSubject("user") // 토큰 용도
				.setExpiration(ext) // 토큰 만료 시간 설정
				.signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256과 Key로 Sign
				.compact(); // 토큰 생성

		return jwt;
	}

	// 토큰 생성
	public static String createPayload(Object payload) {
		return createPayload(payload, 3650 * 24);
	}

	// 토큰 검증
	public static Map<String, Object> verifyJWT(String jwt) {
		if (BaseUtilities.isEmpty(jwt))
			return null;
		Map<String, Object> claimMap = null;
		try {
			Claims claims = Jwts.parser().setSigningKey(key.getBytes("UTF-8")) // Set Key
					.parseClaimsJws(jwt) // 파싱 및 검증, 실패 시 에러
					.getBody();

			claimMap = claims;

			// Date expiration = claims.get("exp", Date.class);
			// String data = claims.get("data", String.class);

		} catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
			throw e;
		} catch (MalformedJwtException e) { // 그외 에러났을 경우
			throw e;
		} catch (SignatureException e) { // 그외 에러났을 경우
			throw e;
		} catch (RuntimeException e) { // 그외 에러났을 경우
			throw e;
		} catch (Exception e) {
			throw new EzApiException("IAR0200", e.getMessage(), e);
		}
		return claimMap;
	}
}
