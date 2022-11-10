package com.egov.voc.kspo.corp.dao;


import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;
import com.egov.voc.sys.model.CrmComnCdBaseVo;
import com.egov.voc.sys.model.CrmEmpBaseVo;
import com.egov.voc.sys.model.CrmOrgBaseVo;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface VocCorporationDao extends ICrmDao {
    List<CrmOrgBaseVo> selectOrgList(Map<String, Object> param);

    List<CrmEmpBaseVo> selectEmpList(EzMap param);

    List<CrmComnCdBaseVo> selectComnCdList(Map<String, Object> param);
}
