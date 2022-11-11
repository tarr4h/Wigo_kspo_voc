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

    String selectMaxPrcdSeq();

    String selectMaxDirCd();

    void insertDirCd(EzMap dirCd);

    void insertDirMcMapping(EzMap param);

    void insertProcedure(VocProcedureVo prcd);
}
