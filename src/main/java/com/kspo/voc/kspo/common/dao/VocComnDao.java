package com.kspo.voc.kspo.common.dao;

import java.util.List;
import java.util.Map;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;
import com.kspo.voc.sys.model.EmpBaseVo;
import com.kspo.voc.sys.model.OrgBaseVo;

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

@VocMapper
public interface VocComnDao extends IVocDao {
    <T> T selectComnCd(Map<String, Object> param);

    <T> List<T> selectComnCdList(Map<String, Object> param);

    /// 사원, 부서 관련
    List<OrgBaseVo> selectOrgList(Map<String, Object> param);

    List<EmpBaseVo> selectEmpList(EzMap param);

    OrgBaseVo selectOrg(String orgId);

    EmpBaseVo selectEmp(String empId);

}
