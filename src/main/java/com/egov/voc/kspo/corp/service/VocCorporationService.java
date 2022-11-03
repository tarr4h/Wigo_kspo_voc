package com.egov.voc.kspo.corp.service;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.corp.dao.VocCorporationDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmEmpBaseVo;
import com.egov.voc.sys.model.CrmOrgBaseVo;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("corpService")
@Slf4j
public class VocCorporationService extends AbstractCrmService {


    @Autowired
    VocCorporationDao dao;
    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public List<CrmOrgBaseVo> selectOrgList(Map<String, Object> param){
        return dao.selectOrgList(param);
    }

    public List<CrmEmpBaseVo> selectEmpList(EzMap param) {
        return dao.selectEmpList(param);
    }



}
