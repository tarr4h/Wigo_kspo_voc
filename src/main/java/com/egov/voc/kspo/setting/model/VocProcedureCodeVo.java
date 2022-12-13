package com.egov.voc.kspo.setting.model;


import com.egov.base.common.model.BaseVo;
import com.egov.voc.kspo.common.util.VocUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocProcedureCodeVo extends BaseVo {

    private static final long serialVersionUID = -4401868272090337403L;
	private String prcdSeq;
    private String prcdNm;
    private String topComnCd;
    private String comnCd;
    private int deadline;
    private String dutyOrg;
    private String dutyOrgNm;
    private String dutyEmp;
    private String dutyEmpNm;
    private String dutyRole;
    private String dutyChngYn;
    private String regUseYn;
    private String recUseYn;
    private String regCompulsoryYn;
    private String recCompulsoryYn;
    private String taskYn;
    private String modDt;
    private String modUsr;



    public String getDeadlineConvert() {
        return VocUtils.convertDeadline(deadline);
    }

    public String getRequestCompulsoryYn(String target){
        if(target.equals("reg")){
            return this.getRegCompulsoryYn();
        } else if(target.equals("rec")){
            return this.getRecCompulsoryYn();
        }

        return null;
    }
}
