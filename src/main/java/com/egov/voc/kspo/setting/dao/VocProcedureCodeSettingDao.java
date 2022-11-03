package com.egov.voc.kspo.setting.dao;

import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface VocProcedureCodeSettingDao extends ICrmDao {

    int chngProcedureDuty(Map<String, Object> param);
}
