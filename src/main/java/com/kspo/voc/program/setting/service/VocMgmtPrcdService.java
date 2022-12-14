package com.kspo.voc.program.setting.service;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.program.common.service.VocAbstractService;
import com.kspo.voc.program.common.stnd.CodeGeneration;
import com.kspo.voc.program.common.stnd.MgmtCodeCategory;
import com.kspo.voc.program.setting.dao.VocMgmtPrcdDao;
import com.kspo.voc.sys.dao.IVocDao;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.program.setting.service.VocMgmtPrcdService
 *  - VocMgmtPrcdService.java
 * </pre>
 *
 * @ClassName     : VocMgmtPrcdService
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-05
 *
*/

@SuppressWarnings("unchecked")
@Service
@Slf4j
public class VocMgmtPrcdService extends VocAbstractService {

    @Autowired
    VocMgmtPrcdDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public Object vocMgmtCdTree(EzMap param) {
        MgmtCodeCategory.setMgmtCdTreeMap(param, MgmtCodeCategory.CHANNEL);
        return AbstractTreeVo.makeHierarchy(selectVocMgmtCdTree(param));
    }

    public Object selectDirCd(Map<String, Object> param) throws EgovBizException {
        if(selectChannelDirCd(param) == null){
            String maxDirCd = dao.selectMaxDirCd();
            param.put("dirCd", CodeGeneration.generateCode(maxDirCd, CodeGeneration.DIRCD));
            dao.insertDirCd(param);
            dao.insertDirMgmt(param);
        }

        return selectChannelDirCd(param);
    }

    public Object insertDirOrg(EzMap param) throws EgovBizException {
        int orgCnt = selectDirOrg(param).size();
        String primProcOrgYn = "N";
        if(orgCnt == 0){
            primProcOrgYn = "Y";
        }
        param.put("primProcOrgYn", primProcOrgYn);

        return dao.insertDirOrg(param);
    }

    public Object updateDirOrg(EzMap param) {
        List<Map<String, Object>> rows = (List<Map<String, Object>>) param.get("rows");

        int cnt = 0;
        for(Map<String, Object> dirOrg : rows){
            String primProcOrgYn = (String) dirOrg.get("primProcOrgYn");
            if(primProcOrgYn.equals("Y")){
                cnt++;
            }
        }

        StringBuilder msg = new StringBuilder();
        boolean result = true;

        if(cnt > 1){
            msg.append("?????????????????? 1?????? ????????? ?????? ???????????????.");
            result = false;
        } else {
            dao.updateDirOrg(param);
            msg.append("?????????????????????.");
        }

        param.put("msg", msg.toString());
        param.put("result", result);

        return param;
    }

    public Object deleteDirOrg(EzMap param) {
        List<Map<String, Object>> rows = (List<Map<String, Object>>) param.get("rows");

        int cnt = 0;
        for(Map<String, Object> dirOrg : rows){
            String primProcOrgYn = (String) dirOrg.get("primProcOrgYn");
            if(primProcOrgYn.equals("Y")){
                cnt++;
            }
        }

        StringBuilder msg = new StringBuilder();
        boolean result = true;

        if(cnt > 0){
            msg.append("?????????????????? ????????? ????????? ????????? ???????????????.\n?????? ??? ????????? ?????????.");
            result = false;
        } else {
            int delResult = dao.deleteDirOrg(param);
            msg.append(delResult).append("?????? ?????????????????????.");
        }

        param.put("msg", msg.toString());
        param.put("result", result);

        return param;
    }

    public Object insertDirPrcd(EzMap param) {
        List<Map<String, Object>> prcdBasList = (List<Map<String, Object>>) param.get("prcdBasList");

        int i = 1;
        for(Map<String, Object> prcdBas : prcdBasList){
            String maxMgmtPrcdCd = dao.selectMaxMgmtPrcdCd();
            prcdBas.put("mgmtPrcdCd", CodeGeneration.generateCode(maxMgmtPrcdCd, CodeGeneration.MGMT_PRCD));
            prcdBas.put("mgmtPrcdOrdr", i);
            dao.insertMgmtPrcd(prcdBas);

            prcdBas.put("dirCd", param.get("dirCd"));
            dao.insertDirPrcd(prcdBas);
            i++;
        }

        param.put("msg", (i-1)+"?????? ?????????????????????.");
        param.put("result", true);

        return param;
    }

    public Object deleteMgmtPrcd(EzMap param) {
        boolean result = false;
        StringBuilder msg = new StringBuilder();

        List<EzMap> list = (List<EzMap>) param.get("mgmtPrcdList");

        log.debug("list null or size is 0 = {}", list == null || list.size() == 0);
        if(list == null || list.size() == 0){
            msg.append("????????? ??????????????? ?????? ????????? ?????????.");
        } else {
            dao.deleteMgmtPrcd(param);
            result = true;
            msg.append("?????????????????????.");
        }

        param.put("msg", msg);
        param.put("result", result);

        return param;
    }
}
