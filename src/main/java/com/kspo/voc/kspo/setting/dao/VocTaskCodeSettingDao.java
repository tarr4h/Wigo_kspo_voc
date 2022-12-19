package com.kspo.voc.kspo.setting.dao;



import java.util.List;
import java.util.Map;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface VocTaskCodeSettingDao extends ICrmDao {

    int chngTaskDuty(Map<String, Object> param);

    <T> List<T> selectAvailablePrcdList(Map<String, Object> param);

    int updateAutoApplyPrcd(EzMap param);

    <T> List<T> selectAppliedPrcd(EzMap param);
}
