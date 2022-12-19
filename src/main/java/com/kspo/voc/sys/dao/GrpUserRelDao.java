package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface GrpUserRelDao extends IVocDao {

	void deleteUserId(Object param) throws Exception;

	void deleteGrpId(Object param) throws Exception;

}
