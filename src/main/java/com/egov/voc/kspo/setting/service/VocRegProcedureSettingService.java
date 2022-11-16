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
import com.egov.voc.sys.model.CrmOrgBaseVo;
import com.egov.voc.sys.service.AbstractCrmService;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.openxmlformats.schemas.drawingml.x2006.main.CTEffectStyleItem;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        // single
        ManageCodeCategoryEnum.setComnCdTreeMap(param, ManageCodeCategoryEnum.REGISTRATION);
        // multiple example
//        ManageCodeCategoryEnum.setComnCdListTreeMap(param, Arrays.asList(ManageCodeCategoryEnum.REGISTRATION, ManageCodeCategoryEnum.RECEIPT));
        return AbstractTreeVo.makeHierarchy(dao.vocManagementCodeTree(param));
    }

    public Object selectPrcdBasList(Map<String, Object> param) {
        return dao.selectPrcdBasList(param);
    }

    @SuppressWarnings("unchecked")
    public Object insertProcedure(EzMap param) {
        // 1. 경로코드 조회(voc_dir_cd)
        log.debug("******* select dirCd *******");
        String dirCd = dao.selectDirCd(param);
        param.put("dirCd", dirCd);

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
        return dao.insertProcedureDirConn(param);
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
        String mcPrcdSeq = (String) param.get("mcPrcdSeq");
        List<Map<String, Object>> taskList = (List<Map<String, Object>>) param.get("taskList");
        log.debug("mcPrcdSeq = {}", mcPrcdSeq);
        log.debug("tasklist = {}", taskList);

        int result = 0;
        for(Map<String, Object> task : taskList){
            String maxMcTaskSeq = dao.selectMaxMcTaskSeq();
            String mcTaskSeq = CodeGeneration.generateCode(maxMcTaskSeq, CodeGeneration.TASK);
            int odrg = dao.selectTaskList(param).size() + 1;
            task.put("mcTaskSeq", mcTaskSeq);
            task.put("mcPrcdSeq", mcPrcdSeq);
            task.put("odrg", odrg);
            result += dao.insertTask(task);
        }
        return result;
    }
}
