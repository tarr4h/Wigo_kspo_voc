package com.kspo.voc.kspo.setting.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.kspo.common.service.VocAbstractService;
import com.kspo.voc.kspo.common.stnd.CodeGeneration;
import com.kspo.voc.kspo.setting.dao.VocMgmtCdMappDao;
import com.kspo.voc.kspo.setting.model.VocMgmtCdVo;
import com.kspo.voc.kspo.setting.model.VocMgmtCdMappVo;
import com.kspo.voc.sys.dao.IVocDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VocMgmtCdMappService extends VocAbstractService {

    @Autowired
    VocMgmtCdMappDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public Object vocMgmtCdTree(EzMap param) {
        return AbstractTreeVo.makeHierarchy(selectVocMgmtCdTree(param));
    }

    public Object vocMgmtCdMappTree(EzMap param) {
        return AbstractTreeVo.makeHierarchy(dao.selectList(param));
    }

    public Object insert(Map<String, Object> param) {
        Map<String, Object> returnMap = new HashMap<>();

        String maxCd = dao.selectMaxCd();
        String mappCd = CodeGeneration.generateCode(maxCd, CodeGeneration.PROCEDURE_MAPPING);
        param.put("mappCd", mappCd);


        if(param.get("prntsMappCd") != null){
            // validateMEthod 실행
            returnMap = validateInsert(param);
            log.debug("validateResult = {}", returnMap);
            if(!(boolean) returnMap.get("result")){
                return returnMap;
            } else {
                param.putAll(returnMap);
            }
        } else {
            param.put("mappLvlNo", 1);
        }
        dao.insert(param);
        returnMap.put("msg", "등록되었습니다.");
        returnMap.put("result", true);
        return returnMap;
    }

    public Object delete(Map<String, Object> param){
        VocMgmtCdMappVo topVo = dao.select(param);
        List<VocMgmtCdMappVo> childList = findChildList(param);
        childList.add(0, topVo);

        param.put("list", childList);
        return dao.delete(param);
    }

    public List<VocMgmtCdMappVo> findChildList(Object param){
        List<VocMgmtCdMappVo> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        if(param instanceof VocMgmtCdMappVo){
            map.put("prntsMappingSeq", ((VocMgmtCdMappVo) param).getMappCd());
        } else if(param instanceof HashMap){
            map.put("prntsMappingSeq", ((HashMap<?, ?>) param).get("mappingSeq"));
        }

        List<VocMgmtCdMappVo> voList = dao.selectList(map);

        if(!voList.isEmpty()){
            list.addAll(voList);
            for(VocMgmtCdMappVo vo : voList){
                list.addAll(findChildList(vo));
            }
        }

        return list;
    }

    public Map<String, Object> validateInsert(Map<String, Object> param){
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("result", false);

        VocMgmtCdVo reqVo = selectMgmtCd(param);
        String grp = reqVo.getTopMgmtCd();

        // 바로 위 부모코드 조회
        Map<String, Object> map = new HashMap<>();
        map.put("mappCd", param.get("prntsMappCd"));

        VocMgmtCdMappVo prntsVo = dao.select(map);

//      현재 요소의 부모코드가 최상단코드가 아니면서 상위 mapping에 등록되어 있지 않은 경우
        if(!reqVo.getTopMgmtCd().equals(reqVo.getPrntsMgmtCd()) && !reqVo.getPrntsMgmtCd().equals(prntsVo.getMgmtCd())) {
            returnMap.put("msg", "해당 코드의 상위코드가 등록되지 않았습니다.\n상위 코드를 먼저 등록해주세요.");
            return returnMap;
        }

        // 상위 부모코드 목록 조회
        List<VocMgmtCdMappVo> list = findPrntsList(prntsVo);
        list.add(prntsVo);

        // prnts가 무조건 존재함 == size가 최소 1
        for(int i = 0; i < list.size(); i++){
            VocMgmtCdVo prcd = selectMgmtCd(list.get(i));
            if(list.get(i).getMgmtCd().equals(reqVo.getMgmtCd())){
                returnMap.put("msg", "중복 코드입니다.");
                return returnMap;
            } else if(prcd.getTopMgmtCd().equals(grp) && (!prntsVo.getTopMgmtCd().equals(grp) || reqVo.getMgmtCdLvlNo() <= prcd.getMgmtCdLvlNo())){
                returnMap.put("msg", "같은 분류의 코드가 부모레벨 이상에 존재하여 등록 불가합니다.");
                return returnMap;
            }
        }

        // 부모코드의 자식요소 조회(siblings)
        Map<String, Object> sbMap = new HashMap<>();
        sbMap.put("prntsMappCd", prntsVo.getMappCd());
        List<VocMgmtCdMappVo> siblings = dao.selectList(sbMap);

        for(VocMgmtCdMappVo vo : siblings){
            if(vo.getMgmtCd().equals(reqVo.getMgmtCd())){
                returnMap.put("msg", "같은 레벨에 동일한 코드가 존재합니다.");
                return returnMap;
            }
        }

        returnMap.put("result", true);
        returnMap.put("mappLvlNo", prntsVo.getMappLvlNo() + 1);
        returnMap.put("prntsMappCd", prntsVo.getMappCd());
        return returnMap;
    }

    public List<VocMgmtCdMappVo> findPrntsList(VocMgmtCdMappVo vo){
        List<VocMgmtCdMappVo> list = new ArrayList<>();
        if(vo.getPrntsMappCd() == null){
            return list;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("mappCd", vo.getPrntsMappCd());

        VocMgmtCdMappVo prnts = dao.select(param);
        if(prnts != null){
            list.add(prnts);
            list.addAll(findPrntsList(prnts));
        }

        return list;
    }





}
