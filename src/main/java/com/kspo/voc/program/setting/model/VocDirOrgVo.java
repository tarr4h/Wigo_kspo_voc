package com.kspo.voc.program.setting.model;

import com.kspo.voc.program.common.util.VocUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.kspo.voc.program.setting.model.VocDirOrgVo
 *  - VocDirOrgVo.java
 * </pre>
 *
 * @ClassName     : VocDirOrgVo
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-05
 *
*/

@Getter
@Setter
public class VocDirOrgVo extends VocDirCdVo {

    @Override
    public String getDirCd() {
        return super.getDirCd();
    }
    @Override
    public void setDirCd(String dirCd) {
        super.setDirCd(dirCd);
    }

    private String primProcOrgYn;
    private String orgId;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;

    public String getOrgNm(){
        return VocUtils.getOrgNm(this.orgId);
    }
}
