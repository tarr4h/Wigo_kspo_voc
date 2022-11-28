package com.egov.voc.kspo.setting.service;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.ITreeVo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.stnd.PrcdStatus;
import com.egov.voc.kspo.process.model.VocRegPrcdVo;
import com.egov.voc.kspo.setting.dao.VocProcessCodeDao;
import com.egov.voc.kspo.setting.model.VocProcedureVo;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Slf4j
public abstract class VocAbstractService extends AbstractCrmService {

    @Autowired
    VocProcessCodeDao dao;

    public List<? extends ITreeVo> selectVocManagementCodeTree(Map<String, Object> param){
        return dao.selectVocManagementCodeTree(param);
    }

    /**
     * 등록절차의 상태 업데이트
     *  - 등록 voc 건의 절차를 조회한 뒤, 규칙을 통해 update할 절차를 선택 및 업데이트
     * @param param : require REG_SEQ
     * @param requestStatus : PrcdStatus Enum에서 status를 선택.(string)
     * @return : insert result(integer)
     */
    @SuppressWarnings("unchecked")
    public void updateRegProcedureStatus(Map<String, Object> param, PrcdStatus requestStatus) throws Exception{
        String regSeq = (String) param.get("regSeq");
        if(regSeq == null){
            throw new Exception("*** parameter에 [regSeq]가 존재하지 않습니다. ***");
        }
        List<VocRegPrcdVo> regPrcdList = dao.selectRegPrcdList(param);

        int cntN = 0;
        int index = 0;
        for(VocRegPrcdVo regPrcd : regPrcdList){
            if(regPrcd.getStatus().equals(PrcdStatus.STNDBY.getStatus())){
                cntN++;
            }
        }

        if(cntN == 0) {
            // 1. N이 존재하지 않는다면, Y가 아닌 절차를 업데이트 -> list에서 status = 'Y' 제거
            regPrcdList.removeIf(value -> value.getStatus().equals(PrcdStatus.COMPLETE.getStatus()));
        } else if(cntN != regPrcdList.size()){
            // 2. 모두 N이 아니고 N이 존재한다면, 가장 공통코드 우선순위가 낮은 N 이전의 절차를 업데이트
            for(int i = 0; i < regPrcdList.size(); i++){
                if(regPrcdList.get(i).getStatus().equals(PrcdStatus.REJECT.getStatus())){
                    index = i - 1;
                    break;
                }
            }
        }
        // 3. 모두 N이라면 0번 index를 update
        VocRegPrcdVo vo = regPrcdList.get(index);
        vo.setStatus(requestStatus.getStatus());
        dao.updateRegPrcd(vo);

        // STATUS 업데이트 이후 등록 건 STATUS를 변경한다.
        updateRegistrationStatus(Utilities.beanToMap(vo), requestStatus);
    }

    public void updateRegistrationStatus(Map<String, Object> param, PrcdStatus requestStatus){
        VocRegPrcdVo regPrcd = dao.selectRegPrcd(param);
        VocProcedureVo prcd = dao.selectProcedure(regPrcd);

        Map<String, Object> status = dao.selectVocStatus(prcd.getPrcdSeq(), requestStatus.getStatus());
        status.put("regSeq", param.get("regSeq"));
        dao.updateRegistrationStatus(status);

        // 완료(Y)인 경우 다음 절차의 대기(N) 상태로 업데이트 진행
        if(requestStatus.getStatus().equals(PrcdStatus.COMPLETE.getStatus())){
            param.remove("mcPrcdSeq");
            param.put("prntsSeq", prcd.getMcPrcdSeq());
            VocProcedureVo nextPrcd = dao.selectProcedure(param);

            Map<String, Object> nextStatus = dao.selectVocStatus(nextPrcd.getPrcdSeq(), PrcdStatus.STNDBY.getStatus());
            // 다음 상태가 존재하지 않는 경우 == 완료 -> 종결 처리(C)
            if(nextStatus == null){
                nextStatus = dao.selectVocStatus(null, PrcdStatus.CLOSE.getStatus());
            }
            nextStatus.put("regSeq", param.get("regSeq"));
            dao.updateRegistrationStatus(nextStatus);
        }
    }

    public <T> T selectManagementCode(Object param){
        return dao.selectManagementCode(param);
    }

    public <T> T selectPrcdBas(Object param){
        return dao.selectPrcdBas(param);
    }

    public <T> List<T> selectTaskBasList(Object param){
        return dao.selectTaskBasList(param);
    }

    public <T> List<T> selectPrcdBasList(Object param){
        return dao.selectPrcdBasList(param);
    }

    public <T> List<T> selectActivityFuncBasList(Object param){
        return dao.selectActivityFuncBasList(param);
    }

    public <T> T selectProcedure(Object param){
        return dao.selectProcedure(param);
    };

    public <T> List<T> selectProcedureList(Object param){
        return dao.selectProcedureList(param);
    }

    public <T> List<T> selectTaskList(Object param){
        return dao.selectTaskList(param);
    }

    public <T> List<T> selectActivityList(Object param){
        return dao.selectActivityList(param);
    }

    public String selectDirCd(Object param){
        return dao.selectDirCd(param);
    }

    public List<EzMap> selectDutyOrgList(Object param){
        return dao.selectDutyOrgList(param);
    }

}
