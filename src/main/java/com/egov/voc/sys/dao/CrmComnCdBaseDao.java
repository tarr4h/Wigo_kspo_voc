package com.egov.voc.sys.dao;

import com.egov.voc.sys.mapper.CrmMapper;
import com.egov.voc.sys.model.CrmComnCdBaseVo;

import java.util.List;

@CrmMapper
public interface CrmComnCdBaseDao extends ICrmDao {

	int deleteChildren(Object param) throws Exception;
	List<CrmComnCdBaseVo> selectCodeCombo(Object param) throws Exception;
}
