package com.kspo.voc.sys.dao;

import java.util.List;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.mapper.CrmMapper;
import com.kspo.voc.sys.model.CrmMenuVo;

@CrmMapper
public interface CrmMenuBaseDao extends ICrmDao {

	CrmMenuVo selectMaxInfo(Object so) throws Exception;

	List<CrmMenuVo> selectTreeList(EzMap param) throws Exception;

	int updateSeq(Object param);

	List<CrmMenuVo> selectUserMenuList(Object param) throws Exception;

	int selectChildrenCount(Object param) throws Exception;
	
}
