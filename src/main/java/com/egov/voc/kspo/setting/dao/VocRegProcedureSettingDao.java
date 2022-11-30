package com.egov.voc.kspo.setting.dao;

import com.egov.base.common.model.EzMap;
import com.egov.voc.kspo.setting.model.*;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

import java.util.Map;

@CrmMapper
public interface VocRegProcedureSettingDao extends ICrmDao {

    String selectMaxMcPrcdSeq();
    String selectMaxMcTaskSeq();
    String selectMaxActSeq();
    String selectMaxDirCd();


    int insertDirCd(EzMap param);

    int insertDirMcMapping(EzMap param);

    int insertProcedure(Map<String, Object> prcd);

    int insertDirOrgMapping(Map<String, Object> param);

    int insertProcedureDirConn(Map<String, Object> param);

    int deleteDirOrg(Map<String, Object> param);

    int updateDirOrg(EzMap param);



    int insertTask(Map<String, Object> taskMap);

    int insertActivity(Map<String, Object> param);



    int deleteActivity(Object param);

    int deleteTask(EzMap param);

    int deleteProcedure(EzMap param);

    int updateProcedure(VocProcedureVo procedure);
}
