package com.kspo.voc.sys.model;

import com.kspo.base.common.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpBaseVo extends BaseVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * <p>사원ID</p> 
    */
    private String empId;
    /**
    * <p>조직ID</p> 
    */
    private String orgId;
    /**
    * <p>사원명</p> 
    */
    private String empNm;
    /**
    * <p>회사코드</p> 
    */
    private String cmpCd;
    /**
    * <p>직업ID</p> 
    */
    private String jobId;
    /**
    * <p>직무코드</p> 
    */
    private String dutyCd;
    
    /**
     * <p>사원등급코드</p> 
     */
    private String empGradeCd;
    /**
    * <p>전화번호</p> 
    */
    private String telNo;
    /**
    * <p>생년월일</p> 
    */
    private String birthday;
    /**
    * <p>이메일주소</p> 
    */
    private String emailAddr;
    /**
    * <p>상태코드</p> 
    */
    private String statusCd;
    /**
    * <p>입사년월일</p> 
    */
    private String encoYmd;
    /**
    * <p>퇴사년월일</p> 
    */
    private String retireYmd;
    /**
    * <p>사원유형코드</p> 
    */
    private String empTypeCd;
    /**
    * <p>이동전화번호</p> 
    */
    private String mphonNo;
    
    /**
     * <p>이동전화번호암호화</p> 
     */
     private String mphonNoEncVal;
    
}
