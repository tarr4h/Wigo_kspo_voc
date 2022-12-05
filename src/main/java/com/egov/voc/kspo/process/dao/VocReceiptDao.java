package com.egov.voc.kspo.process.dao;

import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

import java.util.Map;

/**
 * <pre>
 * com.egov.voc.kspo.process.dao.VocReceiptDao
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
