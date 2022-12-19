package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface CrmGrpUserRelDao extends ICrmDao {

	void deleteUserId(Object param) throws Exception;

	void deleteGrpId(Object param) throws Exception;

}
