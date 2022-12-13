package com.egov.voc.kspo.process.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egov.base.common.model.EzMap;
import com.egov.voc.kspo.common.service.VocAbstractService;
import com.egov.voc.kspo.common.stnd.PrcdCategory;
import com.egov.voc.kspo.common.stnd.PrcdStatus;
import com.egov.voc.kspo.process.dao.VocReceiptListDao;
import com.egov.voc.sys.dao.ICrmDao;

/**
 * <pre>
 * com.egov.voc.kspo.process.service.VocReceiptListService
 *  - VocReceiptListService.java
 * </pre>
 *
 * @ClassName     : VocReceiptListService
 * @description   :
 * @author        : tarr4h
 * @date          : 2022-12-05
 *
*/

@Service
public class VocReceiptListService extends VocAbstractService {

    @Autowired
    VocReceiptListDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }


    public <T> List<T> selectList(Map<String, Object> param) {
        setStatusCd(param, PrcdCategory.RECEIPT, PrcdStatus.STNDBY);
        return dao.selectList(param);
    }

    public int selectListCount(EzMap param) {
        return dao.selectListCount(param);
    }
}
