package com.egov.voc.kspo.setting.dao;



import com.egov.base.common.model.EzMap;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface VocManagementCodeDao extends ICrmDao {

    String maxChildCd(EzMap param);

}
