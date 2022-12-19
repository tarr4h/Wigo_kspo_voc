package com.kspo.voc.kspo.process.model;

import com.kspo.base.common.model.BaseVo;
import com.kspo.voc.kspo.common.util.VocUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocRegistrationVo extends BaseVo {

    private static final long serialVersionUID = 9008776161302071248L;
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
