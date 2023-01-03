package com.kspo.voc.kspo.common.dao;

import java.util.List;
import java.util.Map;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.ITreeVo;
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

}

