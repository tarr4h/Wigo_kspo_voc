package com.kspo.voc.kspo.setting.service;


import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.kspo.common.service.VocAbstractService;
import com.kspo.voc.kspo.common.stnd.CodeGeneration;
import com.kspo.voc.kspo.common.util.VocUtils;
import com.kspo.voc.kspo.setting.dao.VocPrcdCdBasDao;
import com.kspo.voc.kspo.setting.model.VocPrcdBasVo;
import com.kspo.voc.kspo.setting.model.VocTaskBasVo;
import com.kspo.voc.sys.dao.IVocDao;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
@Slf4j
public class VocPrcdCdBasService extends VocAbstractService {

    @Autowired
    VocPrcdCdBasDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public <T> List<T> selectProcedureCodeList(Map<String, Object> param) {
        return dao.selectList(param);
    }

    @Override
    public int insert(Object param) throws EgovBizException {
        param = VocUtils.setCodeSettingParam(param);
        String maxCd = dao.selectMaxCd();
        ((Map<String, Object>) param).put("prcdCd", CodeGeneration.generateCode(maxCd, CodeGeneration.PROCEDURE_BAS));
        return super.insert(param);
    }

    @Override
    public int update(Object param) throws EgovBizException {
        return super.update(param);
    }

    /**
     * 절차 row 삭제
     *  - 해당 절차가 task의 자동적용으로 설정되어 있다면, trigger를 통해 update됩니다.
     *  - 트리거명 : trig_voc_procedure_bas_delete
     * @param param
     * @return
     * @throws EgovBizException
     */
    @Override
    public int delete(Object param) throws EgovBizException {
        return super.delete(param);
    }

    public Object chngPrcdDuty(Map<String, Object> param) {
        return dao.chngPrcdDuty(param);
    }

    public Object updateDeadline(Map<String, Object> param) {
        Map<String, Object> returnMap = new HashMap<>();
        VocUtils.sumUpDeadline(param);

        int deadline = VocUtils.parseIntObject(param.get("ddlnSec"));
        log.debug("deadline = {}", deadline);

        dao.updateDeadline(param);
        returnMap.put("msg", "변경되었습니다.");
        returnMap.put("result", true);
        return returnMap;
    }
}
