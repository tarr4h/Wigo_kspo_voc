package com.egov.voc.kspo.setting.service;


import com.egov.voc.base.common.model.AbstractTreeVo;
import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.common.stnd.CodeGeneration;
import com.egov.voc.kspo.setting.dao.VocRegProcedureSettingDao;
import com.egov.voc.kspo.common.stnd.ManageCodeCategoryEnum;

import com.egov.voc.kspo.setting.model.VocManagementCodeVo;
import com.egov.voc.kspo.setting.model.VocProcedureCodeVo;
import com.egov.voc.kspo.setting.model.VocProcedureVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.service.AbstractCrmService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        // 1. 경로코드 생성 및 등록(voc_dir_cd)
        String maxDirCd = dao.selectMaxDirCd();
        String dirCd = CodeGeneration.generateCode(maxDirCd, CodeGeneration.DIRCD);
        log.debug("dirCd = {}", dirCd);
        param.put("dirCd", dirCd);
        dao.insertDirCd(param);

        // 2. 경로코드 별 분류 매핑(voc_dir_mc_mapping)
        dao.insertDirMcMapping(param);

        // 3. 담당부서 등록(voc_dir_org_mapping)
        dao.insertDirOrgMapping(param);

        // 4. 분류코드별 절차 등록(voc_procedure)
            // 경로-절차 매핑을 위해 mcPrcdSeq 보관 리스트
            List<String> mcPrcdSeqList = new ArrayList<>();

        List<String> prcdArr = (List<String>) param.get("prcdArr");
        String foreSeq = null;
        for(int i = 0; i < prcdArr.size(); i++){
            String prcdSeq = prcdArr.get(i);
            String maxMcPrcdSeq = dao.selectMaxMcPrcdSeq();
            String mcPrcdSeq = CodeGeneration.generateCode(maxMcPrcdSeq, CodeGeneration.PROCEDURE);
            log.debug("mcPrcdSeq = {}", mcPrcdSeq);

            VocProcedureVo prcd = new VocProcedureVo();
            prcd.setPrcdSeq(prcdSeq);
            prcd.setMcPrcdSeq(mcPrcdSeq);
            if(i > 0){
                prcd.setPrntsSeq(foreSeq);
            }
            log.debug("prcd = {}", prcd);

            dao.insertProcedure(prcd);

            mcPrcdSeqList.add(mcPrcdSeq);
            foreSeq = mcPrcdSeq;
        }

        // 5. 분류코드별 절차 매핑(voc_procedure_dir_conn)
        param.put("mcPrcdSeqList", mcPrcdSeqList);

        return dao.insertProcedureDirConn(param);
    }

    public <T> List<T> selectProcedureList(EzMap param) {
//        return dao.selectProcedureList(param);
        return null;
    }
}
