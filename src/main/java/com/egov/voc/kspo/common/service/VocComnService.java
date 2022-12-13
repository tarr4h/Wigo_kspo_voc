package com.egov.voc.kspo.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egov.base.common.model.EzMap;
import com.egov.voc.kspo.common.dao.VocComnDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmEmpBaseVo;
import com.egov.voc.sys.model.CrmOrgBaseVo;

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
public class VocComnService extends VocAbstractService{

    @Autowired
    VocComnDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public <T> T selectComnCd(Map<String, Object> param){
        return dao.selectComnCd(param);
    }

    public <T> List<T> selectComnCdList(Map<String, Object> param) {
        return dao.selectComnCdList(param);
    }

    ///// 사원, 부서 관련
    public List<CrmOrgBaseVo> selectOrgList(Map<String, Object> param){
        return dao.selectOrgList(param);
    }

    public List<CrmEmpBaseVo> selectEmpList(EzMap param) {
        return dao.selectEmpList(param);
    }

    public CrmOrgBaseVo selectOrg(String orgId) {
        return dao.selectOrg(orgId);
    }

    public CrmEmpBaseVo selectEmp(String empId) {
        return dao.selectEmp(empId);
    }
}
