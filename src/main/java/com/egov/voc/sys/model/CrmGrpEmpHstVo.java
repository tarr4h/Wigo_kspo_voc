package com.egov.voc.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmGrpEmpHstVo extends CrmGrpEmpRelVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * <p>그룹사원이력코드</p> 
    */
    private String grpEmpHstCd;
    
    /**
    * <p>삭제여부</p> 
    */
    private String delYn;
}
