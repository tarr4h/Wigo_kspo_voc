package com.kspo.voc.kspo.process.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.kspo.voc.kspo.setting.model.VocProcedureVo;

@Getter
@Setter
public class VocPrcdVo extends VocProcedureVo {

    private static final long serialVersionUID = 235109878386090833L;
	private String vocPrcdSeq;
    private String vocSeq;
    private String mcPrcdSeq;
    private Date deadlineDt;
    private String processOrg;
    private String processor;
    private String status;
    private String amdrId;
    private String amdDt;
    public VocPrcdVo() {
    }

    public VocPrcdVo(String vocPrcdSeq, String status) {
        this.vocPrcdSeq = vocPrcdSeq;
        this.status = status;
    }

}
