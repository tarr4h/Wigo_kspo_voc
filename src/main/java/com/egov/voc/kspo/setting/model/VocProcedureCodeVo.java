package com.egov.voc.kspo.setting.model;


import com.egov.voc.base.common.model.BaseVo;
import com.egov.voc.kspo.common.util.VocUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocProcedureCodeVo extends BaseVo {

    private String prcdSeq;
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
    private String regUseYn;
    private String recUseYn;
    private String regCompulsoryYn;
    private String recCompulsoryYn;
    private String taskYn;
    private String modDt;
    private String modUsr;



    public String getDeadlineConvert() {
        return VocUtils.convertDeadline(deadline);
    }
}
