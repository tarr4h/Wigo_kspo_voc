package com.egov.voc.kspo.setting.dao;

import com.egov.voc.base.common.model.BaseVo;
import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.ITreeVo;
import com.egov.voc.kspo.setting.model.VocProcedureVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface VocRegProcedureSettingDao extends ICrmDao {

    List<? extends ITreeVo> vocManagementCodeTree(EzMap param);

    List<? extends BaseVo> selectPrcdBasList(Map<String, Object> param);

    String selectMaxMcPrcdSeq();

    String selectMaxDirCd();

    int insertDirCd(EzMap dirCd);

    int insertDirMcMapping(EzMap param);

    int insertProcedure(VocProcedureVo prcd);

    int insertDirOrgMapping(Map<String, Object> param);

    int insertProcedureDirConn(Map<String, Object> param);

}
