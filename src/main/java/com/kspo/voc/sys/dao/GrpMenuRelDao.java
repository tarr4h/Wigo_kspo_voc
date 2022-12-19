package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface GrpMenuRelDao extends IVocDao {

	int deleteMenuId(Object param) throws Exception;

	void deleteGrpId(Object param) throws Exception;

}
