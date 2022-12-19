package com.kspo.voc.sys.dao;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.mapper.VocMapper;
import com.kspo.voc.sys.model.MenuVo;

@VocMapper
public interface MenuBaseDao extends IVocDao {

	MenuVo selectMaxInfo(Object so) throws EgovBizException;

	List<MenuVo> selectTreeList(EzMap param) throws EgovBizException;

	int updateSeq(Object param);

	List<MenuVo> selectUserMenuList(Object param) throws EgovBizException;

	int selectChildrenCount(Object param) throws EgovBizException;
	
}
