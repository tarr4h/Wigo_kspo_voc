package com.kspo.voc.program.setting.model;

import com.kspo.voc.program.common.util.VocUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.kspo.voc.program.setting.model.VocMgmtActvVo
 *  - VocMgmtActvVo.java
 * </pre>
 *
 * @ClassName     : VocMgmtActvVo
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-06
 *
*/

@Getter
@Setter
public class VocMgmtActvVo extends VocActvBasVo {

    private String mgmtActvCd;
    private String actvCd;
    private String mgmtTaskCd;
    private String mgmtActvOrdr;
    private String dutyOrgId;
    private String dutyEmpId;
    private String dutyRoleCd;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;

    public String getDutyEmpNm(){
        return VocUtils.getEmpNm(dutyEmpId);
    }
    public String getDutyOrgNm(){
        return VocUtils.getOrgNm(dutyOrgId);
    }
}
