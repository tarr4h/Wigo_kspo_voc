package com.kspo.voc.program.setting.service;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.BaseVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.program.common.service.VocAbstractService;
import com.kspo.voc.program.common.stnd.CodeGeneration;
import com.kspo.voc.program.common.stnd.ManageCodeCategory;
import com.kspo.voc.program.common.util.VocUtils;
import com.kspo.voc.program.setting.dao.VocDtlMgmtPrcdDao;
import com.kspo.voc.program.setting.model.*;
import com.kspo.voc.sys.dao.IVocDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.program.setting.service.VocDtlMgmtPrcdService
 *  - VocDtlMgmtPrcdService.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : VocDtlMgmtPrcdService
 * @description :
 * @date : 2023-01-09
 */

@SuppressWarnings("unchecked")
@Service
@Slf4j
public class VocDtlMgmtPrcdService extends VocAbstractService {

    @Autowired
    VocDtlMgmtPrcdDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public Object vocMgmtChCdTree(EzMap param) {
        ManageCodeCategory.setComnCdTreeMap(param, ManageCodeCategory.CHANNEL);
        return AbstractTreeVo.makeHierarchy(selectVocMgmtCdTree(param));
    }

    public Object vocMgmtTpCdTree(EzMap param) {
        ManageCodeCategory.setComnCdTreeMap(param, ManageCodeCategory.TYPE);
        return AbstractTreeVo.makeHierarchy(selectVocMgmtCdTree(param));
    }

    public Object selectComboDirCd(Map<String, Object> param) {
        String dirCd = null;

        List<VocDirMgmtVo> dirMgmtListByMgmtCd = dao.selectDirMgmtList(param);

        for(VocDirMgmtVo dirMgmtVo : dirMgmtListByMgmtCd){
            List<VocDirMgmtVo> dirMgmtListByDirCd = dao.selectDirMgmtList(Utilities.beanToMap(dirMgmtVo));

            int cnt = 0;
            for(VocDirMgmtVo dirMgmtVoByDirCd : dirMgmtListByDirCd){
                String mgmtCd = dirMgmtVoByDirCd.getMgmtCd();
                if(mgmtCd.equals(param.get("chMgmtCd")) || mgmtCd.equals(param.get("tpMgmtCd"))){
                    cnt++;
                }
            }

            if(cnt == 2){
                dirCd = dirMgmtVo.getDirCd();
                break;
            }
        }

        if(dirCd == null){
            String maxDirCd = dao.selectMaxDirCd();
            param.put("dirCd", CodeGeneration.generateCode(maxDirCd, CodeGeneration.DIRCD));
            dao.insertDirCd(param);
            dao.insertDirMgmtCombo(param);
        } else {
            param.put("dirCd", dirCd);
        }

        return param;
    }

    public Object insertMgmtPrcd(Map<String, Object> param) throws EgovBizException {
        List<Map<String, Object>> prcdList = (List<Map<String, Object>>) param.get("prcdList");

        List<VocMgmtPrcdVo> existMgmtPrcdList = selectMgmtPrcdList(param);

        int i = 1;
        for(VocMgmtPrcdVo existMgmtPrcd : existMgmtPrcdList){
            existMgmtPrcd.setMgmtPrcdOrdr(i);
            dao.updateMgmtPrcd(existMgmtPrcd);
            i++;
        }

        for(Map<String, Object> prcd : prcdList){
            String maxMgmtPrcdCd = dao.selectMaxMgmtPrcdCd();
            prcd.put("mgmtPrcdCd", CodeGeneration.generateCode(maxMgmtPrcdCd, CodeGeneration.MGMT_PRCD));
            prcd.put("mgmtPrcdOrdr", i);
            dao.insertMgmtPrcd(prcd);
            i++;

            prcd.put("dirCd", param.get("dirCd"));
            dao.insertDirPrcd(prcd);

            List<VocTaskBasVo> taskList = selectTaskBasList(prcd);
            if(taskList.size() != 0){
                int j = 1;
                for(VocTaskBasVo task : taskList){
                    Map<String, Object> taskMap = Utilities.beanToMap(task);
                    String maxMgmtTaskCd = dao.selectMaxMgmtTaskCd();
                    taskMap.put("mgmtTaskCd", CodeGeneration.generateCode(maxMgmtTaskCd, CodeGeneration.MGMT_TASK));
                    taskMap.put("mgmtPrcdCd", prcd.get("mgmtPrcdCd"));
                    taskMap.put("mgmtTaskOrdr", j);

                    dao.insertMgmtTask(taskMap);
                    j++;
                }
            }

        }

        return (i-1);
    }

    public Object insertMgmtTask(EzMap param) {
        List<Map<String, Object>> taskList = (List<Map<String, Object>>) param.get("taskList");

        int i = 1;
        List<VocMgmtTaskVo> existTaskList = selectMgmtTaskList(param);
        for(VocMgmtTaskVo existTask : existTaskList){
            existTask.setMgmtTaskOrdr(i);
            dao.updateMgmtTask(existTask);
            i++;
        }

        for(Map<String, Object> task : taskList){
            String maxTaskCd = dao.selectMaxMgmtTaskCd();
            task.put("mgmtTaskCd", CodeGeneration.generateCode(maxTaskCd, CodeGeneration.MGMT_TASK));
            task.put("mgmtPrcdCd", param.get("mgmtPrcdCd"));
            task.put("mgmtTaskOrdr", i);
            dao.insertMgmtTask(task);
            i++;
        }

        return taskList.size();
    }

