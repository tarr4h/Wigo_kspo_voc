package com.egov.voc.kspo.setting.service;


import com.egov.voc.base.common.model.AbstractTreeVo;
import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.common.stnd.CodeGeneration;
import com.egov.voc.kspo.setting.dao.VocRegProcedureSettingDao;
import com.egov.voc.kspo.common.stnd.ManageCodeCategoryEnum;
import com.egov.voc.kspo.setting.model.VocProcedureVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        log.debug("param = {}", param);
        log.debug("arr = {}", param.get("arr"));
        log.debug("managementCd = {}", param.get("managementCd"));



        // 1. 경로코드 생성 및 등록(voc_dir_cd)
        String maxDirCd = dao.selectMaxDirCd();
        String dirCd = CodeGeneration.generateCode(maxDirCd, CodeGeneration.DIRCD);
        log.debug("dirCd = {}", dirCd);
        param.put("dirCd", dirCd);
//        dao.insertDirCd(param);

        // 2. 경로코드 별 분류 매핑(voc_dir_mc_mapping)
//        dao.insertDirMcMapping(param);

        // 3. 담당부서 등록(voc_dir_org_mapping)
//        String orgId =

        // 4. 분류코드별 절차 등록(voc_procedure)
        List<String> prcdArr = (List<String>) param.get("arr");
        for(int i = 0; i < prcdArr.size(); i++){
            String prcdSeq = prcdArr.get(i);

            String maxPrcdSeq = dao.selectMaxPrcdSeq();
            String mcPrcdSeq = CodeGeneration.generateCode(maxPrcdSeq, CodeGeneration.PROCEDURE);
            log.debug("mcPrcdSeq = {}", mcPrcdSeq);

            VocProcedureVo prcd = new VocProcedureVo();
            prcd.setPrcdSeq(prcdSeq);
            prcd.setMcPrcdSeq(mcPrcdSeq);
            if(i > 0){
                String prntsSeq = prcdArr.get(i - 1);
                prcd.setPrntsSeq(prntsSeq);
            }
            log.debug("prcd = {}", prcd);

            dao.insertProcedure(prcd);
        }

        // 5. 분류코드별 절차 매핑(voc_procedure_dir_conn)
        return null;
    }
}
