package com.kspo.voc.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrpMenuHstVo extends GrpMenuRelVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * <p>그룹메뉴이력코드</p> 
    */
    private String grpMenuHstId;
    
    /**
    * <p>삭제여부</p> 
    */
    private String delYn;
    
    
}
