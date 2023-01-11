package com.kspo.voc.program.setting.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocMgmtCdMappVo extends VocMgmtCdVo {

    private static final long serialVersionUID = 5198406220369039570L;
	private String mappCd;
    private String mgmtCd;
    private String prntsMappCd;
    private int mappLvlNo;
    private String mgmtCdNm;
    private String regrId;
    private String regDt;
    private String amdrId;
    private String amdDt;

//    @Override
//    public String getId() {
//        return getMappCd();
//    }
//
//    @Override
//    public String getText() {
//        return getMgmtCdNm();
//    }
//
//    @Override
//    public String getParentId() {
//        return getPrntsMappCd();
//    }
//
//    @Override
//    public int getLevel() {
//        return getMappLvlNo();
//    }
//
//    @Override
//    public void setPrntsMgmtCdNm(String prntsCdNm) {
//        super.setPrntsMgmtCdNm(prntsCdNm);
//    }
//
//    @Override
//    public String getTopMgmtCd() {
//        return super.getTopMgmtCd();
//    }
//
//    @Override
//    public String getPrntsMgmtCd() {
//        return super.getPrntsMgmtCd();
//    }
//
//    @Override
//    public String getTopComnCd() {
//        return super.getTopComnCd();
//    }
//
//    @Override
//    public String getComnCd() {
//        return super.getComnCd();
//    }
//
//    @Override
//    public int getMgmtCdOrdr() {
//        return super.getMgmtCdOrdr();
//    }
//
//    @Override
//    public String getUseYn() {
//        return super.getUseYn();
//    }
//
//    @Override
//    public String getTopMgmtCdNm() {
//        return super.getTopMgmtCdNm();
//    }
//
//    @Override
//    public String getPrntsMgmtCdNm() {
//        return super.getPrntsMgmtCdNm();
//    }
//
//    @Override
//    public void setTopMgmtCd(String topCd) {
//        super.setTopMgmtCd(topCd);
//    }
//
//    @Override
//    public void setPrntsMgmtCd(String prntsCd) {
//        super.setPrntsMgmtCd(prntsCd);
//    }
//
//    @Override
//    public void setTopComnCd(String topComnCd) {
//        super.setTopComnCd(topComnCd);
//    }
//
//    @Override
//    public void setComnCd(String comnCd) {
//        super.setComnCd(comnCd);
//    }
//
//    @Override
//    public void setMgmtCdOrdr(int odrg) {
//        super.setMgmtCdOrdr(odrg);
//    }
//
//    @Override
//    public void setUseYn(String useYn) {
//        super.setUseYn(useYn);
//    }
//
//    @Override
//    public void setTopMgmtCdNm(String topCdNm) {
//        super.setTopMgmtCdNm(topCdNm);
//    }
}
