package com.kspo.voc.program.setting.model;

import com.kspo.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.kspo.voc.program.setting.model.VocDirMgmtVo
 *  - VocDirMgmtVo.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : VocDirMgmtVo
 * @description :
 * @date : 2023-01-09
 */
@Getter
@Setter
public class VocDirMgmtVo extends BaseVo {

    /* 경로코드 */
    private String dirCd;
    /* 관리코드 */
    private String mgmtCd;
}
