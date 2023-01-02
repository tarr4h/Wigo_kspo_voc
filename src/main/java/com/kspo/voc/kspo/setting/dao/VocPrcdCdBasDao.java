package com.kspo.voc.kspo.setting.dao;

import com.kspo.voc.kspo.setting.model.VocTaskBasVo;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

import java.util.List;
import java.util.Map;

@VocMapper
public interface VocPrcdCdBasDao extends IVocDao {

    int chngPrcdDuty(Map<String, Object> param);

    int updateDeadline(Map<String, Object> param);

    int deleteAutoApplyPrcd(List<VocTaskBasVo> autoApplyPrcdList);

    String selectMaxCd();
}
