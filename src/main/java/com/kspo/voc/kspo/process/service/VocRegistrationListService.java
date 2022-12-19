package com.kspo.voc.kspo.process.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.kspo.common.service.VocAbstractService;
import com.kspo.voc.kspo.common.stnd.PrcdCategory;
import com.kspo.voc.kspo.common.stnd.PrcdStatus;
import com.kspo.voc.kspo.process.dao.VocRegistrationListDao;
import com.kspo.voc.sys.dao.IVocDao;

/**
 * <pre>
 * com.kspo.voc.kspo.process.service.VocRegistrationListService
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
public class VocRegistrationListService extends VocAbstractService {

    @Autowired
    VocRegistrationListDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public <T> List<T> selectList(Map<String, Object> param) throws EgovBizException {
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
