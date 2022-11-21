package com.egov.voc.kspo.setting.service;

import com.egov.voc.base.common.model.AbstractTreeVo;
import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.kspo.common.stnd.CodeGeneration;
import com.egov.voc.kspo.setting.dao.VocProcedureMappingDao;
import com.egov.voc.kspo.setting.model.VocManagementCodeVo;
import com.egov.voc.kspo.setting.model.VocProcedureMappingVo;
import com.egov.voc.kspo.setting.model.VocProcedureVo;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.service.AbstractCrmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VocProcedureMappingService extends AbstractCrmService {

    @Autowired
    VocProcedureMappingDao dao;

    @Override
    public ICrmDao getDao() {
        return dao;
    }

    public Object vocManagementCodeTree(EzMap param) {
        return AbstractTreeVo.makeHierarchy(dao.vocManagementCodeTree(param));
    }

    public Object vocProcedureMappingTree(EzMap param) {
        return AbstractTreeVo.makeHierarchy(dao.selectList(param));
    }

    public Object insert(Map<String, Object> param) {
        Map<String, Object> returnMap = new HashMap<>();

        String maxCd = dao.selectMaxCd();
        String mappingSeq = CodeGeneration.generateCode(maxCd, CodeGeneration.PROCEDURE_MAPPING);
        param.put("mappingSeq", mappingSeq);


        if(param.get("prntsCd") != null){
            // validateMEthod 실행
            returnMap = validateInsert(param);
            log.debug("validateResult = {}", returnMap);
            if(!(boolean) returnMap.get("result")){
                return returnMap;
            } else {
                param.putAll(returnMap);
            }
        } else {
            param.put("lvl", 1);
        }
        dao.insert(param);
        returnMap.put("msg", "등록되었습니다.");
        returnMap.put("result", true);
        return returnMap;
    }

    public Object delete(Map<String, Object> param){
        VocProcedureMappingVo topVo = dao.select(param);
        List<VocProcedureMappingVo> childList = findChildList(param);
        childList.add(0, topVo);

        param.put("list", childList);
        return dao.delete(param);
    }

    public List<VocProcedureMappingVo> findChildList(Object param){
        List<VocProcedureMappingVo> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        if(param instanceof VocProcedureMappingVo){
            map.put("prntsMappingSeq", ((VocProcedureMappingVo) param).getMappingSeq());
        } else if(param instanceof HashMap){
            map.put("prntsMappingSeq", ((HashMap<?, ?>) param).get("mappingSeq"));
        }

        List<VocProcedureMappingVo> voList = dao.selectList(map);

        if(!voList.isEmpty()){
            list.addAll(voList);
            for(VocProcedureMappingVo vo : voList){
                list.addAll(findChildList(vo));
            }
        }

        return list;
    }

    public Map<String, Object> validateInsert(Map<String, Object> param){
        Map<String, Object> returnMap = new HashMap<>();

        VocManagementCodeVo reqVo = dao.selectManagementCode(param);
        String grp = reqVo.getTopCd();
        log.debug("grp = {}", grp);

        // 바로 위 부모코드 조회
        Map<String, Object> map = new HashMap<>();
        map.put("mappingSeq", param.get("prntsCd"));
        log.debug("prntsCd = {}", param.get("prntsCd"));

        VocProcedureMappingVo prntsVo = dao.select(map);

        // 상위 부모코드 목록 조회
        List<VocProcedureMappingVo> list = findPrntsList(prntsVo);
        list.add(prntsVo);
        log.debug("prntsListSize = {}", list.size());

        // prnts가 무조건 존재함 == size가 최소 1
        for(int i = 0; i < list.size(); i++){
            VocManagementCodeVo prcd = dao.selectManagementCode(list.get(i));
            log.debug("prcd topCd = {}", prcd.getTopCd());
            log.debug("prntsVo topCd = {}", prntsVo.getTopCd());
            log.debug("lvl > reqVo, prntsPrcd = {}, {}", reqVo.getLvl(), prcd.getLvl());
            if(list.get(i).getManagementCd().equals(reqVo.getManagementCd())){
                returnMap.put("msg", "중복 코드입니다.");
                returnMap.put("result", false);
                return returnMap;
            } else if(prcd.getTopCd().equals(grp) && (!prntsVo.getTopCd().equals(grp) || reqVo.getLvl() <= prcd.getLvl())){
                returnMap.put("msg", "같은 분류의 코드가 부모레벨 이상에 존재하여 등록 불가합니다.");
                returnMap.put("result", false);
                return returnMap;
            } else if((reqVo.getTopCd().equals(prntsVo.getTopCd()) && !reqVo.getTopCd().equals(reqVo.getManagementCd()) && !reqVo.getPrntsCd().equals(prntsVo.getManagementCd()))
                || (!reqVo.getTopCd().equals(prntsVo.getTopCd()) && !reqVo.getTopCd().equals(reqVo.getManagementCd())))
            {
//                // 현재 요소(reqVo)가 prntsCd가 존재하는데, 부모요소가 없다면 등록불가
//                returnMap.put("msg", "순서에 맞게 등록해주세요.\n상위 코드가 존재합니다.");
//                returnMap.put("result", false);
//                return returnMap;
            }
        }


        returnMap.put("result", true);
        returnMap.put("lvl", prntsVo.getLvl() + 1);
        returnMap.put("prntsMappingSeq", prntsVo.getMappingSeq());
        return returnMap;
    }

    public List<VocProcedureMappingVo> findPrntsList(VocProcedureMappingVo vo){
        List<VocProcedureMappingVo> list = new ArrayList<>();
        if(vo.getPrntsMappingSeq() == null){
            return list;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("mappingSeq", vo.getPrntsMappingSeq());

        VocProcedureMappingVo prnts = dao.select(param);
        if(prnts != null){
            list.add(prnts);
            list.addAll(findPrntsList(prnts));
        }

        return list;
    }





}
