package com.egov.voc.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmGrpOrgHstVo extends CrmGrpOrgRelVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * <p>그룹조직이력코드</p> 
    */
    private String grpOrgHstCd;
    	
    /**
    * <p>삭제여부</p> 
    */
    private String delYn;
}
