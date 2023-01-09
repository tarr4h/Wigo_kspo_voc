package com.kspo.voc.program.setting.model;

import com.kspo.base.common.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.kspo.voc.program.setting.model.VocActvBasVo
 *  - VocActvBasVo.java
 * </pre>
 *
 * @ClassName     : VocActvBasVo
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-03
 *
*/

@Getter
@Setter
public class VocActvBasVo extends BaseVo {

    /**
     * <p>수행코드</p>
     */
    private String actvCd;
    /**
     * <p>수행명</p>
     */
    private String actvNm;
    /**
     * <p>기능유형코드</p>
     */
    private String funcTpCd;
    /**
     * <p>호출메소드코드</p>
     */
    private String callMthdCd;
    /**
     * <p>수행설명</p>
     */
    private String actvExpln;
    /**
     * <p>사용여부</p>
     */
    private String useYn;
    /**
     * <p>등록자 아이디</p>
     */
    private String regrId;
    /**
     * 등록일시
     */
    private String regDt;
    /**
     * 수정자 아이디
     */
    private String amdrId;
    /**
     * 수정일시
     */
    private String amdDt;
}
