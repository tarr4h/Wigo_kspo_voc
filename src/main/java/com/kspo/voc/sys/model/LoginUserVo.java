package com.kspo.voc.sys.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserVo extends EmpBaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 소속 그룹목록
	 */
	List<GrpBaseVo> groupList;
	
	/*
	 * <p>사용자 그룹 콤마로 구분</p>
	 */
	private String grpIds;
	/*
	 * <p>저장된 아이디</p>
	 */
	private String saveId;

	/**
	 * <p>
	 * 로그인ID
	 * </p>
	 */
	private String loginId;
	/**
	 * <p>
	 * 로그인비밀번호
	 * </p>
	 */
	private String loginPwd;
	/**
	 * <p>
	 * 사용자성별코드
	 * </p>
	 * <p>
	 * 공통코드 : S040
	 * </p>
	 */
	private String userGndrCd;
	/**
	 * <p>
	 * 비밀번호수정일시
	 * </p>
	 */
	private String pwdAmdDt;
	/**
	 * <p>
	 * 최종로그인일시
	 * </p>
	 */
	private String lastLoginDt;
	/**
	 * <p>
	 * 비밀번호만료일시
	 * </p>
	 */
	private String pwdExpDt;
	/**
	 * <p>
	 * 로그인실패수
	 * </p>
	 */
	private Integer loginFailCnt;
	/**
	 * <p>
	 * 삭제여부
	 * </p>
	 * <p>
	 * 공통코드 : S030
	 * </p>
	 */
	private String delYn;

	
	private String topMenuId;
	
	/**
	 * <p>
	 * 사용자코드
	 * </p>
	 */
	public String getUserId() {
		return getEmpId();
	}

	public void setUserId(String userId) {
		setEmpId(userId);
	}

//	public String getLoginId() {
//		return getEmpId();
//	}
//
//	public void setLoginId(String loginId) {
//		setEmpId(loginId);
//	}

	/**
	 * <p>
	 * 사용자명
	 * </p>
	 */
	public String getUserNm() {
		return getEmpNm();
	}

	public void setUserNm(String userNm) {
		setEmpNm(userNm);
	}

	public String getUserBirthday() {
		return getBirthday();
	}

	public void setUserBirthday(String userBirthday) {
		setBirthday(userBirthday);
	}

}
