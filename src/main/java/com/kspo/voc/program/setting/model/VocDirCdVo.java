package com.kspo.voc.program.setting.model;

import com.kspo.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.kspo.voc.program.setting.model.VocDirCdVo
 *  - VocDirCdVo.java
 * </pre>
 *
 * @ClassName     : VocDirCdVo
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-05
 *
*/

@Getter
@Setter
public class VocDirCdVo extends BaseVo {

    private String dirCd;
    private String spDirYn;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;
}
