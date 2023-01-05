package com.kspo.voc.program.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.program.common.dao.VocComnDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.model.EmpBaseVo;
import com.kspo.voc.sys.model.OrgBaseVo;

/**
 * <pre>
 * com.kspo.voc.program.common.service.VocComnService
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
    public IVocDao getDao() {
        return dao;
    }

    public <T> T selectComnCd(Map<String, Object> param){
        return dao.selectComnCd(param);
    }

    public <T> List<T> selectComnCdList(Map<String, Object> param) {
        return dao.selectComnCdList(param);
    }

    ///// 사원, 부서 관련
    public List<OrgBaseVo> selectOrgList(Map<String, Object> param){
        return dao.selectOrgList(param);
    }

    public List<EmpBaseVo> selectEmpList(EzMap param) {
        return dao.selectEmpList(param);
    }

    public OrgBaseVo selectOrg(String orgId) {
        return dao.selectOrg(orgId);
    }

    public EmpBaseVo selectEmp(String empId) {
        return dao.selectEmp(empId);
    }
}
