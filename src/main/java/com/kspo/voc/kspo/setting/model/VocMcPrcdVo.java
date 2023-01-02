package com.kspo.voc.kspo.setting.model;

import com.kspo.voc.kspo.common.util.VocUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocMcPrcdVo extends VocPrcdBasVo implements Comparable<VocMcPrcdVo>{

	private static final long serialVersionUID = 1L;
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
    public String getTaskUseYn() {
        return super.getTaskUseYn();
    }

    @Override
    public int compareTo(VocMcPrcdVo vo) {
        return this.prcdSeq.compareTo(vo.prcdSeq);
    }
}