package com.kspo.base.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kspo.base.api.model.ApiResultVo;
import com.kspo.base.api.model.TokenVo;
import com.kspo.base.api.service.TokenService;
import com.kspo.base.common.model.EzApiException;
import com.kspo.voc.comn.util.Constants;

/**
 * @author Kim Young Hyun(youngh.kim@kt.com)
 * @date 2022. 5. 27.
 * @description
 *
 *              <pre>
 * API를 사용하기 위한 token을 처리하는 controller
 * token 생성 API는 swagger에 오픈하지 않는다.
 *              </pre>
 *
 */
@RestController
@RequestMapping("/api/v1.0/token/{channelCode}")
public class TokenController extends BaseRestController {

	@Autowired
	TokenService service;

	/**
	 * @author Kim Young Hyun(youngh.kim@kt.com)
	 * @date 2022. 5. 27.
	 * @param channelCode
	 * @return
	 * @throws Exception
	 * @description 등록된 channelCode에 해당하는 token을 생성한다.
	 *
	 */
	@GetMapping("")
	public ResponseEntity<ApiResultVo<TokenVo>> getToken(@PathVariable("channelCode") String channelCode)
			throws Exception {
		if (StringUtils.isBlank(channelCode)) {
			throw new EzApiException(Constants._API_CODE_NO_TOKEN, "채널코드가 유효하지 않습니다.");
		}
		TokenVo tokenVo = service.getToken(channelCode);

		HttpHeaders headers = new HttpHeaders();
		// 24 hours
		int maxAge = 60 * 60 * 24;
		headers.add("Set-Cookie", "ceragem-token=" + tokenVo.getToken() + "; Max-Age=" + maxAge + "; Path=/; HttpOnly");

		return successResponse(tokenVo, headers);
	}
}
