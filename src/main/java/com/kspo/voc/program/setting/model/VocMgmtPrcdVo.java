package com.kspo.voc.program.setting.model;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.kspo.voc.program.setting.model.VocMgmtPrcdVo
 *  - VocMgmtPrcdVo.java
 * </pre>
 *
 * @ClassName     : VocMgmtPrcdVo
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-06
 *
*/

@Getter
@Setter
public class VocMgmtPrcdVo extends VocPrcdBasVo {

    private String mgmtPrcdCd;
    private String prcdCd;
    private String mgmtPrcdOrdr;
    private int ddlnSec;
    private String dutyOrgId;
    private String dutyEmpId;
    private String dutyRoleCd;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;

}
