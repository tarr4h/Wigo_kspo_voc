package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface CommonDao extends IVocDao {

	String getAutoSeq(Object param);
}
