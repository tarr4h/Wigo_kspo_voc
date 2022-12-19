package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface CrmCommonDao extends ICrmDao {

	public String getAutoSeq(Object param);
}
