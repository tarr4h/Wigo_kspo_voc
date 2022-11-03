package com.egov.voc.sys.dao;

import com.egov.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface CrmFileBaseDao extends ICrmDao {

	int selectMaxFileSeq(Object param) throws Exception;

}
