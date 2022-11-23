package com.egov.voc.kspo.process.service;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.stnd.CodeGeneration;
import com.egov.voc.kspo.common.stnd.ManageCodeCategoryEnum;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.process.dao.VocRegistrationDao;
import com.egov.voc.kspo.setting.dao.VocManagementCodeDao;
import com.egov.voc.kspo.setting.dao.VocRegProcedureSettingDao;
import com.egov.voc.kspo.setting.model.VocActivityVo;
import com.egov.voc.kspo.setting.model.VocManagementCodeVo;
import com.egov.voc.kspo.setting.model.VocProcedureVo;
import com.egov.voc.kspo.setting.model.VocTaskVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
@Slf4j
public class VocRegistrationService extends AbstractCrmService {

    @Autowired
    VocRegistrationDao dao;

    @Autowired
    VocRegProcedureSettingDao rDao;

    @Autowired
    VocManagementCodeDao mDao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public Object selectChannel(Map<String, Object> param) {
        param.put("comnCd", ManageCodeCategoryEnum.REGISTRATION.getComnCd());
        return dao.selectChannel(param);
    }

    public Object register(Map<String, Object> param) {
        if(!insert(param)){
            param.put("result", false);
            return param;
        }

        log.debug("param = {}", param);
        // 채널코드를 통해서 일치하는 dir_cd를 구한다.
        param.put("managementCd", param.get("channel"));
        String dirCd = rDao.selectDirCd(param);

        // 일치하는 dir_cd가 없는 경우 상위 채널코드를 통해서 dir_cd를 구한다.(존재할때까지)
        if(dirCd == null){
            do {
                VocManagementCodeVo mc = mDao.select(param);
                if (mc.getPrntsCd() == null) {
                    param.put("msg", "해당 채널에 등록된 절차가 없습니다.");
                    param.put("result", false);
                    return param;
                }

                param.put("managementCd", mc.getPrntsCd());
                dirCd = rDao.selectDirCd(param);
                log.debug("dirCd2 = {}", dirCd);
            } while (dirCd == null);
        }

        // 절차목록(이하 task, activity 등)을 가져와서 등록한다.
        param.put("dirCd", dirCd);
        List<VocProcedureVo> prcdList = rDao.selectProcedureList(param);

        for(VocProcedureVo prcd : prcdList) {
            log.debug("prcd = {}", prcd.getPrcdNm());

            List<VocTaskVo> taskList = rDao.selectTaskList(Utilities.beanToMap(prcd));
            for(VocTaskVo task : taskList){
                log.debug("task = {}", task.getTaskNm());

                List<VocActivityVo> actList = rDao.selectActivityList(Utilities.beanToMap(task));

            }
        }

        return null;
    }

    public boolean insert(Map<String, Object> param) {
        if(!insertValidation(param)){
            return false;
        }

        param.putAll(VocUtils.formSerializeArrayToMap((List<Map<String, Object>>) param.get("formArr")));
        List<String> chList = (List<String>) param.get("chList");
        param.put("channel", chList.get(chList.size() - 1));

        param.put("regSeq", CodeGeneration.generateCode(dao.selectMaxSeq(), CodeGeneration.REGISTRATION));

        dao.insert(param);
        param.put("msg", "등록되었습니다.");
        return true;
    }

    public boolean insertValidation(Map<String, Object> param){
        List<Map<String, Object>> formArr = (List<Map<String, Object>>) param.get("formArr");
        for(Map<String, Object> col : formArr){
            if(col.get("value") == null || col.get("value").equals("")){
                String target = "";
                switch((String) col.get("name")){
                    case "title" : target = "제목";break;
                    case "content" : target = "내용";break;
                }
                param.put("msg", target + "이 입력되지 않았습니다.");
                return false;
            }
        }

        List<String> chList = (List<String>) param.get("chList");
        if(chList.size() == 0){
            param.put("msg", "채널이 선택되지 않았습니다.");
            return false;
        }

        return true;
    }


}
