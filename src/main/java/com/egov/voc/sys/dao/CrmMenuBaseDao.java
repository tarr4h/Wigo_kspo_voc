package com.egov.voc.sys.dao;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.sys.mapper.CrmMapper;
import com.egov.voc.sys.model.CrmMenuVo;

import java.util.List;

@CrmMapper
public interface CrmMenuBaseDao extends ICrmDao {

	CrmMenuVo selectMaxInfo(Object so) throws Exception;

	List<CrmMenuVo> selectTreeList(EzMap param) throws Exception;

	int updateSeq(Object param);

	List<CrmMenuVo> selectUserMenuList(Object param) throws Exception;

	int selectChildrenCount(Object param) throws Exception;
	
}
