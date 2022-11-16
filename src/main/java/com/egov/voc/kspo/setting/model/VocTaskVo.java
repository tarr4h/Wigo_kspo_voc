package com.egov.voc.kspo.setting.model;

import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.util.VocUtils;
import lombok.Getter;
import lombok.Setter;

import javax.rmi.CORBA.Util;

@Getter
@Setter
public class VocTaskVo extends VocTaskCodeVo{

    private String mcTaskSeq;
    private String mcPrcdSeq;
    private String taskSeq;
    private String taskNm;
    private int odrg;
    private int deadline;
    private String dutyOrg;
    private String dutyEmp;
    private String dutyRole;
    private String regUsr;
    private String regDt;

    @Override
    public String getDutyOrgNm() {
        return VocUtils.getOrgNm(dutyOrg);
    }

    @Override
    public String getDutyEmpNm() {
        return VocUtils.getEmpNm(dutyEmp);
    }

    @Override
    public String getDeadlineConvert() {
        return VocUtils.convertDeadline(deadline);
    }
}