    public Object insertMgmtActv(EzMap param) {
        List<Map<String, Object>> actvList = (List<Map<String, Object>>) param.get("actvList");

        int i = 1;
        List<VocMgmtActvVo> existActvList = selectMgmtActvList(param);
        for(VocMgmtActvVo existActv : existActvList){
            existActv.setMgmtActvOrdr(i);
            dao.updateMgmtActv(existActv);
            i++;
        }

        for(Map<String, Object> actv : actvList){
            String maxActvCd = dao.selectMaxMgmtActvCd();
            actv.put("mgmtActvCd", CodeGeneration.generateCode(maxActvCd, CodeGeneration.MGMT_ACTV));
            actv.put("mgmtTaskCd", param.get("mgmtTaskCd"));
            actv.put("mgmtActvOrdr", i);
            dao.insertMgmtActv(actv);
            i++;
        }

        return actvList.size();
    }

    public List<VocPrcdBasVo> selectAvailablePrcdBasList(EzMap param) {
        List<VocPrcdBasVo> list = selectPrcdBasList(param);

        // 이미 등록된 절차는 제외처리.
        List<VocMgmtPrcdVo> mgmtPrcdList = selectMgmtPrcdList(param);
        for(VocMgmtPrcdVo mgmtPrcdVo : mgmtPrcdList){
            list.removeIf(value -> value.getPrcdCd().equals(mgmtPrcdVo.getPrcdCd()));
        }
        
        // 채널의 VOC 마스터 절차에 등록되지 않은 절차는 제외처리
        List<VocPrcdBasVo> returnList = new ArrayList<>();
        param.put("dirCd", param.get("chDirCd"));
        List<VocMgmtPrcdVo> chMgmtPrcdList = selectMgmtPrcdList(param);
        for(VocPrcdBasVo prcdBas : list){
            int cnt = 0;
            for(VocMgmtPrcdVo chMgmtPrcdVo : chMgmtPrcdList){
                if(prcdBas.getPrcdCd().equals(chMgmtPrcdVo.getPrcdCd())){
                    cnt++;
                }
            }

            if(cnt != 0){
                returnList.add(prcdBas);
            }
        }

        return returnList;
    }

    public List<VocTaskBasVo> selectAvailableTaskBasList(EzMap param) {
        List<VocTaskBasVo> list = selectTaskBasList(param);

        List<VocMgmtTaskVo> mgmtTaskList = selectMgmtTaskList(param);
        for(VocMgmtTaskVo mgmtTaskVo : mgmtTaskList){
            list.removeIf(value -> value.getTaskCd().equals(mgmtTaskVo.getTaskCd()));
        }

        return list;
    }

    public List<VocActvBasVo> selectAvailableActvBasList(EzMap param) {
        List<VocActvBasVo> list = selectActvBasList(param);

        List<VocMgmtActvVo> mgmtActvList = selectMgmtActvList(param);
        for(VocMgmtActvVo mgmtActvVo : mgmtActvList){
            list.removeIf(value -> value.getActvCd().equals(mgmtActvVo.getActvCd()));
        }

        return list;
    }

    public Object deleteMgmtActvList(Map<String, Object> param) {
        return dao.deleteMgmtActvList(param);
    }

    public Object deleteMgmtTaskList(Map<String, Object> param) {
        dao.deleteChildMgmtActvList(param);
        return dao.deleteMgmtTaskList(param);
    }

    public Object deleteMgmtPrcdList(Map<String, Object> param) {
        List<Map<String, Object>> prcdList = (List<Map<String, Object>>) param.get("prcdList");
        for(Map<String, Object> prcd : prcdList){
            Map<String, Object> taskParam = new HashMap<>();

            List<VocMgmtTaskVo> taskList = selectMgmtTaskList(prcd);
            if(taskList.size() != 0){
                taskParam.put("taskList", taskList);
                deleteMgmtTaskList(taskParam);
            }
        }

        int mgmtPrcdDelRslt = dao.deleteMgmtPrcdList(param);
        dao.deleteDirPrcd(param);
        return mgmtPrcdDelRslt ;
    }

    public Object updateMgmtList(EzMap param, Class<? extends VocMgmtBaseVo> cls){
        List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("rows");

        int updateRslt = 0;
        for(Map<String, Object> map : list){
            VocMgmtBaseVo mbv = Utilities.mapToBean(map, cls);
            if(param.get("orgId") != null && !param.get("orgId").equals("")){
                mbv.setDutyOrgId((String) param.get("orgId"));
            } else if(param.get("ddln") != null && !param.get("ddln").equals("")){
                Map<String, Object> ddln = (Map<String, Object>) param.get("ddln");
                VocUtils.sumUpDeadline(ddln);
                mbv.setDdlnSec(VocUtils.parseIntObject(ddln.get("ddlnSec")));
            }

            updateRslt += updateMgmt(mbv);
        }

        return updateRslt;
    }

    public int updateMgmt(VocMgmtBaseVo vo){
        Class<? extends VocMgmtBaseVo> cls = vo.getClass();
        if(cls.equals(VocMgmtPrcdVo.class)){
            return dao.updateMgmtPrcd((VocMgmtPrcdVo) vo);
        } else if(cls.equals(VocMgmtTaskVo.class)){
            return dao.updateMgmtTask((VocMgmtTaskVo) vo);
        } else if(cls.equals(VocMgmtActvVo.class)) {
            return dao.updateMgmtActv((VocMgmtActvVo) vo);
        } else {
            return 0;
        }
    }

}
