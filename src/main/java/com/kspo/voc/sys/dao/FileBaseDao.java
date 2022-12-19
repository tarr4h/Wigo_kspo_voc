package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface FileBaseDao extends IVocDao {

	int selectMaxFileSeq(Object param) throws Exception;

}
