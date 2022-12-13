package com.egov.voc.kspo.process.model;

import com.egov.voc.kspo.setting.model.VocProcedureVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class VocRegPrcdVo extends VocProcedureVo {

    private static final long serialVersionUID = 235109878386090833L;
	private String regPrcdSeq;
    private String regSeq;
    private String mcPrcdSeq;
    private Date deadlineDt;
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
