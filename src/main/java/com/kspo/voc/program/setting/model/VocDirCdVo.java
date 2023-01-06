package com.kspo.voc.program.setting.model;

import com.kspo.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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


    private static final long serialVersionUID = -9092179687302744061L;
    private String dirCd;
    private String spDirYn;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;
}
