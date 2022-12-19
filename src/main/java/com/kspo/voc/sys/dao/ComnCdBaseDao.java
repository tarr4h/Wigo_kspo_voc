package com.kspo.voc.sys.dao;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import com.kspo.voc.sys.mapper.VocMapper;
import com.kspo.voc.sys.model.ComnCdBaseVo;

@VocMapper
public interface ComnCdBaseDao extends IVocDao {

	int deleteChildren(Object param) throws EgovBizException;
	List<ComnCdBaseVo> selectCodeCombo(Object param) throws EgovBizException;
}
