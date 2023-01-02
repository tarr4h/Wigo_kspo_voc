package com.kspo.voc.kspo.setting.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocMgmgCdMappVo extends VocMgmtCdVo {

    private static final long serialVersionUID = 5198406220369039570L;
	private String mappCd;
    private String mgmtCd;
    private String prntsMappCd;
    private int mappLvlNo;
    private String codeNm;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;

    @Override
    public String getId() {
        return getMappCd();
    }

    @Override
    public String getText() {
        return getCodeNm();
    }

    @Override
    public String getParentId() {
        return getPrntsMappCd();
    }

    @Override
    public int getLevel() {
        return getMappLvlNo();
    }

    @Override
    public void setPrntsCdNm(String prntsCdNm) {
        super.setPrntsCdNm(prntsCdNm);
    }

    @Override
    public String getTopMgmtCd() {
        return super.getTopMgmtCd();
    }

    @Override
    public String getPrntsMgmtCd() {
        return super.getPrntsMgmtCd();
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
    public int getMgmtCdOdrd() {
        return super.getMgmtCdOdrd();
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
    public void setTopMgmtCd(String topCd) {
        super.setTopMgmtCd(topCd);
    }

    @Override
    public void setPrntsMgmtCd(String prntsCd) {
        super.setPrntsMgmtCd(prntsCd);
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
    public void setMgmtCdOdrd(int odrg) {
        super.setMgmtCdOdrd(odrg);
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
