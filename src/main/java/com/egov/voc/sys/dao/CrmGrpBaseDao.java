package com.egov.voc.sys.dao;

import com.egov.base.common.model.EzMap;
import com.egov.voc.sys.mapper.CrmMapper;
import com.egov.voc.sys.model.CrmGrpBaseVo;
import com.egov.voc.sys.model.CrmGrpMenuRelVo;
import com.egov.voc.sys.model.CrmGrpUserRelVo;

import java.util.List;

@CrmMapper
public interface CrmGrpBaseDao extends ICrmDao {

	List<CrmGrpUserRelVo> selectGroupUserList(Object param) throws Exception;

	int selectGroupUserListCount(Object param) throws Exception;
	
	List<CrmGrpMenuRelVo> selectGroupMenuList(Object param) throws Exception;

	int selectGroupMenuListCount(Object param) throws Exception;

	List<CrmGrpBaseVo> selectGroupCheckList(EzMap param) throws Exception;

}
