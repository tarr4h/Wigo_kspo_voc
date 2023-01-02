package com.kspo.voc.kspo.setting.model;


import com.kspo.base.common.model.BaseVo;
import com.kspo.voc.kspo.common.util.VocUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocPrcdBasVo extends BaseVo {

    private static final long serialVersionUID = -4401868272090337403L;
	private String prcdCd;
    private String prcdNm;
    private String refTopComnCd;
    private String refComnCd;
    private int ddlnSec;
    private String dutyOrgId;
    private String dutyOrgNm;
    private String dutyEmpId;
    private String dutyEmpNm;
    private String dutyRoleCd;
    private String dutyChngYn;
    private String vocApplyYn;
    private String vocDtlApplyYn;
    private String vocEstlApplyYn;
    private String vocDtlEstlApplyYn;
    private String taskUseYn;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;



    public String getDeadlineConvert() {
        return VocUtils.convertDeadline(ddlnSec);
    }

    public String getRequestEstlYn(String target){
        if(target.equals("voc")){
            return this.getVocEstlApplyYn();
        } else if(target.equals("vocDtl")){
            return this.getVocDtlEstlApplyYn();
        }

        return null;
    }
}
