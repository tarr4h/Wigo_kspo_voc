package com.kspo.voc.kspo.setting.dao;

import java.util.List;
import java.util.Map;

import com.kspo.voc.kspo.setting.model.VocTaskCodeVo;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.mapper.VocMapper;

@VocMapper
public interface VocProcedureCodeSettingDao extends IVocDao {

    int chngProcedureDuty(Map<String, Object> param);

    int updateDeadline(Map<String, Object> param);

    int deleteAutoApplyPrcd(List<VocTaskCodeVo> autoApplyPrcdList);
}
