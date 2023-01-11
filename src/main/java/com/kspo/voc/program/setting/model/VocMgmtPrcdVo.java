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
public class VocMgmtPrcdVo extends VocMgmtBaseVo {

    private String mgmtPrcdCd;
    private String prcdCd;
    private int mgmtPrcdOrdr;
    private String prcdNm;

}
