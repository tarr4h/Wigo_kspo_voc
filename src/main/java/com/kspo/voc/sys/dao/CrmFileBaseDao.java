package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface CrmFileBaseDao extends ICrmDao {

	int selectMaxFileSeq(Object param) throws Exception;

}
