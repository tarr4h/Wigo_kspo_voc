package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface GrpEmpRelDao extends IVocDao {
	void deleteGrpId(Object param) throws Exception;
}
