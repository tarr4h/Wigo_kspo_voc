package com.kspo.voc.sys.dao;

import java.util.List;

import com.kspo.voc.sys.mapper.CrmMapper;
import com.kspo.voc.sys.model.CrmComnCdBaseVo;

@CrmMapper
public interface CrmComnCdBaseDao extends ICrmDao {

	int deleteChildren(Object param) throws Exception;
	List<CrmComnCdBaseVo> selectCodeCombo(Object param) throws Exception;
}
