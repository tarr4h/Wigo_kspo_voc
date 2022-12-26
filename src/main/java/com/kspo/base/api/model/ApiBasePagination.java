package com.kspo.base.api.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "페이징 정보")
@Getter
@Setter
public class ApiBasePagination {
	/**
	 * Required Fields - 이 필드들은 페이징 계산을 위해 반드시 입력되어야 하는 필드 값들이다.
	 * 
	 * currentPageNo : 현재 페이지 번호 recordCountPerPage : 한 페이지당 게시되는 게시물 건 수 pageSize :
	 * 페이지 리스트에 게시되는 페이지 건수, totalRecordCount : 전체 게시물 건 수.
	 */

	@Parameter(description = "현재페이지", example = "1")
	@Schema(description = "현재페이지", example = "1")
	private int currentPageNo = 1;

	@Parameter(description = "페이지별 검색건수", example = "30")
	@Schema(description = "페이지별 검색건수", example = "30")
//	@Max(value = 10000,message = "페이지당 최대 row는 10000건 입니다.")
	private int recordCountPerPage = 30;

	@Parameter(description = "총검색건수", example = "0", hidden = true)
	@Schema(description = "총검색건수", example = "0", hidden = false, accessMode = AccessMode.READ_ONLY)
	private int totalRecordCount = 0;

//	@Parameter(description = "첫행 row number", example = "1", hidden = true)
	@Schema(description = "첫행 row number", example = "1", hidden = true, accessMode = AccessMode.READ_ONLY)
	public int getFirstRecordIndex() {
		return (getCurrentPageNo() - 1) * getRecordCountPerPage() + 1;
	}

	@Schema(description = "마지막행 row number", example = "30", hidden = true, accessMode = AccessMode.READ_ONLY)
	public int getLastRecordIndex() {
		return getCurrentPageNo() * getRecordCountPerPage();
	}

}
