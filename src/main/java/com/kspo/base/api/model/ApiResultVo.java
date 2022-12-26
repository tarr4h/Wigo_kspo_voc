package com.kspo.base.api.model;


import com.kspo.voc.comn.util.Constants;
import com.kspo.voc.comn.util.Utilities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "API 공통 result")
@Getter
@Setter
public class ApiResultVo<T> extends ApiVoidResultVo /* extends ResponseEntity<T> */
{

	private String timestamp = Utilities.getTimeStamp();
	@Schema(description = "결과데이터", required = false)
	private T payload;

	public ApiResultVo() {
//		super(HttpStatus.OK);
		super();
	}

	public ApiResultVo(String code, String message) {
//		super(HttpStatus.OK);
		super(code, message);
	}

	public ApiResultVo(String code, String message, T payload) {
//		super(payload,HttpStatus.OK);
		super(code, message);
		this.payload = payload;
	}

	public ApiResultVo(T payload) {
		super(Constants._API_CODE_OK, _MSG_SUCCESS);
		this.payload = payload;
	}

}
