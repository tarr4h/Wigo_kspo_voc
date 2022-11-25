package com.egov.voc.kspo.process.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class VocRegPrcdVo {

    private String regPrcdSeq;
    private String regSeq;
    private String mcPrcdSeq;
    private Date deadline;
    private String status;
    private String modUsr;
    private String modDt;

    public VocRegPrcdVo() {
    }

    public VocRegPrcdVo(String regPrcdSeq, String status) {
        this.regPrcdSeq = regPrcdSeq;
        this.status = status;
    }
}
