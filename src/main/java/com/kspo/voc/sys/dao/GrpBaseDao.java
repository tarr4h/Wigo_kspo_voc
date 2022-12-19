package com.kspo.voc.sys.dao;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.mapper.VocMapper;
import com.kspo.voc.sys.model.GrpBaseVo;
import com.kspo.voc.sys.model.GrpMenuRelVo;
import com.kspo.voc.sys.model.GrpUserRelVo;

@VocMapper
public interface GrpBaseDao extends IVocDao {

	List<GrpUserRelVo> selectGroupUserList(Object param) throws EgovBizException;

	int selectGroupUserListCount(Object param) throws EgovBizException;
	
	List<GrpMenuRelVo> selectGroupMenuList(Object param) throws EgovBizException;

	int selectGroupMenuListCount(Object param) throws EgovBizException;

	List<GrpBaseVo> selectGroupCheckList(EzMap param) throws EgovBizException;

}
