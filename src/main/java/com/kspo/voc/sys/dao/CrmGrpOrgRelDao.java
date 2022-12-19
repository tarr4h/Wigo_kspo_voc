package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface CrmGrpOrgRelDao extends ICrmDao {

	void deleteGrpId(Object param) throws Exception;

}
