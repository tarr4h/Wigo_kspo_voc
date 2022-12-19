package com.kspo.voc.kspo.process.dao;

import java.util.Map;

import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.mapper.CrmMapper;

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

@CrmMapper
public interface VocReceiptDao extends ICrmDao {
    <T> T selectRegistration(Map<String, Object> param);
}
