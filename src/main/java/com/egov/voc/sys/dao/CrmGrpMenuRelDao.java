package com.egov.voc.sys.dao;

import com.egov.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface CrmGrpMenuRelDao extends ICrmDao {

	int deleteMenuCd(Object param) throws Exception;

	void deleteGrpCd(Object param) throws Exception;

}
