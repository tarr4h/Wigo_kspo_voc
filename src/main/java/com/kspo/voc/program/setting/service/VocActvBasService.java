package com.kspo.voc.program.setting.service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.program.common.service.VocAbstractService;
import com.kspo.voc.program.common.stnd.CodeGeneration;
import com.kspo.voc.program.common.util.VocUtils;
import com.kspo.voc.program.setting.dao.VocActvBasDao;
import com.kspo.voc.sys.dao.IVocDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.program.setting.service.VocActvBasService
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

    public Object insert(EzMap param) {
        String maxCd = dao.selectMaxCd();
        param.put("actvCd", CodeGeneration.generateCode(maxCd, CodeGeneration.ACTIVITY_BAS));

        return dao.insert(param);
    }
}
