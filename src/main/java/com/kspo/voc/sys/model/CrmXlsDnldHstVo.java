package com.kspo.voc.sys.model;

import com.kspo.base.common.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmXlsDnldHstVo extends BaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * <p>
	 * 다운로드이력코드
	 * </p>
	 */
	private String dnldHstId;
	/**
	 * <p>
	 * 메뉴코드
	 * </p>
	 */
	private String menuId;
	/**
	 * <p>
	 * 다운로드명
	 * </p>
	 */
	private String dnldNm;

	private String menuNm;
	private String menuPath;
}
