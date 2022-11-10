package com.egov.voc.kspo.setting.service;


import com.egov.voc.base.common.model.AbstractTreeVo;
import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.common.stnd.CodeGeneration;
import com.egov.voc.kspo.setting.dao.VocRegProcedureSettingDao;
import com.egov.voc.kspo.common.stnd.ManageCodeCategoryEnum;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Object insertProcedure(EzMap param) {
        log.debug("param = {}", param);
        log.debug("arr = {}", param.get("arr"));
        log.debug("managementCd = {}", param.get("managementCd"));

        CodeGeneration.generateCode("PB00000001", CodeGeneration.PROCEDURE);
        CodeGeneration.generateCode(null, CodeGeneration.PROCEDURE);

        // 1. 경로코드 생성 및 등록(voc_dir_cd)

        // 2. 경로코드 별 분류 매핑(voc_dir_mc_mapping)

        // 3. 분류코드별 절차 등록(voc_procedure)

        // 4. 분류코드별 절차 매핑(voc_procedure_dir_conn)
        return null;
    }
}
