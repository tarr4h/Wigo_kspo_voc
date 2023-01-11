package com.kspo.voc.sys.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

import com.kspo.base.common.model.AbstractTreeVo;

@Getter
@Setter
public class MenuBaseVo extends AbstractTreeVo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * <p>
	 * 메뉴코드
	 * </p>
	 */
	private String menuId;
	/**
	 * <p>
	 * 메뉴명
	 * </p>
	 */
	private String menuNm;
	/**
	 * <p>
	 * 메뉴URL
	 * </p>
	 */
	private String menuUrl;
	/**
	 * <p>
	 * 최상위메뉴코드
	 * </p>
	 */
	private String topMenuId;
	/**
	 * <p>
	 * 부모메뉴코드
	 * </p>
	 */
	private String prntsMenuId;
	/**
	 * <p>
	 * 메뉴레벨번호
	 * </p>
	 */
	private Integer menuLvlNo = 0;
	/**
	 * <p>
	 * 메뉴순번
	 * </p>
	 */
	private Integer menuOdrg = 0;
	/**
	 * <p>
	 * 메뉴아이콘코드
	 * </p>
	 */
	private String menuIconCd;
	/**
	 * <p>
	 * 메뉴권한여부
	 * </p>
	 */
	private String menuAuthYn;
	/**
	 * <p>
	 * 메뉴노출여부
	 * </p>
	 */
	private String menuShowYn;
	/**
	 * <p>
	 * 메뉴팝업여부
	 * </p>
	 */
	private String menuPopupYn;
	/**
	 * <p>
	 * 메뉴팝업가로길이
	 * </p>
	 */
	private Integer menuPopupWdthLen = 0;
	/**
	 * <p>
	 * 메뉴팝업세로길이
	 * </p>
	 */
	private Integer menuPopupVrtcLen = 0;
	/**
	 * <p>
	 * 메뉴설명
	 * </p>
	 */
	private String menuExpln;
	/**
	 * <p>
	 * 변경로그여부
	 * </p>
	 */
	private String chngLogYn;
	/**
	 * <p>
	 * 사용여부
	 * </p>
	 */
	private String useYn;
	
	
	
	
	
	public MenuBaseVo() {

	}

	public MenuBaseVo(Map<String, Object> param) {
		super(param);
	}
	@Override
	public String getId() {
		return getMenuId();
	}

	@Override
	public String getText() {
		return getMenuNm();
	}

	@Override
	public String getParentId() {
		return getPrntsMenuId();
	}
	@Override
	public int getLevel() {
		return getMenuLvlNo();
	}
}
