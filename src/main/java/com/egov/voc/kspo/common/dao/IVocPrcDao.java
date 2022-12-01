package com.egov.voc.kspo.common.dao;

import com.egov.base.common.model.EzMap;
import com.egov.base.common.model.ITreeVo;
import com.egov.voc.kspo.process.model.VocRegPrcdVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface IVocPrcDao extends ICrmDao {

    List<? extends ITreeVo> selectVocManagementCodeTree(Map<String, Object> param);

    <T> List<T> selectTaskBasList(Object param);

    <T> T selectManagementCode(Object param);

    <T> T selectPrcdBas(Object param);

    <T> List<T> selectPrcdBasList(Object param);

    <T> List<T> selectActivityFuncBasList(Object param);


    <T> T selectProcedure(Object param);

    <T> List<T> selectProcedureList(Object param);

    <T> List<T> selectTaskList(Object param);

    <T> List<T> selectActivityList(Object param);

    String selectDirCd(Object param);

    List<EzMap> selectDutyOrgList(Object param);

    List<VocRegPrcdVo> selectRegPrcdList(Object param);

    int updateRegPrcd(Object param);

    <T> T selectRegPrcd(Object param);

    Map<String, Object> selectVocStatus(String prcdSeq, String status);

    int updateRegistrationStatus(Object param);

    <T> List<T> getManagementCodeSelect(Map<String, Object> param);

    <T> T selectStatus(Object param);

    <T> List<T> selectStatusList(Object param);


}

