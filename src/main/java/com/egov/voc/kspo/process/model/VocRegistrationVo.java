package com.egov.voc.kspo.process.model;

import com.egov.base.common.model.BaseVo;
import com.egov.voc.kspo.common.util.VocUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class VocRegistrationVo extends BaseVo {

    private String regSeq;
    private String channel;
    private String statusCd;
    private String title;
    private String content;
    private String regComment;
    private String custId;
    private String issueYn;
    private String issueGrade;
    private String regUsr;
    private String regOrg;
    private String regDt;
    private String recUsr;
    private String recOrg;
    private String recDt;
    private String ownershipOrg;
    private String delYn;

    private String statusCdNm;


    public String getRegUsrNm(){
        return VocUtils.getEmpNm(regUsr);
    }


}
