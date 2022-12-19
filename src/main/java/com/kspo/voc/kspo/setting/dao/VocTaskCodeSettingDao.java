package com.kspo.voc.kspo.setting.dao;



import java.util.List;
import java.util.Map;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface VocTaskCodeSettingDao extends IVocDao {

    int chngTaskDuty(Map<String, Object> param);

    <T> List<T> selectAvailablePrcdList(Map<String, Object> param);

    int updateAutoApplyPrcd(EzMap param);

    <T> List<T> selectAppliedPrcd(EzMap param);
}
