package com.kspo.voc.sys.dao;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface FileBaseDao extends IVocDao {

	int selectMaxFileSeq(Object param) throws EgovBizException;

}
