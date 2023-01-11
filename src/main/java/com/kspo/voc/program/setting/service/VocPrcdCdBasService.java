package com.kspo.voc.program.setting.service;


import com.kspo.voc.program.common.service.VocAbstractService;
import com.kspo.voc.program.common.stnd.CodeGeneration;
import com.kspo.voc.program.common.util.VocUtils;
import com.kspo.voc.program.setting.dao.VocPrcdCdBasDao;
import com.kspo.voc.sys.dao.IVocDao;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public int insert(Map<String, Object> param) throws EgovBizException {
        int ddlnSec = VocUtils.sumUpDeadline(param);
        param.put("ddlnSec", ddlnSec);

        String maxCd = dao.selectMaxCd();
        param.put("prcdCd", CodeGeneration.generateCode(maxCd, CodeGeneration.PROCEDURE_BAS));

        return dao.insert(param);
    }

    public Object chngPrcdDuty(Map<String, Object> param) {
        return dao.chngPrcdDuty(param);
    }

    public Object updateDeadline(Map<String, Object> param) {
        Map<String, Object> returnMap = new HashMap<>();
        int ddlnSec = VocUtils.sumUpDeadline(param);
        param.put("ddlnSec", ddlnSec);

        dao.updateDeadline(param);
        returnMap.put("msg", "변경되었습니다.");
        returnMap.put("result", true);
        return returnMap;
    }
}
