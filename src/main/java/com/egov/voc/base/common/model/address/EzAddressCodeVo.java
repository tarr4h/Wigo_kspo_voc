package com.egov.voc.base.common.model.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EzAddressCodeVo {
	private int totalCount;
	private int currentPage;
	private int countPerPage;
	private String errorCode;
	private String errorMessage;
}
