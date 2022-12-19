package com.kspo.voc.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrpMenuRelVo extends MenuVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
    * <p>그룹코드</p> 
    */
    private String grpId;
    
    private String grpNm;

}
