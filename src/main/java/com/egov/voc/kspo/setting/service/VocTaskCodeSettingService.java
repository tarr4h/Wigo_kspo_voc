package com.egov.voc.kspo.setting.service;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.setting.dao.VocTaskCodeSettingDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VocTaskCodeSettingService extends AbstractCrmService {

    @Autowired
    VocTaskCodeSettingDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public <T> List<T> selectTaskCodeList(EzMap param) {
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

    public Object chngTaskDuty(Map<String, Object> param) {
        return dao.chngTaskDuty(param);
    }


    public <T> List<T> selectAvailablePrcdList(EzMap param) {
        return dao.selectAvailablePrcdList(param);
    }

    public List<EzMap> selectAppliedPrcd(EzMap param) {
        return dao.selectAppliedPrcd(param);
    }

    public Object updateAutoApplyPrcd(EzMap param) {
        return dao.updateAutoApplyPrcd(param);
    }

}
