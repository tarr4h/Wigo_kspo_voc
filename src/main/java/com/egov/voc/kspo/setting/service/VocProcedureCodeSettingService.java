package com.egov.voc.kspo.setting.service;


import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.common.VocUtils;
import com.egov.voc.kspo.setting.dao.VocProcedureCodeSettingDao;
import com.egov.voc.kspo.setting.model.VocProcedureCodeVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VocProcedureCodeSettingService extends AbstractCrmService {

    @Autowired
    VocProcedureCodeSettingDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public List<VocProcedureCodeVo> selectProcedureCodeList(EzMap param) {
        return dao.selectList(param);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int insert(Object param) throws Exception {
        List<Map<String, Object>> formArr = (List<Map<String, Object>>) ((Map<String, Object>) param).get("formArr");
        param = VocUtils.formSerializeArrayToMap(formArr);
        return super.insert(param);
    }

    @Override
    public int update(Object param) throws Exception {
        return super.update(param);
    }

    @Override
    public int delete(Object param) throws Exception {
        return super.delete(param);
    }

    @SuppressWarnings("unchecked")
    public Object chngProcedureDuty(Map<String, Object> param) {
        log.debug("param = {}", param);
        return dao.chngProcedureDuty(param);
    }


}
