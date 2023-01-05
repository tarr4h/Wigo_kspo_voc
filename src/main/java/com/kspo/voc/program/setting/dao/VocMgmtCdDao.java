package com.kspo.voc.program.setting.dao;



import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface VocMgmtCdDao extends IVocDao {

    String maxChildCd(EzMap param);

}
