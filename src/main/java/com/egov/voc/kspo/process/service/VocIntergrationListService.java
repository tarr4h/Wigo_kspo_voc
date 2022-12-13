package com.egov.voc.kspo.process.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egov.base.common.model.EzMap;
import com.egov.voc.kspo.common.service.VocAbstractService;
import com.egov.voc.kspo.process.dao.VocIntergrationListDao;
import com.egov.voc.sys.dao.ICrmDao;

/**
 * <pre>
 * com.egov.voc.kspo.process.service.VocIntergrationListService
 *  - VocIntergrationListService.java
 * </pre>
 *
 * @ClassName     : VocIntergrationListService
 * @description   : 통합목록 service
 * @author        : tarr4h
 * @date          : 2022-11-30
 *
 */
@Service
public class VocIntergrationListService extends VocAbstractService {

    @Autowired
    VocIntergrationListDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public <T> List<T> selectList(EzMap param) {
        return dao.selectList(param);
    }

    public int selectListCount(EzMap param) {
        return dao.selectListCount(param);
    }
}
