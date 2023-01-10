package com.kspo.voc.program.setting.model;

import com.kspo.voc.program.common.util.VocUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.kspo.voc.program.setting.model.VocMgmtPrcdVo
 *  - VocMgmtPrcdVo.java1
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
public class VocMgmtTaskVo extends VocMgmtBaseVo {

    private String mgmtTaskCd;
    private String taskCd;
    private String mgmtPrcdCd;
    private int mgmtTaskOrdr;
    private String taskNm;

    @Override
    public String getDutyOrgNm(){
        return super.getDutyOrgNm();
    }

    @Override
    public int getChildCnt() {
        return super.getChildCnt();
    }
}
