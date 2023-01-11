package com.kspo.voc.program.setting.model;

import com.kspo.base.common.model.BaseVo;
import com.kspo.voc.program.common.util.VocUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.kspo.voc.program.setting.model.VocMgmtBaseVo
 *   - VocMgmtBaseVo.java
 * </pre>
 *
 * @author : 한태우
 * @ClassName : VocMgmtBaseVo
 * @description :
 * @date : 2023/01/10
 */
@Getter
@Setter
public class VocMgmtBaseVo extends BaseVo {

    private int ddlnSec;
    private String dutyOrgId;
    private String dutyEmpId;
    private String dutyRoleCd;
//    private String regrId;
//    private String regDt;
//    private String amdrId;
//    private String amdDt;

    private int childCnt;

    public String getDutyOrgNm(){
        return VocUtils.getOrgNm(dutyOrgId);
    }
}
