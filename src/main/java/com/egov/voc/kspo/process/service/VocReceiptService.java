package com.egov.voc.kspo.process.service;

import com.egov.voc.kspo.common.service.VocAbstractService;
import com.egov.voc.kspo.process.dao.VocReceiptDao;
import com.egov.voc.sys.dao.ICrmDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
@Slf4j
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
