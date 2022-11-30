package com.egov.voc.kspo.setting.dao;

import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface VocProcedureMappingDao extends ICrmDao {
    String selectMaxCd();
}
