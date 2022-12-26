package com.kspo.voc.kspo.common.dao;

import java.util.List;
import java.util.Map;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.ITreeVo;
import com.kspo.voc.kspo.process.model.VocPrcdVo;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface IVocPrcDao extends IVocDao {

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

    List<VocPrcdVo> selectRegPrcdList(Object param);

    int updateRegPrcd(Object param);

    <T> T selectRegPrcd(Object param);

    Map<String, Object> selectVocStatus(String prcdSeq, String status);

    int updateRegistrationStatus(Object param);

    <T> List<T> getManagementCodeSelect(Map<String, Object> param);

    <T> T selectStatusCd(Object param);

    <T> List<T> selectStatusList(Object param);


}

