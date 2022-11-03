package com.egov.voc.sys.dao;

import com.egov.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface CrmGrpEmpRelDao extends ICrmDao {
	void deleteGrpCd(Object param) throws Exception;
}
