package com.kspo.voc.sys.dao;

import java.util.List;

import com.kspo.voc.sys.mapper.VocMapper;
import com.kspo.voc.sys.model.ComnCdBaseVo;

@VocMapper
public interface ComnCdBaseDao extends IVocDao {

	int deleteChildren(Object param) throws Exception;
	List<ComnCdBaseVo> selectCodeCombo(Object param) throws Exception;
}
