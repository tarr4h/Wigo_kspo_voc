package com.egov.voc.kspo.common.service;

import com.egov.voc.kspo.common.dao.VocComnDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmComnCdBaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.egov.voc.kspo.common.service.VocComnService
 *  - VocComnService.java
 * </pre>
 *
 * @ClassName     : VocComnService
 * @description   :
 * @author        : tarr4h
 * @date          : 2022-12-01
 *
*/

@Service
@Slf4j
public class VocComnService extends VocAbstractService{

    @Autowired
    VocComnDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public Object selectComnCdList(Map<String, Object> param) {
        log.debug("param = {}", param);

        StringBuilder sb = new StringBuilder();
        List<CrmComnCdBaseVo> comnCdList = dao.selectComnCdList(param);
        for(CrmComnCdBaseVo comnCd : comnCdList){
            String opt = "<option value='" + comnCd.getComnCd() + "' data-top-comn-cd='" + comnCd.getTopComnCd()  + "'>" + comnCd.getComnCdNm() + "</option>";
            sb.append(opt);
        }
        return sb;
    }
}
