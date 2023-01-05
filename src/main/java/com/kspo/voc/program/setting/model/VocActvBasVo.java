package com.kspo.voc.program.setting.model;

import com.kspo.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.kspo.voc.program.setting.model.VocActvBasVo
 *  - VocActvBasVo.java
 * </pre>
 *
 * @ClassName     : VocActvBasVo
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-03
 *
*/

@Getter
@Setter
public class VocActvBasVo extends BaseVo {

    private String actvCd;
    private String actvNm;
    private String funcTpCd;
    private String callMthdCd;
    private String actvExpln;
    private String useYn;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;
}
