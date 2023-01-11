package com.kspo.voc.program.common.stnd;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import com.kspo.base.common.model.EzMap;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

@Getter
public enum MgmtCodeCategory {

    CHANNEL("VOC010", "001", "channel", "채널"),
    TYPE("VOC010", "002", "type", "유형"),
    TARGET("VOC010", "003", "target", "대상");
    
    private final String topComnCd;
    private final String comnCd;
    private final String caption;
    private final String footNote;

    MgmtCodeCategory(String topComnCd, String comnCd, String caption, String footNote) {
        this.topComnCd = topComnCd;
        this.comnCd = comnCd;
        this.caption = caption;
        this.footNote = footNote;
    };

    public static void setMgmtCdTreeMap(EzMap param, MgmtCodeCategory enumType){
        param.put("topComnCd", enumType.getTopComnCd());
        param.put("comnCd", enumType.getComnCd());
    }

    public static void setMgmtCdListTreeMap(EzMap param, List<MgmtCodeCategory> enumTypeList) throws EgovBizException{
        List<String> comnCdList = new ArrayList<>();
        String topComnCd = "";
        for(MgmtCodeCategory enumType : enumTypeList){
            comnCdList.add(enumType.getComnCd());

            if(topComnCd.equals("")){
                topComnCd = enumType.getTopComnCd();
            } else if(!topComnCd.equals(enumType.getTopComnCd())){
                throw new EgovBizException("같은 topComnCd의 mgmtCd만 지정해주세요.");
            }
        }
        param.put("topComnCd", topComnCd);
        param.put("comnCd", comnCdList);
    }
}
