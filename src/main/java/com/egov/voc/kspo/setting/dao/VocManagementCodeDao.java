package com.egov.voc.kspo.setting.dao;



import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.ITreeVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface VocManagementCodeDao extends ICrmDao {
    List<? extends ITreeVo> vocManagementCodeTree(EzMap param);

    String maxChildCd(EzMap param);
}
