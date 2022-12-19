package com.kspo.voc.kspo.setting.dao;

import java.util.Map;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.kspo.setting.model.*;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface VocRegProcedureSettingDao extends IVocDao {

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
