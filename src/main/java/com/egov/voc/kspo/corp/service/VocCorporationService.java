package com.egov.voc.kspo.corp.service;

import com.egov.base.common.model.EzMap;
import com.egov.voc.kspo.corp.dao.VocCorporationDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmComnCdBaseVo;
import com.egov.voc.sys.model.CrmEmpBaseVo;
import com.egov.voc.sys.model.CrmOrgBaseVo;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.extern.slf4j.Slf4j;
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

    public CrmOrgBaseVo selectOrg(String orgId) {
        return dao.selectOrg(orgId);
    }

    public CrmEmpBaseVo selectEmp(String empId) {
        return dao.selectEmp(empId);
    }
}
