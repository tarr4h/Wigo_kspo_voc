package com.kspo.voc.sys.dao;

import java.util.List;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.mapper.CrmMapper;
import com.kspo.voc.sys.model.CrmGrpBaseVo;
import com.kspo.voc.sys.model.CrmGrpMenuRelVo;
import com.kspo.voc.sys.model.CrmGrpUserRelVo;

@CrmMapper
public interface CrmGrpBaseDao extends ICrmDao {

	List<CrmGrpUserRelVo> selectGroupUserList(Object param) throws Exception;

	int selectGroupUserListCount(Object param) throws Exception;
	
	List<CrmGrpMenuRelVo> selectGroupMenuList(Object param) throws Exception;

	int selectGroupMenuListCount(Object param) throws Exception;

	List<CrmGrpBaseVo> selectGroupCheckList(EzMap param) throws Exception;

}
