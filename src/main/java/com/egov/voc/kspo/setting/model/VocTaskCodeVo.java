package com.egov.voc.kspo.setting.model;


import com.egov.voc.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocTaskCodeVo extends BaseVo {

    private String taskSeq;
    private String taskNm;
    private int deadline;
    private String dutyDept;
    private String dutyDeptNm;
    private String dutyEmp;
    private String dutyEmpNm;
    private String dutyRole;
    private String chngYn;
    private String autoApplyYn;
    private String autoApplyAllYn;
    private String autoApplyPrcdSeq;
    private String autoApplyPrcdNm;
    private String modDt;
    private String modUsr;
}
