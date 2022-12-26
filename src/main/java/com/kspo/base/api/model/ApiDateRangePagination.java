package com.kspo.base.api.model;


import com.kspo.base.common.validate.DateValue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "페이징 정보")
@Getter
@Setter
public class ApiDateRangePagination extends ApiPagination {

	/**
	 * 검색시작일
	 */
	@Schema(description = "검색시작일", example = "", hidden = true, required = false, nullable = true)
	@DateValue
	private String startDt;
	/**
	 * 검색종료일
	 */
	@Schema(description = "검색종료일", example = "", hidden = true, required = false, nullable = true)
	@DateValue
	private String endDt;
}
