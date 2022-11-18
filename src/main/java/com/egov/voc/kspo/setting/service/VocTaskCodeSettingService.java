package com.egov.voc.kspo.setting.service;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.setting.dao.VocTaskCodeSettingDao;
import com.egov.voc.kspo.setting.model.VocProcedureCodeVo;
import com.egov.voc.kspo.setting.model.VocProcedureVo;
import com.egov.voc.kspo.setting.model.VocTaskCodeVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Map<String, Object> insertCode(Object param) throws Exception {
        param = VocUtils.setCodeSettingParam(param);

        Map<String, Object> returnMap = validateAutoApply((Map<String, Object>) param);
        if(!(boolean) returnMap.get("result")){
            return returnMap;
        }

        int result = dao.insert(param);
        returnMap.put("msg", result + "건이 등록되었습니다.");
        return returnMap;
    }

    public Map<String, Object> validateAutoApply(Map<String, Object> param){
        Map<String, Object> returnMap = new HashMap<>();

        int deadline = VocUtils.parseIntObject(param.get("deadline"));
        String autoApplyAllYn = (String) param.get("autoApplyAllYn");

        // task를 허용하는 절차 리스트 조회
        List<VocProcedureCodeVo> prcdBasList = dao.selectAvailablePrcdList(param);

        int deadlineSum = 0;
        List<VocTaskCodeVo> taskBasList = dao.selectList(param);
        
        // 전체절차적용 task 조회, 처리기한의 합 구하기
        for(VocTaskCodeVo taskBas : taskBasList){
            if(taskBas.getAutoApplyAllYn().equals("Y")){
                deadlineSum += taskBas.getDeadline();
            }
        }

        // 1. 전체절차 자동적용인 경우
        if(autoApplyAllYn.equals("Y")){
            // prcd에 적용되는 task의 합이 prcd의 deadline을 초과하는지 확인
            deadlineSum += deadline;
            for(VocProcedureCodeVo prcdBas : prcdBasList){
                List<VocTaskCodeVo> tempTaskBasList = new ArrayList<>(taskBasList);
                tempTaskBasList.removeIf(taskBas -> taskBas.getAutoApplyPrcdSeq() != null && !taskBas.getAutoApplyPrcdSeq().equals(prcdBas.getPrcdSeq()));

                int currDeadline = deadlineSum;
                if(tempTaskBasList.size() != 0){
                    for(VocTaskCodeVo taskBas : tempTaskBasList){
                        currDeadline += taskBas.getDeadline();
                    }
                }

                if(prcdBas.getDeadline() < currDeadline){
                    returnMap.put("msg", "task 추가 시 처리기한이 초과되는 절차가 존재합니다.\n확인 후 다시 등록해주세요.");
                    returnMap.put("result", false);
                    return returnMap;
                }
            }
        }

        // 2. 특정절차 적용인 경우
        String autoApplyYn = (String) param.get("autoApplyYn");
        if(autoApplyYn.equals("Y") && autoApplyAllYn.equals("N")){
            String autoApplyPrcdSeq = (String) param.get("autoApplyPrcdSeq");
            // task의 합이 prcd의 deadline을 초과하는지 확인
            for(VocProcedureCodeVo prcdBas : prcdBasList){
                int currDeadline = deadlineSum;
                if(prcdBas.getPrcdSeq().equals(autoApplyPrcdSeq)){
                    currDeadline += deadline;
                    for(VocTaskCodeVo taskBas : taskBasList){
                        if(taskBas.getAutoApplyPrcdSeq() != null && taskBas.getAutoApplyPrcdSeq().equals(prcdBas.getPrcdSeq())){
                            currDeadline += taskBas.getDeadline();
                        }
                    }

                    if(prcdBas.getDeadline() < currDeadline){
                        returnMap.put("msg", "[" + prcdBas.getPrcdNm() + "] 절차의 처리기한이 초과됩니다.\n확인 후 다시 등록해주세요.");
                        returnMap.put("result", false);
                        return returnMap;
                    }
                }
            }
        }

        returnMap.put("result", true);
        return returnMap;
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
