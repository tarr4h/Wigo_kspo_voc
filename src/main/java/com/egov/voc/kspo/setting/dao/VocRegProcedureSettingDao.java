package com.egov.voc.kspo.setting.dao;

import com.egov.voc.base.common.model.BaseVo;
import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.ITreeVo;
import com.egov.voc.kspo.setting.model.*;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface VocRegProcedureSettingDao extends ICrmDao {

    List<? extends ITreeVo> vocManagementCodeTree(EzMap param);

    <T> T selectPrcdBas(Object prcdSeq);

    List<? extends BaseVo> selectPrcdBasList(Map<String, Object> param);

    String selectMaxMcPrcdSeq();

    String selectMaxDirCd();

    int insertDirCd(EzMap param);

    int insertDirMcMapping(EzMap param);

    int insertProcedure(Map<String, Object> prcd);

    int insertDirOrgMapping(Map<String, Object> param);

    int insertProcedureDirConn(Map<String, Object> param);

    String selectDirCd(Map<String, Object> param);

    <T> List<T> selectProcedureList(EzMap param);

    List<EzMap> selectDutyOrgList(Map<String, Object> param);

    int deleteDirOrg(EzMap param);

    int updateDirOrg(EzMap param);

    <T> List<T> selectTaskBasList(EzMap param);

    String selectMaxMcTaskSeq();

    int insertTask(Map<String, Object> taskMap);

    <T> List<T> selectTaskList(EzMap param);

    <T> List<T> selectActivityList(EzMap param);
}
