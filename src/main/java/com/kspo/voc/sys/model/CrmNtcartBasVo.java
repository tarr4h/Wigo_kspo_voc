package com.kspo.voc.sys.model;

import com.kspo.base.common.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmNtcartBasVo extends BaseVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * <p>게시물ID</p> 
    */
    private String ntcartId;
    /**
    * <p>게시판ID</p> 
    */
    private String brdId;
    /**
    * <p>상위게시물ID</p> 
    */
    private String upNtcartId;
    /**
    * <p>최상위게시물ID</p> 
    */
    private String topNtcartId;
    /**
    * <p>게시물명</p> 
    */
    private String ntcartNm;
    /**
    * <p>게시물내용</p> 
    */
    private String ntcartCtnts;
    /**
    * <p>답변내용</p> 
    */
    private String answerCtnts;
    /**
    * <p>공지상태코드</p> 
    */
    private String noteStatusCd;
    /**
    * <p>게시상태코드</p> 
    */
    private String postStatusCd;
    /**
    * <p>공지여부</p> 
    */
    private String noteYn;
    /**
    * <p>게시시작일시</p> 
    */
    private String postStaDt;
    /**
    * <p>게시종료일시</p> 
    */
    private String postEndDt;
    /**
    * <p>공지시작일시</p> 
    */
    private String noteStaDt;
    /**
    * <p>공지종료일시</p> 
    */
    private String noteEndDt;
    /**
    * <p>파일코드</p> 
    */
    private String fileCd;
    /**
    * <p>게시물비밀번호</p> 
    */
    private String ntcartPwd;
}
