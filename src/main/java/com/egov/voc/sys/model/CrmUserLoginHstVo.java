package com.egov.voc.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmUserLoginHstVo extends CrmLoginUserVo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
    * <p>로그인일시</p> 
    */
    private String loginDt;
    /**
    * <p>로그인IP주소</p> 
    */
    private String loginIpAddr;
}
