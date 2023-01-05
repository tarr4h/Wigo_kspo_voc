package com.kspo.voc.program.setting.dao;

import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

/**
 * <pre>
 * com.kspo.voc.program.setting.dao.VocActvBasDao
 *  - VocActvBasDao.java
 * </pre>
 *
 * @ClassName     : VocActvBasDao
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-03
 *
*/

@VocMapper
public interface VocActvBasDao extends IVocDao {

    String selectMaxCd();

}
