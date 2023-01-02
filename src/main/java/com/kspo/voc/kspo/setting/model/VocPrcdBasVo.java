package com.kspo.voc.kspo.setting.model;


import com.kspo.base.common.model.BaseVo;
import com.kspo.voc.kspo.common.util.VocUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocPrcdBasVo extends BaseVo {

    private static final long serialVersionUID = -4401868272090337403L;
	private String prcdId;
    private String prcdNm;
    private String topComnCd;
    private String comnCd;
    private int deadline;
    private String dutyOrg;
    private String dutyOrgNm;
    private String dutyEmp;
    private String dutyEmpNm;
    private String dutyRole;
    private String dutyChngYn;
    private String vocUseYn;
    private String vocDtlUseYn;
    private String vocCompulsoryYn;
    private String vocDtlCompulsoryYn;
    private String taskYn;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;



    public String getDeadlineConvert() {
        return VocUtils.convertDeadline(deadline);
    }

    public String getRequestCompulsoryYn(String target){
        if(target.equals("voc")){
            return this.getVocCompulsoryYn();
        } else if(target.equals("vocDtl")){
            return this.getVocDtlCompulsoryYn();
        }

        return null;
    }
}
