package com.egov.voc.kspo.setting.model;

import com.egov.voc.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class VocProcedureVo extends BaseVo {

    private String mcPrcdSeq;
    private String prntsSeq;
    private String prcdSeq;
    private String prcdNm;
    private int deadline;
    private String dutyOrg;
    private String dutyEmp;
    private String dutyRole;
    private String regUsr;
    private String regDt;
}
