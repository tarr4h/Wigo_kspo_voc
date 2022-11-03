package com.egov.voc.kspo.setting.service;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.common.VocUtils;
import com.egov.voc.kspo.setting.dao.VocTaskCodeSettingDao;
import com.egov.voc.kspo.setting.model.VocTaskCodeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VocTaskCodeSettingService {

    @Autowired
    VocTaskCodeSettingDao dao;

    public List<VocTaskCodeVo> selectTaskCodeList(EzMap param) {
        return dao.selectTaskCodeList(param);
    }

    public Object insertCode(Map<String, Object> param) {
        List<Map<String, Object>> formArr = (List<Map<String, Object>>) param.get("formArr");
        param = VocUtils.formSerializeArrayToMap(formArr);

        return dao.insertCode(param);
    }

    public Object saveRows(Map<String, Object> param) {
        return dao.saveRows(param);
    }

    public Object deleteRows(Map<String, Object> param) {
        return dao.deleteRows(param);
    }

    public Object chngTaskDuty(Map<String, Object> param) {
        return dao.chngTaskDuty(param);
    }
}
