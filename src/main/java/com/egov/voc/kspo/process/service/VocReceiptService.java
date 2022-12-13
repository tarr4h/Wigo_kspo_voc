package com.egov.voc.kspo.process.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egov.voc.kspo.common.service.VocAbstractService;
import com.egov.voc.kspo.process.dao.VocReceiptDao;
import com.egov.voc.sys.dao.ICrmDao;

/**
 * <pre>
 * com.egov.voc.kspo.process.service.VocReceiptService
 *  - VocReceiptService.java
 * </pre>
 *
 * @ClassName     : VocReceiptService
 * @description   :
 * @author        : tarr4h
 * @date          : 2022-12-05
 *
*/

@Service
public class VocReceiptService extends VocAbstractService {

    @Autowired
    VocReceiptDao dao;


    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public <T> T selectRegstration(Map<String, Object> param) {
        return dao.selectRegistration(param);
    }
}
