package com.kspo.voc.kspo.common.stnd;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import com.kspo.base.common.model.EzMap;

@Getter
public enum ManageCodeCategory {

    CHANNEL("001", "channel", "채널"),
    TYPE("002", "type", "유형"),
    LOCATION("003", "location", "장소"),
    CAUSE("004", "cause", "원인"),
    TARGET("005", "target", "대상");
    
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
