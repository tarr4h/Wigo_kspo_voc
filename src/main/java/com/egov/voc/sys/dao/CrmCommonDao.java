package com.egov.voc.sys.dao;

import com.egov.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface CrmCommonDao extends ICrmDao {

	public String getAutoSeq(Object param);
}
