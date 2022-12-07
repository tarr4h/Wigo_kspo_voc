package com.egov.voc.kspo.common.stnd;

import lombok.Getter;

/**
 * enum Class
 * <pre>
 * com.egov.voc.kspo.common.stnd.PrcdCategory
 *  - PrcdCategory.java
 * </pre>
 *
 * @ClassName     : PrcdCategory
 * @description   :
 * @author        : tarr4h
 * @date          : 2022-12-01
 *
*/

@Getter
public enum PrcdCategory {

    REGISTRATION("001", "등록"),
    ASSIGN("002", "배정"),
    RECEIPT("003", "접수"),
    PROCESSING("004", "처리"),
    COMPLETE("005", "완료"),
    CONCLUSION("006", "종결");

    private String topComnCd;
    private String comnCd;
    private String caption;

    private String topCd = "VOC020";

    PrcdCategory(String comnCd, String caption) {
        this.topComnCd = topCd;
        this.comnCd = comnCd;
        this.caption = caption;
    }
}
