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
public class VocMgmtActvVo extends VocMgmtBaseVo {

    private String mgmtActvCd;
    private String actvCd;
    private String mgmtTaskCd;
    private int mgmtActvOrdr;
    private String actvNm;


    @Override
    public String getDutyOrgNm(){
        return super.getDutyOrgNm();
    }
}
