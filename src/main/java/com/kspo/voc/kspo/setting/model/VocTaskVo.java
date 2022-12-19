package com.kspo.voc.kspo.setting.model;

import com.kspo.voc.kspo.common.util.VocUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocTaskVo extends VocTaskCodeVo{

    private static final long serialVersionUID = 7276553724431526965L;
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
