package com.egov.voc.kspo.setting.model;

import com.egov.voc.kspo.common.util.VocUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class VocProcedureVo extends VocProcedureCodeVo implements Comparable<VocProcedureVo>{

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

    public String getDutyOrgNm(){
        return VocUtils.getOrgNm(dutyOrg);
    }

    public String getDutyEmpNm(){
        return VocUtils.getEmpNm(dutyEmp);
    }

    @Override
    public String getDeadlineConvert(){
        return VocUtils.convertDeadline(deadline);
    }

    @Override
    public String getTaskYn() {
        return super.getTaskYn();
    }

    @Override
    public int compareTo(VocProcedureVo vo) {
        return this.prcdSeq.compareTo(vo.prcdSeq);
    }
}
