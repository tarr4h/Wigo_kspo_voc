package com.egov.voc.kspo.setting.dao;



import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.setting.model.VocTaskCodeVo;
import com.egov.voc.sys.mapper.CrmMapper;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface VocTaskCodeSettingDao {
    List<VocTaskCodeVo> selectTaskCodeList(EzMap param);

    int insertCode(Map<String, Object> param);

    int saveRows(Map<String, Object> param);

    int deleteRows(Map<String, Object> param);

    int chngTaskDuty(Map<String, Object> param);
}
