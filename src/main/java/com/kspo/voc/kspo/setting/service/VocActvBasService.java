package com.kspo.voc.kspo.setting.service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.kspo.common.service.VocAbstractService;
import com.kspo.voc.kspo.common.stnd.CodeGeneration;
import com.kspo.voc.kspo.common.util.VocUtils;
import com.kspo.voc.kspo.setting.dao.VocActvBasDao;
import com.kspo.voc.sys.dao.IVocDao;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.kspo.setting.service.VocActvBasService
 *  - VocActvBasService.java
 * </pre>
 *
 * @ClassName     : VocActvBasService
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-03
 *
*/

@SuppressWarnings("unchecked")
@Service
@Slf4j
public class VocActvBasService extends VocAbstractService {

    @Autowired
    VocActvBasDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public Object insertActv(EzMap param) {
        List<Map<String, Object>> frmArr = (List<Map<String, Object>>) param.get("frmArr");
        Map<String, Object> map = VocUtils.formSerializeArrayToMap(frmArr);

        String maxCd = dao.selectMaxCd();
        map.put("actvCd", CodeGeneration.generateCode(maxCd, CodeGeneration.ACTIVITY_BAS));

        return dao.insert(map);
    }
}
