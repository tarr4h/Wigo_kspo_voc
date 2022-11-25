package com.egov.voc.kspo.process.service;

import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.stnd.CodeGeneration;
import com.egov.voc.kspo.common.stnd.ManageCodeCategoryEnum;
import com.egov.voc.kspo.common.stnd.PrcdStatus;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.process.dao.VocRegistrationDao;
import com.egov.voc.kspo.process.model.VocRegPrcdVo;
import com.egov.voc.kspo.setting.model.VocManagementCodeVo;
import com.egov.voc.kspo.setting.model.VocProcedureVo;
import com.egov.voc.kspo.setting.service.VocAbstractService;
import com.egov.voc.sys.dao.ICrmDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
@Slf4j
public class VocRegistrationService extends VocAbstractService {

    @Autowired
    VocRegistrationDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public Object selectChannel(Map<String, Object> param) {
        param.put("comnCd", ManageCodeCategoryEnum.REGISTRATION.getComnCd());
        return dao.selectChannel(param);
    }

    public Object register(Map<String, Object> param) throws Exception{
        boolean insert = insert(param);
        if(!insert){
            param.put("result", false);
            return param;
        }

        log.debug("param = {}", param);
        // 채널코드를 통해서 일치하는 dir_cd를 구한다.
        param.put("managementCd", param.get("channel"));
        String dirCd = selectDirCd(param);

        // 일치하는 dir_cd가 없는 경우 상위 채널코드를 통해서 dir_cd를 구한다.(존재할때까지)
        if(dirCd == null){
            do {
                VocManagementCodeVo mc = selectManagementCode(param);
                if (mc.getPrntsCd() == null) {
                    param.put("msg", "해당 채널에 등록된 절차가 없습니다.");
                    param.put("result", false);
                    return param;
                }

                param.put("managementCd", mc.getPrntsCd());
                dirCd = selectDirCd(param);
            } while (dirCd == null);
        }

        // dirCd를 통해 절차목록를 조회
        param.put("dirCd", dirCd);
        List<VocProcedureVo> prcdList = selectProcedureList(param);

        // reg_prcd를 등록. deadline을 지정한다.
        int deadline = 0;
        for(VocProcedureVo prcd : prcdList){
            VocRegPrcdVo regPrcd = new VocRegPrcdVo();
            regPrcd.setRegPrcdSeq(CodeGeneration.generateCode(dao.selectMaxRegPrcdSeq(), CodeGeneration.REG_PROCEDURE));
            regPrcd.setRegSeq((String) param.get("regSeq"));
            regPrcd.setMcPrcdSeq(prcd.getMcPrcdSeq());
            deadline += prcd.getDeadline();
            regPrcd.setDeadline(VocUtils.setDefaultDeadlineDate(deadline));
            regPrcd.setModUsr(Utilities.getLoginUserCd());

            dao.insertRegProcedure(regPrcd);
        }

        // 등록 절차의 상태를 완료로 업데이트
        updateRegProcedureStatus(param, PrcdStatus.COMPLETE);

        return param;
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
