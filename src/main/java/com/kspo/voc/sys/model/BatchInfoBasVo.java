package com.kspo.voc.sys.model;

import com.kspo.base.common.model.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName    TbSysBatchInfoBasVo
 * @author    user
 * @date    2022. 12. 22.
 * @Version    1.0
 * @description    배치정보기본 Vo
 * @Company    Copyright ⓒ wigo.ai. All Right Reserved
 */
@Getter
@Setter
public class BatchInfoBasVo extends BaseVo {
    /**
    *
    */
private static final long serialVersionUID = 1L;
    /**
    * 배치코드 
    */
    private String batchId;
    /**
    * 시스템코드 
    */
    private String sysCd;
    /**
    * 배치명 
    */
    private String batchNm;
    /**
    * 작업ID 
    */
    private String wrkId;
    /**
    * 최종실행일시 
    */
    private String lastExecDt;
    /**
    * 최종성공여부 
    */
    private String lastSuccYn;
    /**
    * 사용여부 
    */
    private String useYn;
}
