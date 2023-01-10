package com.kspo.voc.program.common.dao;

import java.util.List;
import java.util.Map;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.ITreeVo;
import com.kspo.voc.program.setting.model.VocMgmtPrcdVo;
import com.kspo.voc.program.setting.model.VocMgmtTaskVo;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface IVocPrcDao extends IVocDao {

    List<? extends ITreeVo> selectVocMgmtCdTree(Map<String, Object> param);

    <T> List<T> selectTaskBasList(Object param);

    <T> T selectMgmtCd(Object param);

    <T> T selectPrcdBas(Object param);

    <T> List<T> selectPrcdBasList(Object param);

    <T> List<T> getMgmtCdSelect(Map<String, Object> param);

    <T> T selectChannelDirCd(Map<String, Object> param);

    <T> List<T> selectDirOrg(EzMap param);

    <T> List<T> selectMgmtPrcdList(Map<String, Object> param);

    <T> List<T> selectDirPrcdList(Map<String, Object> param);

    <T> List<T> selectMgmtTaskList(Map<String, Object> param);

    <T> List<T> selectActvBasList(Object param);

    <T> List<T> selectMgmtActvList(EzMap param);
}

