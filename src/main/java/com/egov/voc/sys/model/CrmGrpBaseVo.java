package com.egov.voc.sys.model;

import com.egov.voc.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmGrpBaseVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * <p>
	 * 그룹코드
	 * </p>
	 */
	private String grpCd;
	/**
	 * <p>
	 * 그룹명
	 * </p>
	 */
	private String grpNm;
	/**
	 * <p>
	 * 체크여부
	 * </p>
	 */
	private String checkYn;
}
