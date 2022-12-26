package com.kspo.base.api.model;

import java.io.PrintWriter;
import java.io.StringWriter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Schema(description = "error 결과")
@NoArgsConstructor
public class ApiErrorResultVo extends ApiResultVo<Object> {
	private Throwable throwable;
	public ApiErrorResultVo(String errorCode, String errorMessage,Throwable t) {
		super(errorCode,errorMessage);
		throwable = t;
		if(t != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			setPayload(sw.toString());
		}
		
	}
	public Throwable thrownError() {
		return throwable;
	}
	public Throwable thrownError(Throwable error) {
		return throwable = error;
	}
}
