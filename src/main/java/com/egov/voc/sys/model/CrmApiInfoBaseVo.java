package com.egov.voc.sys.model;

import com.egov.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmApiInfoBaseVo extends BaseVo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7918787271268734596L;
	/**
    * <p>API코드</p> 
    */
    private String apiCd;
    /**
    * <p>시스템코드</p> 
    */
    private String sysCd;
    /**
    * <p>APIURL</p> 
    */
    private String apiUrl;
    /**
    * <p>호출메쏘드코드</p> 
    */
    private String callMthdCd;
    /**
    * <p>API권한여부</p> 
    */
    private String apiAuthYn;
    /**
    * <p>사용여부</p> 
    */
    private String useYn;
}
