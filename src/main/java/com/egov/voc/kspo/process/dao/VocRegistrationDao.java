package com.egov.voc.kspo.process.dao;

import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.mapper.CrmMapper;

import java.util.List;
import java.util.Map;

@CrmMapper
public interface VocRegistrationDao extends ICrmDao {
    <T> List<T> selectChannel(Map<String, Object> param);

    String selectMaxSeq();
}
