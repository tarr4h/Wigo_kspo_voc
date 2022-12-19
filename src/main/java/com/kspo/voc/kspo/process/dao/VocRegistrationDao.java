package com.kspo.voc.kspo.process.dao;

import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface VocRegistrationDao extends ICrmDao {

    String selectMaxSeq();

    String selectMaxRegPrcdSeq();

    int insertRegProcedure(Object param);
}

