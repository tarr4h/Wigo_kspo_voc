package com.kspo.voc.kspo.process.dao;

import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface VocRegistrationDao extends IVocDao {

    String selectMaxSeq();

    String selectMaxRegPrcdSeq();

    int insertRegProcedure(Object param);
}

