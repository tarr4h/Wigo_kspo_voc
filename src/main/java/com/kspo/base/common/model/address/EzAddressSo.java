package com.kspo.base.common.model.address;

import com.kspo.base.common.model.EzPaginationInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EzAddressSo {
	private String keyword;
	private String language;
	private int currentPage;
	private int countPerPage;

	public EzAddressSo() {

	}

	public EzAddressSo(EzPaginationInfo page) {
		currentPage = page.getCurrentPageNo();
		countPerPage = page.getRecordCountPerPage();
	}

}
