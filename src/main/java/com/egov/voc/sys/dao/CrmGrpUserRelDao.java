package com.egov.voc.sys.dao;

import com.egov.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface CrmGrpUserRelDao extends ICrmDao {

	void deleteUserCd(Object param) throws Exception;

	void deleteGrpCd(Object param) throws Exception;

}
