package com.kspo.voc.sys.dao;

import java.util.List;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.mapper.VocMapper;
import com.kspo.voc.sys.model.MenuVo;

@VocMapper
public interface MenuBaseDao extends IVocDao {

	MenuVo selectMaxInfo(Object so) throws Exception;

	List<MenuVo> selectTreeList(EzMap param) throws Exception;

	int updateSeq(Object param);

	List<MenuVo> selectUserMenuList(Object param) throws Exception;

	int selectChildrenCount(Object param) throws Exception;
	
}
