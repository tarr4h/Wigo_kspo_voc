package com.egov.voc.kspo.process.service;

import com.egov.base.common.model.EzMap;
import com.egov.voc.kspo.process.dao.VocIntergrationListDao;
import com.egov.voc.kspo.common.service.VocAbstractService;
import com.egov.voc.sys.dao.ICrmDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
@Slf4j
public class VocIntergrationListService extends VocAbstractService {

    @Autowired
    VocIntergrationListDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public <T> List<T> selectProcedureCodeList(EzMap param) {
        return dao.selectList(param);
    }

}
