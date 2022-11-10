package com.egov.voc.kspo.common.stnd;

import com.egov.voc.base.common.model.EzMap;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ManageCodeCategoryEnum {

    REGISTRATION("001", "등록 절차"),
    RECEIPT("002", "접수 절차"),
    DETAIL("003", "세부 절차");

    private String topComnCd;
    private String comnCd;
    private String footNote;
    private static final String topCd = "VOC010";

    ManageCodeCategoryEnum(String comnCd, String footNote) {
        this.topComnCd = topCd;
        this.comnCd = comnCd;
        this.footNote = footNote;
    };

    public static void setComnCdTreeMap(EzMap param, ManageCodeCategoryEnum enumType){
        param.put("topComnCd", enumType.getTopComnCd());
        param.put("comnCd", enumType.getComnCd());
    }

    public static void setComnCdListTreeMap(EzMap param, List<ManageCodeCategoryEnum> enumTypeList){
        List<String> comnCdList = new ArrayList<>();
        for(ManageCodeCategoryEnum enumType : enumTypeList){
            comnCdList.add(enumType.getComnCd());
        }
        param.put("topComnCd", topCd);
        param.put("comnCd", comnCdList);
    }
}
