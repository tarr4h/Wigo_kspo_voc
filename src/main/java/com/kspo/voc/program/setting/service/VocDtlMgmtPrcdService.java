package com.kspo.voc.program.setting.service;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.program.common.service.VocAbstractService;
import com.kspo.voc.program.common.stnd.CodeGeneration;
import com.kspo.voc.program.common.stnd.ManageCodeCategory;
import com.kspo.voc.program.setting.dao.VocDtlMgmtPrcdDao;
import com.kspo.voc.program.setting.model.*;
import com.kspo.voc.sys.dao.IVocDao;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Object insertDirPrcd(Map<String, Object> param) throws EgovBizException {
        List<Map<String, Object>> prcdList = (List<Map<String, Object>>) param.get("prcdList");

        int i = 1;
        for(Map<String, Object> prcd : prcdList){
            log.debug("prcd : {}", prcd);
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


    public Object insertMgmtActv(EzMap param) {
        List<Map<String, Object>> actvList = (List<Map<String, Object>>) param.get("actvList");

        int i = 1;
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

        List<VocMgmtPrcdVo> mgmtPrcdList = selectMgmtPrcdList(param);
        for(VocMgmtPrcdVo mgmtPrcdVo : mgmtPrcdList){
            list.removeIf(value -> value.getPrcdCd().equals(mgmtPrcdVo.getPrcdCd()));
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

    public List<VocTaskBasVo> selectAvailableTaskBasList(EzMap param) {
        List<VocTaskBasVo> list = selectTaskBasList(param);

        List<VocMgmtTaskVo> mgmtTaskList = selectMgmtTaskList(param);
        for(VocMgmtTaskVo mgmtTaskVo : mgmtTaskList){
            list.removeIf(value -> value.getTaskCd().equals(mgmtTaskVo.getTaskCd()));
        }

        return list;
    }
}
