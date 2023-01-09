package com.kspo.voc.program.setting.service;

import com.github.mustachejava.Code;
import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.program.common.service.VocAbstractService;
import com.kspo.voc.program.common.stnd.CodeGeneration;
import com.kspo.voc.program.common.stnd.ManageCodeCategory;
import com.kspo.voc.program.setting.dao.VocMgmtPrcdDao;
import com.kspo.voc.program.setting.model.VocDirOrgVo;
import com.kspo.voc.sys.dao.IVocDao;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        ManageCodeCategory.setComnCdTreeMap(param, ManageCodeCategory.CHANNEL);
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
            msg.append("주처리부서는 1개의 부서만 지정 가능합니다.");
            result = false;
        } else {
            dao.updateDirOrg(param);
            msg.append("저장되었습니다.");
        }

        param.put("msg", msg.toString());
        param.put("result", result);

        return param;
    }

    public Object deleteDirOrg(EzMap param) {
        List<Map<String, Object>> rows = (List<Map<String, Object>>) param.get("rows");
        String dirCd = (String) param.get("dirCd");

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
            msg.append("주처리부서로 설정된 부서는 삭제가 불가합니다.\n변경 후 진행해 주세요.");
            result = false;
        } else {
            int delResult = dao.deleteDirOrg(param);
            msg.append(delResult).append("건이 삭제되었습니다.");
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

        param.put("msg", (i-1)+"건이 등록되었습니다.");
        param.put("result", true);

        return param;
    }

    public Object deleteMgmtPrcd(EzMap param) {
        boolean result = false;
        StringBuilder msg = new StringBuilder();

        List<EzMap> list = (List<EzMap>) param.get("mgmtPrcdList");

        log.debug("list null or size is 0 = {}", list == null || list.size() == 0);
        if(list == null || list.size() == 0){
            msg.append("삭제할 관리절차를 먼저 선택해 주세요.");
        } else {
            dao.deleteMgmtPrcd(param);
            result = true;
            msg.append("삭제되었습니다.");
        }

        param.put("msg", msg);
        param.put("result", result);

        return param;
    }
}
