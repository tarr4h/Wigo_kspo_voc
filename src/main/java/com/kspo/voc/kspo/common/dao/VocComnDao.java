package com.kspo.voc.kspo.common.dao;

import java.util.List;
import java.util.Map;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.mapper.CrmMapper;
import com.kspo.voc.sys.model.CrmEmpBaseVo;
import com.kspo.voc.sys.model.CrmOrgBaseVo;

/**
 * <pre>
 * com.kspo.voc.kspo.common.dao.VocComnDao
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
    <T> T selectComnCd(Map<String, Object> param);

    <T> List<T> selectComnCdList(Map<String, Object> param);

    /// 사원, 부서 관련
    List<CrmOrgBaseVo> selectOrgList(Map<String, Object> param);

    List<CrmEmpBaseVo> selectEmpList(EzMap param);

    CrmOrgBaseVo selectOrg(String orgId);

    CrmEmpBaseVo selectEmp(String empId);

}
