package com.egov.voc.kspo.setting.dao;



import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.setting.model.VocTaskCodeVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface VocTaskCodeSettingDao extends ICrmDao {

    int chngTaskDuty(Map<String, Object> param);

    <T> List<T> selectAvailablePrcdList(Map<String, Object> param);

    int updateAutoApplyPrcd(EzMap param);

    <T> List<T> selectAppliedPrcd(EzMap param);
}
