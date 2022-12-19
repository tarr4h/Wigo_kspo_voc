package com.kspo.voc.sys.model;


import com.kspo.base.common.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBaseVo extends BaseVo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /**
    * <p>사용자코드</p> 
    */
    private String userId;
    /**
    * <p>로그인ID</p> 
    */
    private String loginId;
    /**
    * <p>로그인비밀번호</p> 
    */
    private String loginPwd;
    /**
    * <p>사용자명</p> 
    */
    private String userNm;
    /**
    * <p>이메일주소</p> 
    */
    private String emailAddr;
    /**
    * <p>이동전화번호</p> 
    */
    private String mphonNo;
    /**
    * <p>사용자성별코드</p> 
    * <p>공통코드 : S040</p> 
    */
    private String userGndrCd;
    /**
    * <p>사용자생년월일</p> 
    */
    private String userBirthday;
    /**
    * <p>비밀번호수정일시</p> 
    */
    private String pwdAmdDt;
    /**
    * <p>최종로그인일시</p> 
    */
    private String lastLoginDt;
    /**
    * <p>비밀번호만료일시</p> 
    */
    private String pwdExpDt;
    /**
    * <p>로그인실패수</p> 
    */
    private Integer loginFailCnt;
    /**
    * <p>삭제여부</p> 
    * <p>공통코드 : S030</p> 
    */
    private String delYn;
}
