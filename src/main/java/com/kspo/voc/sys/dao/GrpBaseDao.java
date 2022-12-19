package com.kspo.voc.sys.dao;

import java.util.List;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.mapper.VocMapper;
import com.kspo.voc.sys.model.GrpBaseVo;
import com.kspo.voc.sys.model.GrpMenuRelVo;
import com.kspo.voc.sys.model.GrpUserRelVo;

@VocMapper
public interface GrpBaseDao extends IVocDao {

	List<GrpUserRelVo> selectGroupUserList(Object param) throws Exception;

	int selectGroupUserListCount(Object param) throws Exception;
	
	List<GrpMenuRelVo> selectGroupMenuList(Object param) throws Exception;

	int selectGroupMenuListCount(Object param) throws Exception;

	List<GrpBaseVo> selectGroupCheckList(EzMap param) throws Exception;

}
