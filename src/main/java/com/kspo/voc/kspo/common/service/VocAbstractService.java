package com.kspo.voc.kspo.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.ITreeVo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.kspo.common.dao.IVocPrcDao;
import com.kspo.voc.kspo.common.stnd.ManageCodeCategory;
import com.kspo.voc.kspo.common.stnd.PrcdCategory;
import com.kspo.voc.kspo.common.stnd.PrcdStatus;
import com.kspo.voc.kspo.process.model.VocRegPrcdVo;
import com.kspo.voc.kspo.setting.model.VocProcedureVo;
import com.kspo.voc.sys.service.AbstractCrmService;

public abstract class VocAbstractService extends AbstractCrmService {

    @Autowired
    IVocPrcDao dao;

    public List<? extends ITreeVo> selectVocManagementCodeTree(Map<String, Object> param){
        return dao.selectVocManagementCodeTree(param);
    }

    /**
     * 등록절차 중 완료 상태표기를 다음 절차 대기상태로 변경
     *  - 다음 절차가 없다면 종결처리
     * @param param : reqSeq
     * @return updateResult
     */
    public int updateY2N(Map<String, Object> param){
        VocRegPrcdVo vo = selectNextRegPrcd(param);

        Map<String, Object> map = new HashMap<>();
        map.put("prntsSeq", vo.getMcPrcdSeq());
        VocProcedureVo nextPrcd = selectProcedure(map);

        Map<String, Object> nextStatus = selectVocStatus(nextPrcd.getPrcdSeq(), PrcdStatus.STNDBY.getStatus());
        // 다음 상태가 존재하지 않는 경우 == 완료 -> 종결 처리(C)
        if(nextStatus == null){
            nextStatus = selectVocStatus(null, PrcdStatus.CLOSE.getStatus());
        }
        nextStatus.put("regSeq", param.get("regSeq"));

        return dao.updateRegistrationStatus(nextStatus);
    }

    /**
     * 등록절차의 상태 업데이트
     *  - 등록 voc 건의 절차를 조회한 뒤, 규칙을 통해 update할 절차를 선택 및 업데이트
     * @param param : require REG_SEQ
     * @param requestStatus : PrcdStatus Enum에서 status를 선택.(string)
     * @return : insert result(integer)
     */
//    @SuppressWarnings("unchecked")
    public void updateRegProcedureStatus(Map<String, Object> param, PrcdStatus requestStatus) throws Exception{
        String regSeq = (String) param.get("regSeq");
        if(regSeq == null){
            throw new Exception("*** parameter에 [regSeq]가 존재하지 않습니다. ***");
        }

        VocRegPrcdVo vo = selectNextRegPrcd(param);
        vo.setStatus(requestStatus.getStatus());
        dao.updateRegPrcd(vo);

        // STATUS 업데이트 이후 등록 건 STATUS를 변경한다.
        updateRegistrationStatus(Utilities.beanToMap(vo), requestStatus);
    }

    /**
     * 등록 voc의 status를 변경
     * @param param : VocRegPrcdVo를 map 변경한 object
     * @param requestStatus : PrcdStatus 타입
     */
    public void updateRegistrationStatus(Map<String, Object> param, PrcdStatus requestStatus){
        VocRegPrcdVo regPrcd = dao.selectRegPrcd(param);
        VocProcedureVo prcd = dao.selectProcedure(regPrcd);

        Map<String, Object> status = dao.selectVocStatus(prcd.getPrcdSeq(), requestStatus.getStatus());
        status.put("regSeq", param.get("regSeq"));
        dao.updateRegistrationStatus(status);
    }

    /**
     * 등록 시퀀스를 통해 다음 등록절차를 조회
     * @param param : reqSeq(등록시퀀스) 필수 포함
     * @return VocRegPrcdVo - 등록절차 정보
     */
    public VocRegPrcdVo selectNextRegPrcd(Object param){
        List<VocRegPrcdVo> regPrcdList = dao.selectRegPrcdList(param);

        int cntN = 0;
        int index = 0;
        for(VocRegPrcdVo regPrcd : regPrcdList){
            if(regPrcd.getStatus().equals(PrcdStatus.STNDBY.getStatus())){
                cntN++;
            }
        }

        if(cntN == 0) {
            // 1. N이 존재하지 않는다면, Y가 아닌 절차 -> list에서 status = 'Y' 제거
            regPrcdList.removeIf(value -> value.getStatus().equals(PrcdStatus.COMPLETE.getStatus()));
        } else if(cntN != regPrcdList.size()){
            // 2. 모두 N이 아니고 N이 존재한다면, 가장 공통코드 우선순위가 낮은 N 이전의 절차
            for(int i = 0; i < regPrcdList.size(); i++){
                if(regPrcdList.get(i).getStatus().equals(PrcdStatus.REJECT.getStatus())){
                    index = i - 1;
                    break;
                }
            }
        }
        // 3. 모두 N이라면 0번 index
        VocRegPrcdVo vo = regPrcdList.get(index);
        return vo;
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

    public Object getManagementCodeSelect(Map<String, Object> param, ManageCodeCategory ctgr) {
        param.put("comnCd", ctgr.getComnCd());
        return dao.getManagementCodeSelect(param);
    }

    public Map<String, Object> selectVocStatus(String prcdSeq, String status){
        return dao.selectVocStatus(prcdSeq, status);
    }

    /**
     * 절차와 단건의 상태 파라미터를 통해서 1건의 status 조회 후 parameter에 put
     * @param param : hashMap
     * @param prcdCategory : eNum - 절차 분류
     * @param prcdStatus : PrcdStatus
     */
    public void setStatusCd(Map<String, Object> param, PrcdCategory prcdCategory, PrcdStatus prcdStatus){
        param.put("topComnCd", prcdCategory.getTopComnCd());
        param.put("comnCd", prcdCategory.getComnCd());

        param.put("status", ((PrcdStatus) prcdStatus).getStatus());
        Map<String, Object> status = dao.selectStatusCd(param);

        param.put("statusCd", status.get("STATUS_CD"));
    }

    /**
     * 절차와 여러건의 상태 파라미터를 통해서 N건의 status 조회 후 parameter에 put
     * @param param : hashMap
     * @param prcdCategory : eNum - 절차 분류
     * @param prcdStatus : List<PrcdStatus>
     */
    public void setStatusCd(Map<String, Object> param, PrcdCategory prcdCategory, List<PrcdStatus> prcdStatus){
        List<String> statusCdList = new ArrayList<>();
        for(PrcdStatus reqStatus : prcdStatus){
            Map<String, Object> map = new HashMap<>();
            map.put("topComnCd", prcdCategory.getTopComnCd());
            map.put("comnCd", prcdCategory.getComnCd());
            map.put("status", reqStatus.getStatus());

            Map<String, Object> status = dao.selectStatusCd(map);
            statusCdList.add((String) status.get("STATUS_CD"));
        }

        param.put("statusCd", statusCdList);
    }

    public <T> List<T> selectStatusList(Object param){
        return dao.selectStatusList(param);
    }





}
