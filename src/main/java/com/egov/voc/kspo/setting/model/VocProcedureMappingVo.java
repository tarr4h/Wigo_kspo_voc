package com.egov.voc.kspo.setting.model;

import com.egov.voc.base.common.model.AbstractTreeVo;
import com.egov.voc.base.common.model.ITreeVo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class VocProcedureMappingVo extends VocManagementCodeVo {

    private String mappingSeq;
    private String managementCd;
    private String codeNm;
    private String prntsMappingSeq;
    private int lvl;
    private String regUsr;
    private String regDt;

    @Override
    public String getId() {
        return getMappingSeq();
    }

    @Override
    public String getText() {
        return getCodeNm();
    }

    @Override
    public String getParentId() {
        return getPrntsMappingSeq();
    }

    @Override
    public int getLevel() {
        return getLvl();
    }

    @Override
    public void setPrntsCdNm(String prntsCdNm) {
        super.setPrntsCdNm(prntsCdNm);
    }

    @Override
    public String getTopCd() {
        return super.getTopCd();
    }

    @Override
    public String getPrntsCd() {
        return super.getPrntsCd();
    }

    @Override
    public String getTopComnCd() {
        return super.getTopComnCd();
    }

    @Override
    public String getComnCd() {
        return super.getComnCd();
    }

    @Override
    public int getOdrg() {
        return super.getOdrg();
    }

    @Override
    public String getUseYn() {
        return super.getUseYn();
    }

    @Override
    public String getTopCdNm() {
        return super.getTopCdNm();
    }

    @Override
    public String getPrntsCdNm() {
        return super.getPrntsCdNm();
    }

    @Override
    public void setTopCd(String topCd) {
        super.setTopCd(topCd);
    }

    @Override
    public void setPrntsCd(String prntsCd) {
        super.setPrntsCd(prntsCd);
    }

    @Override
    public void setTopComnCd(String topComnCd) {
        super.setTopComnCd(topComnCd);
    }

    @Override
    public void setComnCd(String comnCd) {
        super.setComnCd(comnCd);
    }

    @Override
    public void setOdrg(int odrg) {
        super.setOdrg(odrg);
    }

    @Override
    public void setUseYn(String useYn) {
        super.setUseYn(useYn);
    }

    @Override
    public void setTopCdNm(String topCdNm) {
        super.setTopCdNm(topCdNm);
    }
}
