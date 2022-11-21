package com.egov.voc.kspo.setting.dao;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.ITreeVo;
import com.egov.voc.kspo.setting.model.VocManagementCodeVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface VocProcedureMappingDao extends ICrmDao {
    List<? extends ITreeVo> vocManagementCodeTree(EzMap param);

    String selectMaxCd();

    <T> T selectManagementCode(Object param);
}
