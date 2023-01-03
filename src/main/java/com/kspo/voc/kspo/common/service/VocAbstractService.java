package com.kspo.voc.kspo.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.ITreeVo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.kspo.common.dao.IVocPrcDao;
import com.kspo.voc.kspo.common.stnd.ManageCodeCategory;
import com.kspo.voc.kspo.common.stnd.PrcdCategory;
import com.kspo.voc.kspo.common.stnd.PrcdStatus;
import com.kspo.voc.sys.service.AbstractVocService;

public abstract class VocAbstractService extends AbstractVocService {

    @Autowired
    IVocPrcDao dao;

    public List<? extends ITreeVo> selectVocMgmtCdTree(Map<String, Object> param){
        return dao.selectVocMgmtCdTree(param);
    }

    public <T> T selectMgmtCd(Object param){
        return dao.selectMgmtCd(param);
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

    public Object getMgmtCdSelect(Map<String, Object> param, ManageCodeCategory ctgr) {
        param.put("comnCd", ctgr.getComnCd());
        return dao.getMgmtCdSelect(param);
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
