package com.kspo.voc.kspo.setting.dao;

import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface VocProcedureMappingDao extends ICrmDao {
    String selectMaxCd();
}
