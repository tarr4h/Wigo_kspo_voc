package com.egov.voc.base.common.model;

/**
 * 
 * <pre>
 * com.wigo.crm.common.model - EzPaginationInfo.java
 * </pre>
 *
 * @ClassName : EzPaginationInfo
 * @Description : 페이징정보
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public class EzPaginationInfo {
	/*
	 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
	 * use this file except in compliance with the License. You may obtain a copy of
	 * the License at
	 *
	 * http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
	 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
	 * License for the specific language governing permissions and limitations under
	 * the License.
	 */

	/**
	 * Required Fields - 이 필드들은 페이징 계산을 위해 반드시 입력되어야 하는 필드 값들이다.
	 * 
	 * currentPageNo : 현재 페이지 번호 recordCountPerPage : 한 페이지당 게시되는 게시물 건 수 pageSize :
	 * 페이지 리스트에 게시되는 페이지 건수, totalRecordCount : 전체 게시물 건 수.
	 */

	private int currentPageNo = 1;
	private int recordCountPerPage = 30;
	private int pageSize = 5;
	private int totalRecordCount = 0;

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public int getTotalPageCount() {
		if (getTotalRecordCount() == 0) {
			return 0;
		}
		return ((getTotalRecordCount() - 1) / getRecordCountPerPage()) + 1;
	}

	public int getFirstPageNo() {
		return 1;
	}

	public int getLastPageNo() {
		return getTotalPageCount();
	}

	public int getFirstPageNoOnPageList() {
		if (getPageSize() == 0) {
			return 0;
		}

		return ((getCurrentPageNo() - 1) / getPageSize()) * getPageSize() + 1;
	}

	public int getLastPageNoOnPageList() {
		int lastPageNoOnPageList = getFirstPageNoOnPageList() + getPageSize() - 1;
		if (lastPageNoOnPageList > getTotalPageCount()) {
			lastPageNoOnPageList = getTotalPageCount();
		}
		return lastPageNoOnPageList;
	}

	public int getFirstRecordIndex() {
		return (getCurrentPageNo() - 1) * getRecordCountPerPage() + 1;
	}

	public int getLastRecordIndex() {
		return getCurrentPageNo() * getRecordCountPerPage();
	}

	public EzMap toToastPage() {
		EzMap ezMap = new EzMap();
		ezMap.setInt("page", getCurrentPageNo());
		ezMap.setInt("totalCount", getTotalRecordCount());
		ezMap.setInt("perPage", getRecordCountPerPage());
		return ezMap;
	}
}
