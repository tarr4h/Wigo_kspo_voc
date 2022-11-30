package com.egov.voc.kspo.setting.model;


import com.egov.base.common.model.BaseVo;
import com.egov.voc.kspo.common.util.VocUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class VocTaskCodeVo extends BaseVo {

    private String taskSeq;
    private String taskNm;
    private int deadline;
    private String dutyOrg;
    private String dutyOrgNm;
    private String dutyEmp;
    private String dutyEmpNm;
    private String dutyRole;
    private String useYn;
    private String dutyChngYn;
    private String autoApplyYn;
    private String autoApplyAllYn;
    private String autoApplyPrcdSeq;
    private String autoApplyPrcdNm;
    private String modDt;
    private String modUsr;


    public String getDeadlineConvert() {
        return VocUtils.convertDeadline(deadline);
    }
}
