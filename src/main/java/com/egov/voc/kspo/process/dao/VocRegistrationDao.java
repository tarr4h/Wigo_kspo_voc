package com.egov.voc.kspo.process.dao;

import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

@CrmMapper
public interface VocRegistrationDao extends ICrmDao {

    String selectMaxSeq();

    String selectMaxRegPrcdSeq();

    int insertRegProcedure(Object param);
}

