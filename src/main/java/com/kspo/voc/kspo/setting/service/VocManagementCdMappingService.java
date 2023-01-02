package com.kspo.voc.kspo.setting.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.kspo.common.service.VocAbstractService;
import com.kspo.voc.kspo.common.stnd.CodeGeneration;
import com.kspo.voc.kspo.setting.dao.VocManagementCdMappingDao;
import com.kspo.voc.kspo.setting.model.VocManagementCodeVo;
import com.kspo.voc.kspo.setting.model.VocManagementCdMappingVo;
import com.kspo.voc.sys.dao.IVocDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VocManagementCdMappingService extends VocAbstractService {

    @Autowired
    VocManagementCdMappingDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public Object vocManagementCodeTree(EzMap param) {
        return AbstractTreeVo.makeHierarchy(selectVocManagementCodeTree(param));
    }

    public Object vocManagementCdMappingTree(EzMap param) {
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
        VocManagementCdMappingVo topVo = dao.select(param);
        List<VocManagementCdMappingVo> childList = findChildList(param);
        childList.add(0, topVo);

        param.put("list", childList);
        return dao.delete(param);
    }

    public List<VocManagementCdMappingVo> findChildList(Object param){
        List<VocManagementCdMappingVo> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        if(param instanceof VocManagementCdMappingVo){
            map.put("prntsMappingSeq", ((VocManagementCdMappingVo) param).getMappingSeq());
        } else if(param instanceof HashMap){
            map.put("prntsMappingSeq", ((HashMap<?, ?>) param).get("mappingSeq"));
        }

        List<VocManagementCdMappingVo> voList = dao.selectList(map);

        if(!voList.isEmpty()){
            list.addAll(voList);
            for(VocManagementCdMappingVo vo : voList){
                list.addAll(findChildList(vo));
            }
        }

        return list;
    }

    public Map<String, Object> validateInsert(Map<String, Object> param){
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("result", false);

        VocManagementCodeVo reqVo = selectManagementCode(param);
        String grp = reqVo.getTopCd();

        // 바로 위 부모코드 조회
        Map<String, Object> map = new HashMap<>();
        map.put("mappingSeq", param.get("prntsCd"));

        VocManagementCdMappingVo prntsVo = dao.select(map);

//      현재 요소의 부모코드가 최상단코드가 아니면서 상위 mapping에 등록되어 있지 않은 경우
        if(!reqVo.getTopCd().equals(reqVo.getPrntsCd()) && !reqVo.getPrntsCd().equals(prntsVo.getManagementCd())) {
            returnMap.put("msg", "해당 코드의 상위코드가 등록되지 않았습니다.\n상위 코드를 먼저 등록해주세요.");
            return returnMap;
        }

        // 상위 부모코드 목록 조회
        List<VocManagementCdMappingVo> list = findPrntsList(prntsVo);
        list.add(prntsVo);

        // prnts가 무조건 존재함 == size가 최소 1
        for(int i = 0; i < list.size(); i++){
            VocManagementCodeVo prcd = selectManagementCode(list.get(i));
            if(list.get(i).getManagementCd().equals(reqVo.getManagementCd())){
                returnMap.put("msg", "중복 코드입니다.");
                return returnMap;
            } else if(prcd.getTopCd().equals(grp) && (!prntsVo.getTopCd().equals(grp) || reqVo.getLvl() <= prcd.getLvl())){
                returnMap.put("msg", "같은 분류의 코드가 부모레벨 이상에 존재하여 등록 불가합니다.");
                return returnMap;
            }
        }

        // 부모코드의 자식요소 조회(siblings)
        Map<String, Object> sbMap = new HashMap<>();
        sbMap.put("prntsMappingSeq", prntsVo.getMappingSeq());
        List<VocManagementCdMappingVo> siblings = dao.selectList(sbMap);

        for(VocManagementCdMappingVo vo : siblings){
            if(vo.getManagementCd().equals(reqVo.getManagementCd())){
                returnMap.put("msg", "같은 레벨에 동일한 코드가 존재합니다.");
                return returnMap;
            }
        }

        returnMap.put("result", true);
        returnMap.put("lvl", prntsVo.getLvl() + 1);
        returnMap.put("prntsMappingSeq", prntsVo.getMappingSeq());
        return returnMap;
    }

    public List<VocManagementCdMappingVo> findPrntsList(VocManagementCdMappingVo vo){
        List<VocManagementCdMappingVo> list = new ArrayList<>();
        if(vo.getPrntsMappingSeq() == null){
            return list;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("mappingSeq", vo.getPrntsMappingSeq());

        VocManagementCdMappingVo prnts = dao.select(param);
        if(prnts != null){
            list.add(prnts);
            list.addAll(findPrntsList(prnts));
        }

        return list;
    }





}
