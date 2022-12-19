package com.kspo.voc.kspo.setting.dao;



import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface VocManagementCodeDao extends ICrmDao {

    String maxChildCd(EzMap param);

}
