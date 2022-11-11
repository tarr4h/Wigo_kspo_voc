package com.egov.voc.kspo.setting.service;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.setting.dao.VocTaskCodeSettingDao;
import com.egov.voc.kspo.setting.model.VocProcedureCodeVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VocTaskCodeSettingService extends AbstractCrmService {

    @Autowired
    VocTaskCodeSettingDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public <T> List<T> selectTaskCodeList(EzMap param) {
        return dao.selectList(param);
    }

    @SuppressWarnings("unchecked")
    public int insert(Object param) throws Exception {
        param = VocUtils.setCodeSettingParam(param);

        int result = validateAutoApply((Map<String, Object>) param);
        if(result > 0){
            return result;
        }
        return dao.insert(param);
    }

    public int validateAutoApply(Map<String, Object> param){
        int deadline = VocUtils.parseIntObject(param.get("deadline"));

        String autoApplyAllYn = (String) param.get("autoApplyAllYn");
        if(autoApplyAllYn.equals("Y")){
            List<VocProcedureCodeVo> prcdBasList = dao.selectAvailablePrcdList(param);
            for(VocProcedureCodeVo vo : prcdBasList){
                if(vo.getDeadline() < deadline){
                    return 999;
                }
            }
        }

        String autoApplyYn = (String) param.get("autoApplyYn");
        if(autoApplyYn.equals("Y") && autoApplyAllYn.equals("N")){
            String autoApplyPrcdSeq = (String) param.get("autoApplyPrcdSeq");
            EzMap map = new EzMap();
            map.put("prcdSeq", autoApplyPrcdSeq);
            VocProcedureCodeVo vo = (VocProcedureCodeVo) dao.selectAppliedPrcd(map).get(0);

            if(vo.getDeadline() < deadline){
                return 998;
            }

        }
        return 0;
    }

    @Override
    public int update(Object param) throws Exception {
        return super.update(param);
    }

    @Override
    public int delete(Object param) throws Exception {
        return super.delete(param);
    }

    public Object chngTaskDuty(Map<String, Object> param) {
        return dao.chngTaskDuty(param);
    }


    public <T> List<T> selectAvailablePrcdList(EzMap param) {
        return dao.selectAvailablePrcdList(param);
    }

    public List<EzMap> selectAppliedPrcd(EzMap param) {
        return dao.selectAppliedPrcd(param);
    }

    public Object updateAutoApplyPrcd(EzMap param) {
        return dao.updateAutoApplyPrcd(param);
    }

}
