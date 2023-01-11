package com.kspo.voc.program.setting.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.program.common.service.VocAbstractService;
import com.kspo.voc.program.common.util.VocUtils;
import com.kspo.voc.program.setting.dao.VocMgmtCdDao;
import com.kspo.voc.program.setting.model.VocMgmtCdVo;
import com.kspo.voc.sys.dao.IVocDao;

import lombok.extern.slf4j.Slf4j;

//@SuppressWarnings("unchecked")
@Service
@Slf4j
public class VocMgmtCdService extends VocAbstractService {

    @Autowired
    VocMgmtCdDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public Object vocMgmtCdTree(Map<String, Object> param) {
        return AbstractTreeVo.makeHierarchy(selectVocMgmtCdTree(param));
    }

    public <T> List<T> vocMgmtCdList(Map<String, Object> param) {
        return dao.selectList(param);
    }

    public Object vocMgmtCd(EzMap param) {
        return selectMgmtCd(param);
    }

    public Object insert(EzMap param) {
        log.debug("param = {}", param);
        Map<String, Object> returnMap = new HashMap<>();
        String msg = null;
        boolean result = false;

        if(param.get("mgmtCdNm") == null){
            msg = "코드명을 입력해주세요.";
        } else {
            if(param.get("prntsMgmtCd") != null){
                generateInsertCodeParam(param);
            } else {
                generateInsertGroupParam(param);
            }
            int insertResult = dao.insert(param);

            if(insertResult > 0){
                msg = "등록되었습니다.";
                result = true;
            } else {
                msg = "등록된 건이 없습니다.";
            }
        }

        returnMap.put("msg", msg);
        returnMap.put("result", result);
        return returnMap;
    }

//    public Object update(EzMap param) {
//        return dao.update(param);
//    }
//
//    public Object delete(List<VocMgmtCdVo> list) {
//        return dao.delete(list);
//    }

    public void generateInsertGroupParam(EzMap param){
        StringBuilder mgmtCd = new StringBuilder();
        mgmtCd.append("MC");

        int groupSize = dao.selectList(param).size() + 1;
        int addNum = groupSize;

        String maxChildCd = dao.maxChildCd(param);
        if(maxChildCd != null){
            String subStrMaxChildValue = maxChildCd.substring(2, 4);
            int convertInteger = Integer.parseInt(subStrMaxChildValue);
            log.debug("convertInteger = {}", convertInteger);

            addNum = convertInteger + 1;
        }

        if(groupSize < 10){
            mgmtCd.append("0").append(addNum);
        } else {
            mgmtCd.append(addNum);
        }

        for(int i = 0; i < 13; i++){
            mgmtCd.append("0");
        }

        param.put("mgmtCd", mgmtCd.toString());
        param.put("topMgmtCd", mgmtCd.toString());
        param.put("mgmtCdOrdr", addNum);
        param.put("mgmtCdLvlNo", 1);
    }

    public void generateInsertCodeParam(EzMap param){
        // 17자리(코드값 2 + 분류값15(2/3/3/3/4))
        VocMgmtCdVo prntsRow = selectMgmtCd(param);
        log.debug("prntsRow = {}", prntsRow.getMgmtCdNm());

        int childrenSize = dao.selectList(param).size();
        String maxChildCd = dao.maxChildCd(param);

        param.put("mgmtCdLvlNo", prntsRow.getMgmtCdLvlNo() + 1);
        param.put("regrId", Utilities.getLoginId());

        String prntsCd = prntsRow.getMgmtCd();
        String prntsCdGrp = prntsCd.substring(2, 4);
        String prntsCdNum = prntsCd.substring(4);

        // prefix
        StringBuilder mgmtCd = new StringBuilder();
        mgmtCd.append("MC").append(prntsCdGrp);

        // 부모코드 자리
        int prntsLvl = prntsRow.getMgmtCdLvlNo() - 1;
        if(prntsLvl != 0){
            String subStrCd = prntsCdNum.substring(0, prntsLvl * 3);
            mgmtCd.append(subStrCd);
        }

        log.debug("prntsLvl = {}", prntsLvl);
        log.debug("mgmtCd 1 = {}", mgmtCd);

        log.debug("maxChildCd = {}", maxChildCd);
        log.debug("childrenSize = {}", childrenSize);
        // 현재 코드의 실제 적재 자리
        int inputVal = 0;
        if(maxChildCd == null) {
            param.put("mgmtCdOrdr", childrenSize + 1);
            inputVal = childrenSize + 1;
        } else {
            int endSpot = prntsLvl < 3 ? 4 : 5;
            String siblingCd = maxChildCd.substring(4 + (prntsLvl * 3), endSpot + (prntsLvl + 1) * 3);
            log.debug("siblingCd = {}", siblingCd);
            inputVal = VocUtils.parseIntObject(siblingCd) + 1;
            param.put("mgmtCdOrdr", inputVal);
        }

        log.debug("inputVal = {}", inputVal);

        if(inputVal < 10){
            mgmtCd.append("000");
        } else if (inputVal < 100){
            mgmtCd.append("00");
        } else if (inputVal < 1000){
            mgmtCd.append("0");
        }
        if(prntsLvl + 1 < 4){
            mgmtCd.deleteCharAt(mgmtCd.length() - 1);
        }
        mgmtCd.append(inputVal);

        int leftSpace = 17 - 4 - ((prntsLvl + 1) * 3);
        leftSpace = prntsLvl == 3 ? leftSpace - 1 : leftSpace;
        for(int i = 0; i < leftSpace; i++){
            mgmtCd.append("0");
        }

        log.debug("mgmtCd = {}", mgmtCd);

        String topMgmtCd = prntsRow.getTopMgmtCd() == null ? mgmtCd.toString() : prntsRow.getTopMgmtCd();
        log.debug("topCd = {}", topMgmtCd);
        param.put("topMgmtCd", topMgmtCd);

        param.put("mgmtCd", mgmtCd.toString());
    }


}
