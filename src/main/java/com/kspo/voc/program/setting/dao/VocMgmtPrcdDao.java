package com.kspo.voc.program.setting.dao;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.program.setting.model.VocDirCdVo;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.program.setting.dao.VocMgmtPrcdDao
 *  - VocMgmtPrcdDao.java
 * </pre>
 *
 * @ClassName     : VocMgmtPrcdDao
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-05
 *
*/

@VocMapper
public interface VocMgmtPrcdDao extends IVocDao {
    <T> T selectDirCd(Map<String, Object> param);
    int insertDirCd(Map<String, Object> param);
    int insertDirOrg(EzMap param);
    String selectMaxDirCd();
    <T> List<T> selectDirOrg(EzMap param);

    int insertDirMgmt(Map<String, Object> param);

    int updateDirOrg(EzMap param);
}
