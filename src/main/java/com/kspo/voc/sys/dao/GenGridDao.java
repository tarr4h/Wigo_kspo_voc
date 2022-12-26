package com.kspo.voc.sys.dao;

import java.util.List;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface GenGridDao extends IVocDao {
	List<EzMap> selectCodeList(Object param);
}
