package com.egov.voc.kspo.process.service;

import com.egov.base.common.model.EzMap;
import com.egov.voc.kspo.common.service.VocAbstractService;
import com.egov.voc.kspo.common.stnd.PrcdCategory;
import com.egov.voc.kspo.common.stnd.PrcdStatus;
import com.egov.voc.kspo.process.dao.VocRegistrationListDao;
import com.egov.voc.sys.dao.ICrmDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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


@SuppressWarnings("unchecked")
@Service
@Slf4j
public class VocRegistrationListService extends VocAbstractService {

    @Autowired
    VocRegistrationListDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public <T> List<T> selectList(Map<String, Object> param) throws Exception {
        setStatusCd(param, PrcdCategory.REGISTRATION, Arrays.asList(PrcdStatus.ONGOING, PrcdStatus.COMPLETE));
//        setStatusCd(param, PrcdCategory.REGISTRATION, PrcdStatus.ONGOING);
        return dao.selectList(param);
    }

    public int selectListCount(EzMap param) {
        return dao.selectListCount(param);
    }

    public Object temporaryApproval(Map<String, Object> param) {
        List<Map<String, Object>> rows = (List<Map<String, Object>>) param.get("rows");
        int result = 0;
        for(Map<String, Object> row : rows){
            result += updateY2N(row);
        }

        return result;
    }
}
