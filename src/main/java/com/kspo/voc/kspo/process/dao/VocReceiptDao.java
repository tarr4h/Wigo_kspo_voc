package com.kspo.voc.kspo.process.dao;

import java.util.Map;

import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

/**
 * <pre>
 * com.kspo.voc.kspo.process.dao.VocReceiptDao
 *  - VocReceiptDao.java
 * </pre>
 *
 * @ClassName     : VocReceiptDao
 * @description   :
 * @author        : tarr4h
 * @date          : 2022-12-05
 *
*/

@VocMapper
public interface VocReceiptDao extends IVocDao {
    <T> T selectRegistration(Map<String, Object> param);
}
