package com.kspo.voc.kspo.process.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.kspo.common.service.VocAbstractService;
import com.kspo.voc.kspo.common.stnd.CodeGeneration;
import com.kspo.voc.kspo.common.stnd.PrcdStatus;
import com.kspo.voc.kspo.common.util.VocUtils;
import com.kspo.voc.kspo.process.dao.VocRegistrationDao;
import com.kspo.voc.kspo.process.model.VocRegPrcdVo;
import com.kspo.voc.kspo.process.model.VocRegistrationVo;
import com.kspo.voc.kspo.setting.model.VocManagementCodeVo;
import com.kspo.voc.kspo.setting.model.VocProcedureVo;
import com.kspo.voc.sys.dao.ICrmDao;

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

    public Object register(Map<String, Object> param, PrcdStatus prcdStatus) throws Exception {
        log.debug("param = {}", param);
        String regSeq = (String) param.get("regSeq");
        boolean result;

        if(regSeq == null){
            result = insertRegstration(param);
        } else {
            result = insert(param);
        }
        if(!result){
            param.put("result", false);
            return param;
        }

        // 등록 절차의 상태를 업데이트
        updateRegProcedureStatus(param, prcdStatus);
        param.put("result", true);
        return param;
    }

    /**
     * voc 등록(insert 통해서) + 채널에 해당하는 절차 등록
     * @param param - regSeq
     * @return boolean true/false
     * @throws Exception
     */
    public boolean insertRegstration(Map<String, Object> param) throws Exception{
        boolean insert = insert(param);
        if(!insert){
            return false;
        }
        
        // 채널코드를 통해서 일치하는 dir_cd를 구한다.
        param.put("managementCd", param.get("channel"));
        String dirCd = selectDirCd(param);

        // 일치하는 dir_cd가 없는 경우 상위 채널코드를 통해서 dir_cd를 구한다.(존재할때까지)
        if(dirCd == null){
            do {
                VocManagementCodeVo mc = selectManagementCode(param);
                if (mc.getPrntsCd() == null) {
                    param.put("msg", "해당 채널에 등록된 절차가 없습니다.");
                    return false;
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
            regPrcd.setDeadlineDt(VocUtils.setDefaultDeadlineDate(deadline));
            regPrcd.setModUsr(Utilities.getLoginUserCd());

            dao.insertRegProcedure(regPrcd);
        }

        return true;
    }

    /**
     * 검증 후 voc_registration insert/update 진행
     * @param param - regSeq
     * @return booean true/false
     */
    public boolean insert(Map<String, Object> param) {
        if(!insertValidation(param)){
            return false;
        }

        param.putAll(VocUtils.formSerializeArrayToMap((List<Map<String, Object>>) param.get("formArr")));
        List<String> chList = (List<String>) param.get("chList");
        param.put("channel", chList.get(chList.size() - 1));
        if(param.get("regSeq") == null){
            param.put("regSeq", CodeGeneration.generateCode(dao.selectMaxSeq(), CodeGeneration.REGISTRATION));
            dao.insert(param);
        } else {
            dao.update(param);
        }

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
                    case "regComment" : continue;
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

        List<String> typeList = (List<String>) param.get("typeList");
        if(typeList.size() == 0){
            param.put("msg", "유형이 선택되지 않았습니다.");
            return false;
        }

        List<String> locationList = (List<String>) param.get("locationList");
        if(locationList.size() == 0){
            param.put("msg", "장소가 선택되지 않았습니다.");
            return false;
        }

        return true;
    }


    public VocRegistrationVo select(Map<String, Object> param) {
        return dao.select(param);
    }

    public List<Map<String, Object>> selectUpperChannel(String channel) {


        return null;
    }
}
