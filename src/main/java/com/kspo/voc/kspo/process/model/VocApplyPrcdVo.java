package com.kspo.voc.kspo.process.model;

import com.kspo.voc.kspo.setting.model.VocMcPrcdVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class VocApplyPrcdVo extends VocMcPrcdVo {

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
    public VocApplyPrcdVo() {
    }

    public VocApplyPrcdVo(String vocPrcdSeq, String status) {
        this.vocPrcdSeq = vocPrcdSeq;
        this.status = status;
    }

}
