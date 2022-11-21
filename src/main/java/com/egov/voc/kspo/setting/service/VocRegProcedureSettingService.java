package com.egov.voc.kspo.setting.service;


import com.egov.voc.base.common.model.AbstractTreeVo;
import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.stnd.CodeGeneration;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.setting.dao.VocRegProcedureSettingDao;
import com.egov.voc.kspo.common.stnd.ManageCodeCategoryEnum;

import com.egov.voc.kspo.setting.model.*;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.service.AbstractCrmService;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class VocRegProcedureSettingService extends AbstractCrmService {

    @Autowired
    VocRegProcedureSettingDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public Object vocManagementCodeTree(EzMap param) {
        return AbstractTreeVo.makeHierarchy(dao.vocManagementCodeTree(param));
    }

    public List<VocProcedureCodeVo> selectPrcdBasList(Map<String, Object> param) {
        List<VocProcedureCodeVo> prcdBasList = dao.selectPrcdBasList(param);
        List<VocProcedureVo> prcdList = dao.selectProcedureList(param);
        for(VocProcedureVo prcd : prcdList){
            prcdBasList.removeIf(prcdBas -> prcdBas.getPrcdSeq().equals(prcd.getPrcdSeq()));
        }

        return prcdBasList;
    }

    @SuppressWarnings("unchecked")
    public Object insertProcedure(EzMap param) {
        // 경로-절차 매핑을 위해 mcPrcdSeq 보관 리스트
        List<String> mcPrcdSeqList = new ArrayList<>();

        // 주처리부서 찾기
        log.debug("******* find dutyOrg *******");
        List<EzMap> orgList = dao.selectDutyOrgList(param);
        orgList.removeIf(value -> value.get("primaryOrgYn").equals("N"));
        String primaryOrg = orgList.get(0).get("orgId").toString();
        log.debug("******* find dutoOrg END *******");

        // 4. 분류코드별 절차 등록(voc_procedure)
        log.debug("******* insert procedure *******");
        List<String> prcdArr = (List<String>) param.get("prcdArr");
        String foreSeq = null;
        for(int i = 0; i < prcdArr.size(); i++){
            String prcdSeq = prcdArr.get(i);
            String maxMcPrcdSeq = dao.selectMaxMcPrcdSeq();
            String mcPrcdSeq = CodeGeneration.generateCode(maxMcPrcdSeq, CodeGeneration.PROCEDURE);

            VocProcedureVo prcd = new VocProcedureVo();
            prcd.setPrcdSeq(prcdSeq);
            prcd.setMcPrcdSeq(mcPrcdSeq);
            if(i > 0){
                prcd.setPrntsSeq(foreSeq);
            }

            // 참조 prcd의 담당부서/담당자/적용권한이 없다면 분류코드 담당부서를 SET
            VocProcedureCodeVo prcdBas = dao.selectPrcdBas(prcdSeq);
            if(prcdBas.getDutyEmp() == null && prcdBas.getDutyOrg() == null && prcdBas.getDutyRole() == null){
                prcd.setDutyOrg(primaryOrg);
            } else {
                prcd.setDutyOrg(prcdBas.getDutyOrg());
            }

            dao.insertProcedure(Utilities.beanToMap(prcd));

            // 절차가 task허용 & 자동적용 절차가 있다면 insert
            if(prcdBas.getTaskYn().equals("Y")){
                log.debug("******* insert task *******");
                param.put("autoApplyAllYn", "Y");
                param.put("autoApplyPrcdSeq", prcdSeq);
                List<VocTaskCodeVo> taskList = dao.selectTaskBasList(param);

                for(VocTaskCodeVo task : taskList){
                    EzMap taskMap = (EzMap) Utilities.beanToMap(task);
                    String maxMcTaskSeq = dao.selectMaxMcTaskSeq();
                    String mcTaskSeq = CodeGeneration.generateCode(maxMcTaskSeq, CodeGeneration.TASK);

                    taskMap.put("mcPrcdSeq", mcPrcdSeq);
                    int odrg = dao.selectTaskList(taskMap).size() + 1;
                    taskMap.put("odrg", odrg);
                    taskMap.put("mcTaskSeq", mcTaskSeq);

                    dao.insertTask(taskMap);
                }
                log.debug("******* insert task END ********");
            }


            mcPrcdSeqList.add(mcPrcdSeq);
            foreSeq = mcPrcdSeq;
            log.debug("******** insert procedure END *******");
        }

        // 5. 분류코드별 절차 매핑(voc_procedure_dir_conn)
        log.debug("******* insert procedure_dir_conn *******");
        param.put("mcPrcdSeqList", mcPrcdSeqList);

        int result = dao.insertProcedureDirConn(param);

        // 6. 후처리 - hierarchy 재정렬
        List<VocProcedureVo> afterPrcdList = dao.selectProcedureList(param);
        Collections.sort(afterPrcdList);

        for(int i = 0; i < afterPrcdList.size(); i++){
            VocProcedureVo procedure = afterPrcdList.get(i);
            if(i == 0){
                procedure.setPrntsSeq(null);
            } else {
                procedure.setPrntsSeq(afterPrcdList.get(i - 1).getMcPrcdSeq());
            }
            dao.updateProcedure(procedure);
        }

        return result;
    }

    public <T> List<T> selectProcedureList(EzMap param) {
        return dao.selectProcedureList(param);
    }

    public List<EzMap> selectDutyOrgList(Map<String, Object> param) {
        List<EzMap> orgList = dao.selectDutyOrgList(param);
        for(EzMap org : orgList){
            VocUtils.setOrgInfoToMap(org);
        }
        return orgList;
    }

    public Object insertDirOrg(EzMap param) {
        String primaryOrgYn = "Y";
        String dirCd = dao.selectDirCd(param);
        if(dirCd == null){
            String maxDirCd = dao.selectMaxDirCd();
            dirCd = CodeGeneration.generateCode(maxDirCd, CodeGeneration.DIRCD);
            param.put("dirCd", dirCd);
            dao.insertDirCd(param);
            dao.insertDirMcMapping(param);
        } else {
            primaryOrgYn = "N";
            param.put("dirCd", dirCd);
        }
        param.put("primaryOrgYn", primaryOrgYn);

        return dao.insertDirOrgMapping(param);
    }

    public Object selectDirCd(Map<String, Object> param) {
        return dao.selectDirCd(param);
    }

    @SuppressWarnings("unchecked")
    public Object deleteDirOrg(EzMap param) {
        List<Map<String, Object>> orgList = (List<Map<String, Object>>) param.get("orgList");
        orgList.removeIf(value -> value.get("primaryOrgYn").equals("Y"));

        param.put("orgList", orgList);
        return dao.deleteDirOrg(param);
    }

    @SuppressWarnings("unchecked")
    public Object updateDirOrg(EzMap param) {
        List<Map<String, Object>> orgList = new ArrayList<>((List<Map<String, Object>>) param.get("orgList"));
        orgList.removeIf(value -> value.get("primaryOrgYn").equals("N"));

        String msg = "수정되었습니다.";
        boolean result = true;
        if(orgList.size() > 1){
            msg = "주처리부서는 1개만 등록 가능합니다.";
            result = false;
        } else if(orgList.size() == 0){
            msg = "최소 1개의 주처리부서가 지정되어 있어야 합니다.";
            result = false;
        } else {
            dao.updateDirOrg(param);
        }

        param.put("msg", msg);
        param.put("result", result);
        return param;
    }

    public Object selectPrcdBas(Map<String, Object> param) {
        return dao.selectPrcdBas(param);
    }

    public List<VocTaskVo> selectTaskList(EzMap param) {
        return dao.selectTaskList(param);
    }

    public List<VocActivityVo> selectActivityList(EzMap param) {
        return dao.selectActivityList(param);
    }

    public <T> List<T> selectTaskBasList(EzMap param) {
        return dao.selectTaskBasList(param);
    }

    @SuppressWarnings("unchecked")
    public Object insertTask(EzMap param) {
        Map<String, Object> returnMap = new HashMap<>();
        String msg = "";
        int result = 0;

        String mcPrcdSeq = (String) param.get("mcPrcdSeq");
        VocProcedureVo prcd = dao.selectProcedure(param);
        int prcdDeadline = prcd.getDeadline();

        List<Map<String, Object>> taskList = (List<Map<String, Object>>) param.get("taskList");

        // deadline 초과여부 확인
        int taskDeadlineSum = 0;
        // 1. 신규등록 task list
        for(Map<String, Object> task : taskList){
            taskDeadlineSum += VocUtils.parseIntObject(task.get("deadline"));
        }
        // 2. 기존 등록 task list
        List<VocTaskVo> foreTaskList = dao.selectTaskList(param);
        for(VocTaskVo foreTask : foreTaskList){
            taskDeadlineSum += foreTask.getDeadline();
        }

        if(taskDeadlineSum > prcdDeadline){
            msg = "task 처리기한의 합이 절차의 처리기한보다 큽니다.";
        } else {
            for(Map<String, Object> task : taskList){
                String mcTaskSeq = CodeGeneration.generateCode(dao.selectMaxMcTaskSeq(), CodeGeneration.TASK);

                task.put("mcTaskSeq", mcTaskSeq);
                task.put("mcPrcdSeq", mcPrcdSeq);
                task.put("odrg", dao.selectTaskList(param).size() + 1);

                result += dao.insertTask(task);
            }
            msg = result + "건이 등록되었습니다.";
        }

        returnMap.put("msg", msg);
        returnMap.put("result", result);
        return returnMap;
    }

    public <T> List<T> selectActivityFuncBasList(EzMap param) {
        return dao.selectActivityFuncBasList(param);
    }

    @SuppressWarnings("unchecked")
    public Object insertActivity(Map<String, Object> param) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("formArr");
        param.putAll(VocUtils.formSerializeArrayToMap(list));
        param.put("actSeq", CodeGeneration.generateCode(dao.selectMaxActSeq(), CodeGeneration.ACTIVITY));
        return dao.insertActivity(param);
    }

    public Object deleteActivity(EzMap param) {
        return dao.deleteActivity(param);
    }

    @SuppressWarnings("unchecked")
    public Object deleteTask(EzMap param) {
        List<Map<String, Object>> taskList = (List<Map<String, Object>>) param.get("taskList");
        // task에 속한 activity 삭제
        for(Map<String, Object> task : taskList){
            List<VocActivityVo> actList = dao.selectActivityList(task);
            task.put("actList", actList);
            dao.deleteActivity(task);
        }
        return dao.deleteTask(param);
    }

    @SuppressWarnings("unchecked")
    public Object deleteProcedure(EzMap param) {
        Map<String, Object> returnMap = new HashMap<>();

        List<Map<String, Object>> prcdList = (List<Map<String, Object>>) param.get("prcdList");

        // 1. procedure에 속한 task 조회
        for(Map<String, Object> prcd : prcdList){
            List<VocTaskVo> taskList = dao.selectTaskList(prcd);

            if(!taskList.isEmpty()){
                // 2. task에 속한 activity 조회
                for(VocTaskVo task : taskList){
                    List<VocActivityVo> actList = dao.selectActivityList(Utilities.beanToMap(task));

                    if(!actList.isEmpty()){
                        // 3. activity 삭제
                        param.put("actList", actList);
                        dao.deleteActivity(param);
                    }
                }
                // 4. task 삭제
                param.put("taskList", taskList);
                dao.deleteTask(param);
            }
        }
        // 4. procedure 삭제
        int delResult = dao.deleteProcedure(param);

        returnMap.put("msg", delResult + "건이 삭제되었습니다.");
        returnMap.put("result", true);
        return returnMap;
    }

    public Object validateRequiredPrcd(Map<String, Object> param) {
        List<VocProcedureVo> prcdList = dao.selectProcedureList(param);
        if(prcdList.size() == 0){
            return true;
        }

        String target = (String) param.get("target");

        List<VocProcedureCodeVo> prcdBasList = dao.selectPrcdBasList(param);
        prcdBasList.removeIf(value -> value.getRequestCompulsoryYn(target).equals("N"));
        int requiredSize = prcdBasList.size();

        int compulsoryCnt = 0;
        for(int i = 0; i < prcdList.size(); i++){
            VocProcedureVo prcd = prcdList.get(i);
            VocProcedureCodeVo prcdBas = dao.selectPrcdBas(prcd);
            if(prcdBas.getRequestCompulsoryYn(target).equals("Y")){
                compulsoryCnt++;
            }
        }

        if(requiredSize != compulsoryCnt){
            return false;
        }

        return true;
    }
}
