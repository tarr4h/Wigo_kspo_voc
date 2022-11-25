package com.egov.voc.kspo.setting.dao;



import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.ITreeVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;
import com.egov.voc.sys.model.CrmComnCdBaseVo;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface VocManagementCodeDao extends ICrmDao {

    String maxChildCd(EzMap param);

}
