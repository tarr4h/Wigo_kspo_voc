package com.kspo.voc.sys.dao;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface GrpOrgRelDao extends IVocDao {

	void deleteGrpId(Object param) throws EgovBizException;

}
