package com.egov.voc.kspo.common.dao;

import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;
import com.egov.voc.sys.model.CrmComnCdBaseVo;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.egov.voc.kspo.common.dao.VocComnDao
 *  - VocComnDao.java
 * </pre>
 *
 * @ClassName     : VocComnDao
 * @description   :
 * @author        : tarr4h
 * @date          : 2022-12-01
 *
*/

@CrmMapper
public interface VocComnDao extends ICrmDao {

    List<CrmComnCdBaseVo> selectComnCdList(Map<String, Object> param);

}
