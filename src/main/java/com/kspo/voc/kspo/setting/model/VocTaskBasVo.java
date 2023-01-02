package com.kspo.voc.kspo.setting.model;


import com.kspo.base.common.model.BaseVo;
import com.kspo.voc.kspo.common.util.VocUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Slf4j
public class VocTaskBasVo extends BaseVo {

    private static final long serialVersionUID = 8950672182287295915L;
	private String taskCd;
    private String taskNm;
    private int ddlnSec;
    private String dutyOrgId;
    private String dutyOrgNm;
    private String dutyEmpId;
    private String dutyEmpNm;
    private String dutyRoleCd;
    private String useYn;
    private String dutyChngYn;
    private String autoApplyPrcdYn;
    private String autoApplyAllPrcdYn;
    private String autoApplyPrcdCd;
    private String autoApplyPrcdNm;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;


    public String getDeadlineConvert() {
        return VocUtils.convertDeadline(ddlnSec);
    }
}
