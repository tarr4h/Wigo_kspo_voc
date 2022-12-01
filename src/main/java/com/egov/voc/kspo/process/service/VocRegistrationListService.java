package com.egov.voc.kspo.process.service;

import com.egov.voc.kspo.process.dao.VocRegistrationListDao;
import com.egov.voc.kspo.common.service.VocAbstractService;
import com.egov.voc.sys.dao.ICrmDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * com.egov.voc.kspo.process.service.VocRegistrationListService
 *  - VocRegstrationService.java
 * </pre>
 *
 * @ClassName     : VocRegistrationListService
 * @description   : 등록 목록 service
 * @author        : tarr4h
 * @date          : 2022-11-30
 *
 */


@Service
@Slf4j
public class VocRegistrationListService extends VocAbstractService {

    @Autowired
    VocRegistrationListDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public <T> List<T> selectList(Object param) throws Exception {
        return dao.selectList(param);
    }
}
