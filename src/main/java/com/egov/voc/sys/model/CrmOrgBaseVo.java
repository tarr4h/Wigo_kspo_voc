package com.egov.voc.sys.model;

import com.egov.voc.base.common.model.AbstractTreeVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmOrgBaseVo extends AbstractTreeVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * <p>조직ID</p> 
    */
    private String orgId;
    /**
    * <p>조직명</p> 
    */
    private String orgNm;
    /**
    * <p>조직분류ID</p> 
    */
    private String orgClassId;
    /**
    * <p>조직분류명</p> 
    */
    private String orgClassNm;
    /**
    * <p>레벨번호</p> 
    */
    private Integer lvlNo;
    /**
    * <p>조직순번</p> 
    */
    private Integer orgOdrg;
    /**
    * <p>상위조직ID</p> 
    */
    private String upOrgId;
    /**
    * <p>상위조직명</p> 
    */
    private String upOrgNm;
    /**
    * <p>조직사원ID</p> 
    */
    private String orgEmpId;
    /**
    * <p>조직사원명</p> 
    */
    private String orgEmpNm;
    /**
    * <p>시작년월일</p> 
    */
    private String staYmd;
    /**
    * <p>종료년월일</p> 
    */
    private String endYmd;
	@Override
	public String getId() {
		return getOrgId();
	}
	@Override
	public String getText() {
		return getOrgNm();
	}
	@Override
	public String getParentId() {
		return getUpOrgId();
	}
	@Override
	public int getLevel() {
		if(orgOdrg != null)
			return orgOdrg;
		return super.getLevel();
	}
}
