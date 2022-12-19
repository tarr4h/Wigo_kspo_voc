package com.kspo.voc.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmChngHstVo extends CrmUserBaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * <p>
	 * 변경이력코드
	 * </p>
	 */
	private String chngHstId;

	private String menuId;
	private String menuPath;
	/**
	 * <p>
	 * 변경호출URL
	 * </p>
	 */
	private String chngCallUrl;
	/**
	 * <p>
	 * 변경결과내역
	 * </p>
	 */
	private String chngRsltTxn;
	/**
	 * <p>
	 * 변경파라미터내역
	 * </p>
	 */
	private String chngParamTxn;
	/**
	 * <p>
	 * 변경호출내역
	 * </p>
	 */
	private String chngCallTxn;
	/**
	 * <p>
	 * 변경호출일시
	 * </p>
	 */
	private String chngCallDt;
	/**
	 * <p>
	 * 변경실행밀리초
	 * </p>
	 */
	private Integer chngExecMsec;
	/**
	 * <p>
	 * 변경호출IP주소
	 * </p>
	 */
	private String chngCallIpAddr;

}
