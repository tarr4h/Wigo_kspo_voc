package com.kspo.voc.sys.model;

import com.kspo.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName IpRelVo
 * @author 김성태
 * @date 2023. 1. 9.
 * @Version 1.0
 * @description 아이피관계 Vo
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class IpRelVo extends BaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	/*
	 * IP관계ID
	 */
	private String ipRelId;
	/**
	 * 참조아이디
	 */
	private String refId;
	/**
	 * 참조유형코드
	 */
	private String refTpCd;
	/**
	 * IP주소
	 */
	private String ipAddr;
	/**
	 * 사용시작일
	 */
	private String useStaYmd;
	/**
	 * 사용종료일
	 */
	private String useEndYmd;
	
	
	/**
	 * 참조아이디
	 */
	private String refIdNm;
	/**
	 * 참조유형코드
	 */
	private String refTpCdNm;
}
