package com.egov.voc.kspo.process.service;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.process.dao.VocIntergrationListDao;
import com.egov.voc.kspo.setting.service.VocAbstractService;
import com.egov.voc.sys.dao.ICrmDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VocIntergrationListService extends VocAbstractService {

    @Autowired
    VocIntergrationListDao dao;

    @Override
    public ICrmDao getDao() {
        return null;
    }

    public <T> List<T> selectProcedureCodeList(EzMap param) {
        return dao.selectList(param);
    }

}
