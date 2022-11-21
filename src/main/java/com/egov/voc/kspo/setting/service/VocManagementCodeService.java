package com.egov.voc.kspo.setting.service;


import com.egov.voc.base.common.model.AbstractTreeVo;
import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.setting.dao.VocManagementCodeDao;
import com.egov.voc.kspo.setting.model.VocManagementCodeVo;
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
public class VocManagementCodeService extends AbstractCrmService {

    @Autowired
    VocManagementCodeDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public Object vocManagementCodeTree(EzMap param) {
        return AbstractTreeVo.makeHierarchy(dao.vocManagementCodeTree(param));
    }

    public <T> List<T> vocManagementCodeList(Map<String, Object> param) {
        return dao.selectList(param);
    }

    public Object vocManagementCode(EzMap param) {
        return dao.select(param);
    }

    public Object insert(EzMap param) {
        log.debug("param = {}", param);
        Map<String, Object> returnMap = new HashMap<>();
        String msg = null;
        boolean result = false;

        if(param.get("codeNm") == null){
            msg = "코드명을 입력해주세요.";
        } else {
            if(param.get("prntsCd") != null){
                param = generateInsertCodeParam(param);
            } else {
                param = generateInsertGroupParam(param);
            }
            int insertResult = dao.insert(param);

            if(insertResult > 0){
                msg = "등록되었습니다.";
                result = true;
            } else {
//                 exception Throw
            }
        }

        returnMap.put("msg", msg);
        returnMap.put("result", result);
        return returnMap;
    }

    public Object update(EzMap param) {
        return dao.update(param);
    }

    public Object delete(List<VocManagementCodeVo> list) {
        return dao.delete(list);
    }

    public EzMap generateInsertGroupParam(EzMap param){
        StringBuilder managementCd = new StringBuilder();
        managementCd.append("MC");

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
            managementCd.append("0").append(addNum);
        } else {
            managementCd.append(addNum);
        };

        for(int i = 0; i < 13; i++){
            managementCd.append("0");
        }

        log.debug("management grp cd = {}", managementCd);

        param.put("managementCd", managementCd.toString());
//        param.put("topCd", managementCd.toString());
        param.put("odrg", addNum);
        param.put("lvl", 1);
        return param;
    }

    public EzMap generateInsertCodeParam(EzMap param){
        // 17자리(코드값 2 + 분류값15(2/3/3/3/4))
        VocManagementCodeVo prntsRow = dao.select(param);
        log.debug("prntsRow = {}", prntsRow.getCodeNm());

        int childrenSize = dao.selectList(param).size();
        String maxChildCd = dao.maxChildCd(param);

        param.put("lvl", prntsRow.getLvl() + 1);
        param.put("regUsr", Utilities.getLoginUserCd());

        String prntsCd = prntsRow.getManagementCd();
        String prntsCdGrp = prntsCd.substring(2, 4);
        String prntsCdNum = prntsCd.substring(4); // grp인 경우엔 2

        // prefix
        StringBuilder managementCd = new StringBuilder();
        managementCd.append("MC").append(prntsCdGrp);

        // 부모코드 자리
        int prntsLvl = prntsRow.getLvl() - 1;
        if(prntsLvl != 0){
            String subStrCd = prntsCdNum.substring(0, prntsLvl * 3);
            managementCd.append(subStrCd);
        }

        log.debug("prntsLvl = {}", prntsLvl);
        log.debug("managementCd 1 = {}", managementCd);

        log.debug("maxChildCd = {}", maxChildCd);
        log.debug("childrenSize = {}", childrenSize);
        // 현재 코드의 실제 적재 자리
        int inputVal = 0;
        if(maxChildCd == null) {
            param.put("odrg", childrenSize + 1);
            inputVal = childrenSize + 1;
        } else {
            int endSpot = prntsLvl < 3 ? 4 : 5;
            String siblingCd = maxChildCd.substring(4 + (prntsLvl * 3), endSpot + (prntsLvl + 1) * 3);
            log.debug("siblingCd = {}", siblingCd);
            inputVal = VocUtils.parseIntObject(siblingCd) + 1;
            param.put("odrg", inputVal);
        }

        log.debug("inputVal = {}", inputVal);

        if(inputVal < 10){
            managementCd.append("000");
        } else if (inputVal < 100){
            managementCd.append("00");
        } else if (inputVal < 1000){
            managementCd.append("0");
        }
        if(prntsLvl + 1 < 4){
            managementCd.deleteCharAt(managementCd.length() - 1);
        }
        managementCd.append(inputVal);

        int leftSpace = 17 - 4 - ((prntsLvl + 1) * 3);
        leftSpace = prntsLvl == 3 ? leftSpace - 1 : leftSpace;
        for(int i = 0; i < leftSpace; i++){
            managementCd.append("0");
        }

        log.debug("managementCd = {}", managementCd);

        String topCd = prntsRow.getTopCd() == null ? managementCd.toString() : prntsRow.getTopCd();
        log.debug("topCd = {}", topCd);
        param.put("topCd", topCd);

        param.put("managementCd", managementCd.toString());
        return param;
    }


}
