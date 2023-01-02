package com.kspo.voc.kspo.setting.service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.kspo.common.stnd.CodeGeneration;
import com.kspo.voc.kspo.common.util.VocUtils;
import com.kspo.voc.kspo.setting.dao.VocTaskBasDao;
import com.kspo.voc.kspo.setting.model.VocPrcdBasVo;
import com.kspo.voc.kspo.setting.model.VocTaskBasVo;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.service.AbstractVocService;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VocTaskBasService extends AbstractVocService {

    @Autowired
    VocTaskBasDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public <T> List<T> selectTaskCodeList(EzMap param) {
        return dao.selectList(param);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> insertCode(Object param) throws EgovBizException {
        param = VocUtils.setCodeSettingParam(param);

        Map<String, Object> returnMap = validateAutoApply((Map<String, Object>) param);
        if(!(boolean) returnMap.get("result")){
            return returnMap;
        }

        String maxCd = dao.selectMaxCd();
        ((Map<String, Object>) param).put("taskCd", CodeGeneration.generateCode(maxCd, CodeGeneration.TASK_BAS));
        int result = dao.insert(param);
        returnMap.put("msg", result + "건이 등록되었습니다.");
        return returnMap;
    }

    public Map<String, Object> validateAutoApply(Map<String, Object> param){
        Map<String, Object> returnMap = new HashMap<>();

        int deadline = VocUtils.parseIntObject(param.get("ddlnSec"));
        String autoApplyAllYn = (String) param.get("autoApplyAllPrcdYn");

        // task를 허용하는 절차 리스트 조회
        List<VocPrcdBasVo> prcdBasList = dao.selectAvailablePrcdList(param);

        int deadlineSum = 0;
        List<VocTaskBasVo> taskBasList = dao.selectList(param);
        
        // 전체절차적용 task 조회, 처리기한의 합 구하기
        for(VocTaskBasVo taskBas : taskBasList){
            if(taskBas.getAutoApplyAllPrcdYn().equals("Y")){
                deadlineSum += taskBas.getDdlnSec();
            }
        }

        // 1. 전체절차 자동적용인 경우
        if(autoApplyAllYn.equals("Y")){
            // prcd에 적용되는 task의 합이 prcd의 deadline을 초과하는지 확인
            deadlineSum += deadline;
            for(VocPrcdBasVo prcdBas : prcdBasList){
                List<VocTaskBasVo> tempTaskBasList = new ArrayList<>(taskBasList);
                tempTaskBasList.removeIf(taskBas -> taskBas.getAutoApplyPrcdCd() != null && !taskBas.getAutoApplyPrcdCd().equals(prcdBas.getPrcdCd()));

                int currDeadline = deadlineSum;
                if(tempTaskBasList.size() != 0){
                    for(VocTaskBasVo taskBas : tempTaskBasList){
                        currDeadline += taskBas.getDdlnSec();
                    }
                }

                if(prcdBas.getDdlnSec() < currDeadline){
                    returnMap.put("msg", "task 추가 시 처리기한이 초과되는 절차가 존재합니다.\n확인 후 다시 등록해주세요.");
                    returnMap.put("result", false);
                    return returnMap;
                }
            }
        }

        // 2. 특정절차 적용인 경우
        String autoApplyYn = (String) param.get("autoApplyPrcdYn");
        if(autoApplyYn.equals("Y") && autoApplyAllYn.equals("N")){
            String autoApplyPrcdCd = (String) param.get("autoApplyPrcdCd");
            // task의 합이 prcd의 deadline을 초과하는지 확인
            for(VocPrcdBasVo prcdBas : prcdBasList){
                int currDeadline = deadlineSum;
                if(prcdBas.getPrcdCd().equals(autoApplyPrcdCd)){
                    currDeadline += deadline;
                    for(VocTaskBasVo taskBas : taskBasList){
                        if(taskBas.getAutoApplyPrcdCd() != null && taskBas.getAutoApplyPrcdCd().equals(prcdBas.getPrcdCd())){
                            currDeadline += taskBas.getDdlnSec();
                        }
                    }

                    if(prcdBas.getDdlnSec() < currDeadline){
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
    public int update(Object param) throws EgovBizException {
        return super.update(param);
    }

    @Override
    public int delete(Object param) throws EgovBizException {
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
