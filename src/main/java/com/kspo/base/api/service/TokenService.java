package com.kspo.base.api.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.api.model.TokenVo;
import com.kspo.base.common.jwt.JwtUtility;
import com.kspo.base.common.model.EzApiException;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Constants;
import com.kspo.voc.sys.model.ComnCdBaseVo;
import com.kspo.voc.sys.service.ComnCdService;

/**
 * @author Kim Young Hyun(youngh.kim@kt.com)
 * @date 2022. 5. 27.
 * @description API를 사용하기 위한 token을 처리하는 service
 *
 */
@Service
public class TokenService {

	@Autowired
	ComnCdService comnCdBasService;

	/**
	 * @author Kim Young Hyun(youngh.kim@kt.com)
	 * @date 2022. 5. 27.
	 * @param channelCode
	 * @return
	 * @throws Exception
	 * @description channelCode로 token을 생성한다.
	 *
	 */
	public TokenVo getToken(String channelCode) throws Exception {
		if (validChannelCode(channelCode)) {
			String jwtToken = JwtUtility.createToken("SYSTEM", channelCode);
			TokenVo token = new TokenVo();
			token.setToken(jwtToken);

			return token;
		} else {
			throw new EzApiException();
		}
	}

	/**
	 * @author Kim Young Hyun(youngh.kim@kt.com)
	 * @date 2022. 5. 27.
	 * @param channelCode
	 * @return
	 * @throws Exception
	 * @description channelCode가 유효한지 확인한다.
	 *
	 */
	private boolean validChannelCode(String channelCode) throws Exception {
		EzMap param = new EzMap();
		param.setString("topComnCd", "S000");
		param.setString("comnCd", channelCode);
		param.setString("useYn", "Y");

		List<ComnCdBaseVo> list = comnCdBasService.getList(param);
		if (CollectionUtils.isEmpty(list)) {
			throw new EzApiException(Constants._API_CODE_NO_TOKEN, "채널코드가 유효하지 않습니다.");
		}

		return true;
	}

}
