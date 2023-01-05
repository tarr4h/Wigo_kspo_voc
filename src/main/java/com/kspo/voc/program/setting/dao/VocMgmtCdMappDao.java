package com.kspo.voc.program.setting.dao;

import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface VocMgmtCdMappDao extends IVocDao {
    String selectMaxCd();
}
