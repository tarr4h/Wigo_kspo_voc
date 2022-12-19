package com.kspo.voc.kspo.setting.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.kspo.common.service.VocAbstractService;
import com.kspo.voc.kspo.common.util.VocUtils;
import com.kspo.voc.kspo.setting.dao.VocProcedureCodeSettingDao;
import com.kspo.voc.kspo.setting.model.VocTaskCodeVo;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class VocProcedureCodeSettingService extends VocAbstractService {

    @Autowired
    VocProcedureCodeSettingDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public <T> List<T> selectProcedureCodeList(Map<String, Object> param) {
        return dao.selectList(param);
    }

    @Override
    public int insert(Object param) throws Exception {
        param = VocUtils.setCodeSettingParam(param);
        return super.insert(param);
    }

    @Override
    public int update(Object param) throws Exception {
        return super.update(param);
    }

    /**
     * 절차 row 삭제
     *  - 해당 절차가 task의 자동적용으로 설정되어 있다면, trigger를 통해 update됩니다.
     *  - 트리거명 : trig_voc_procedure_bas_delete
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public int delete(Object param) throws Exception {
        return super.delete(param);
    }

    public Object chngProcedureDuty(Map<String, Object> param) {
        return dao.chngProcedureDuty(param);
    }

    public Object updateDeadline(Map<String, Object> param) {
        Map<String, Object> returnMap = new HashMap<>();
        VocUtils.sumUpDeadline(param);

        int deadline = VocUtils.parseIntObject(param.get("deadline"));
//        VocProcedureCodeVo prcd = 
        		selectPrcdBas(param);

        int taskDeadlineSum = 0;
        List<VocTaskCodeVo> taskList = selectTaskBasList(param);

        // 1. taskList가 존재하지 않는다면 : 변경
        if(taskList.size() == 0){
            dao.updateDeadline(param);
            returnMap.put("msg", "변경되었습니다.");
            returnMap.put("result", true);
            return returnMap;
        }

        // 전체적용 task deadline의 합 구하기
        List<VocTaskCodeVo> autoApplyAllList = new ArrayList<>(taskList);
        autoApplyAllList.removeIf(task -> task.getAutoApplyAllYn().equals("N"));
        for(VocTaskCodeVo task : autoApplyAllList){
            taskDeadlineSum += task.getDeadline();
        }

        // 2. 전체적용 < deadline : 변경불가
        if(taskDeadlineSum > deadline){
            returnMap.put("msg", "전체적용 TASK의 처리기한 미만으로는 변경하실 수 없습니다.");
            returnMap.put("result", false);
            return returnMap;
        }

        // 전체적용 deadline 합에 요청 prcd를 target으로 하는 task의 처리기한 합 구하기
        List<VocTaskCodeVo> autoApplyPrcdList = new ArrayList<>(taskList);
        autoApplyPrcdList.removeIf(task -> task.getAutoApplyPrcdSeq() == null || !task.getAutoApplyPrcdSeq().equals(param.get("prcdSeq")));
        for(VocTaskCodeVo task : autoApplyPrcdList){
            taskDeadlineSum += task.getDeadline();
        }

        // 3. 전체적용 + 개별적용 < deadline : 변경
        if(taskDeadlineSum < deadline){
            dao.updateDeadline(param);
            returnMap.put("msg", "변경되었습니다.");
        } else {
        // 4. 전체적용 > deadline && 전체적용 + 개별적용 > deadline : 자동적용 task row에서 현재 prcd를 삭제시킴
            dao.deleteAutoApplyPrcd(autoApplyPrcdList);
            dao.updateDeadline(param);
            returnMap.put("msg", "변경되었습니다.\n개별적용 task가 초기화되었습니다.");
        }
        returnMap.put("result", true);
        return returnMap;
    }
}
