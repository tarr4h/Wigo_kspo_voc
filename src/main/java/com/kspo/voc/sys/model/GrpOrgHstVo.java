package com.kspo.voc.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrpOrgHstVo extends GrpOrgRelVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * <p>그룹조직이력코드</p> 
    */
    private String grpOrgHstId;
    	
    /**
    * <p>삭제여부</p> 
    */
    private String delYn;
}
