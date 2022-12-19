package com.kspo.base.common.model.address;


import com.kspo.base.common.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EzAddressVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7484212968989047706L;
	private String roadAddr; /* Y 전체 도로명주소 */
	private String roadAddrPart1; /* Y 도로명주소(참고항목 제외) */
	private String roadAddrPart2; /* String N 도로명주소 참고항목 */
	private String jibunAddr; /* String Y 지번주소 */
	private String engAddr; /* String Y 도로명주소(영문) */
	private String zipNo; /* String Y 우편번호 */
	private String admCd; /* String Y 행정구역코드 */
	private String rnMgtSn; /* String Y 도로명코드 */
	private String bdMgtSn; /* String Y 건물관리번호 */
	private String detBdNmList; /* String N 상세건물명 */
	private String bdNm; /* String N 건물명 */
	private String bdKdcd; /* String Y 공동주택여부(1 : 공동주택, 0 : 비공동주택) */
	private String siNm; /* String Y 시도명 */
	private String sggNm; /* String Y 시군구명 */
	private String emdNm; /* String Y 읍면동명 */
	private String liNm; /* String N 법정리명 */
	private String rn; /* String Y 도로명 */
	private String udrtYn; /* String Y 지하여부(0 : 지상, 1 : 지하) */
	private String buldMnnm; /* Number Y 건물본번 */
	private String buldSlno; /* Number Y 건물부번 */
	private String mtYn; /* String Y 산여부(0 : 대지, 1 : 산) */
	private String lnbrMnnm; /* Number Y 지번본번(번지) */
	private String lnbrSlno; /* Number Y 지번부번(호) */
	private String emdNo; /* String Y 읍면동일련번호 */
	private String hstryYn; /* String Y * 변동이력여부(0: 현행 주소정보, 1: 요청변수의 keyword(검색어)가 변동된 주소정보에서 검색된 정보) */
	private String relJibun; /* String N *관련지번 2020년12월8일 추가된 항목 */
	private String hemdNm; /* String N *관할주민센터 2020년12월8일 추가된 항목 */
}
