package com.egov.voc.kspo.setting.model;

import com.egov.voc.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocBaseVo extends BaseVo {

    private int deadline;
    private String deadlineConvert;


    public String getDeadlineConvert() {
        return String.valueOf(deadline + 1);
    }
}
