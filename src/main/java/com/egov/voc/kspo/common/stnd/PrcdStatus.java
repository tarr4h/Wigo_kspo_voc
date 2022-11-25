package com.egov.voc.kspo.common.stnd;

import lombok.Getter;

@Getter
public enum PrcdStatus {

    STNDBY("N", "대기"),
    ONGOING("S", "진행"),
    REJECT("R", "거절"),
    COMPLETE("Y", "완료" ),
    CLOSE("C", "종결");


    private String status;
    private String footnote;

    PrcdStatus(String status, String footnote) {
        this.status = status;
        this.footnote = footnote;
    }
}
