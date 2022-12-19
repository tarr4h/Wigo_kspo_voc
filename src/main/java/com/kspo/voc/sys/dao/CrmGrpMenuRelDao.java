package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface CrmGrpMenuRelDao extends ICrmDao {

	int deleteMenuId(Object param) throws Exception;

	void deleteGrpId(Object param) throws Exception;

}
