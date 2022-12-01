package com.egov.voc.kspo.common.stnd;

import com.egov.base.common.model.EzMap;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ManageCodeCategory {

    REGISTRATION("001", "reg", "등록 절차"),
    RECEIPT("002", "rec", "접수 절차"),
    DETAIL("003", "det", "세부 절차");

    private String topComnCd;
    private String comnCd;
    private String caption;
    private String footNote;
    private static final String topCd = "VOC010";

    ManageCodeCategory(String comnCd, String caption, String footNote) {
        this.topComnCd = topCd;
        this.comnCd = comnCd;
        this.caption = caption;
        this.footNote = footNote;
    };

    public static void setComnCdTreeMap(EzMap param, ManageCodeCategory enumType){
        param.put("topComnCd", enumType.getTopComnCd());
        param.put("comnCd", enumType.getComnCd());
    }

    public static void setComnCdListTreeMap(EzMap param, List<ManageCodeCategory> enumTypeList){
        List<String> comnCdList = new ArrayList<>();
        for(ManageCodeCategory enumType : enumTypeList){
            comnCdList.add(enumType.getComnCd());
        }
        param.put("topComnCd", topCd);
        param.put("comnCd", comnCdList);
    }
}
