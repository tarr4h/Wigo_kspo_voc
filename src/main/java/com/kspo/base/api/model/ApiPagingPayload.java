package com.kspo.base.api.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "리스트 및 페이징")
public class ApiPagingPayload<T> {
	@Schema(description = "결과목록")
	private List<T> list;
	@Schema(description = "페이징 정보")
	private ApiBasePagination pagination;

	public ApiPagingPayload(List<T> list, ApiBasePagination so) {
		this.list = list;
		this.pagination = toPagination(so);
	}

	private ApiBasePagination toPagination(ApiBasePagination so) {
		ApiBasePagination pagination = new ApiBasePagination();
		if (so != null) {
			pagination.setCurrentPageNo(so.getCurrentPageNo());
			pagination.setRecordCountPerPage(so.getRecordCountPerPage());
			pagination.setTotalRecordCount(so.getTotalRecordCount());
		}

		return pagination;
	}

}
