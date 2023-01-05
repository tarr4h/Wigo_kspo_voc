package com.kspo.voc.program.setting.service;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.program.common.service.VocAbstractService;
import com.kspo.voc.program.common.stnd.ManageCodeCategory;
import com.kspo.voc.program.setting.dao.VocMgmtPrcdDao;
import com.kspo.voc.sys.dao.IVocDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.program.setting.service.VocMgmtPrcdService
 *  - VocMgmtPrcdService.java
 * </pre>
 *
 * @ClassName     : VocMgmtPrcdService
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-05
 *
*/

@Service
@Slf4j
public class VocMgmtPrcdService extends VocAbstractService {

    @Autowired
    VocMgmtPrcdDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public Object vocMgmtCdTree(EzMap param) {
        ManageCodeCategory.setComnCdTreeMap(param, ManageCodeCategory.CHANNEL);
        return AbstractTreeVo.makeHierarchy(selectVocMgmtCdTree(param));
    }

    public Object selectDirCd(Map<String, Object> param) {
        return dao.selectDirCd(param);
    }
}
