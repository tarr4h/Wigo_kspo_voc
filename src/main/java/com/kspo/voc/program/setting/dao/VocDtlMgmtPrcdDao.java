package com.kspo.voc.program.setting.dao;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.program.setting.model.VocDirCdVo;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.program.setting.dao.VocDtlMgmtPrcdDao
 *  - VocDtlMgmtPrcdDao.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : VocDtlMgmtPrcdDao
 * @description :
 * @date : 2023-01-09
 */

@VocMapper
public interface VocDtlMgmtPrcdDao extends IVocDao {
    String selectMaxDirCd();

    void insertDirCd(Map<String, Object> param);

    int insertDirMgmtCombo(Map<String, Object> param);

    <T> List<T> selectDirMgmtList(Map<String, Object> param);

    int insertDirPrcd(Map<String, Object> prcd);

    String selectMaxMgmtPrcdCd();

    int insertMgmtPrcd(Map<String, Object> prcd);

    String selectMaxMgmtTaskCd();

    int insertMgmtTask(Map<String, Object> taskMap);

    int insertMgmtActv(Map<String, Object> param);

    String selectMaxMgmtActvCd();
}
