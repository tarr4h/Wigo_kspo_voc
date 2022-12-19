package com.kspo.voc.kspo.setting.dao;

import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface VocProcedureMappingDao extends IVocDao {
    String selectMaxCd();
}
