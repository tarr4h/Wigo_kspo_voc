package com.egov.voc.kspo.common.util;


import com.egov.voc.base.common.model.AbstractTreeVo;
import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.EzPaginationInfo;
import com.egov.voc.base.common.model.ITreeVo;
import com.egov.voc.base.common.util.BaseUtilities;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.corp.service.VocCorporationService;
import com.egov.voc.sys.model.CrmEmpBaseVo;
import com.egov.voc.sys.model.CrmOrgBaseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Component
@Slf4j
public class VocUtils extends BaseUtilities {

    private static VocCorporationService corpService;

    @Autowired
    VocCorporationService cpService;


    public static int parseIntObject(Object obj){
        int returnObj = Integer.parseInt(String.valueOf(obj));
        return returnObj;
    }


    @PostConstruct
    public void init() throws Exception{
        super.init();
        corpService = this.cpService;
    }

    public static EzMap getEmpGrid(EzMap param) {
        EzPaginationInfo page = param.getPaginationInfo();
        List<CrmEmpBaseVo> list = corpService.selectEmpList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    public static Object getOrgTree(Map<String, Object> param){
        return AbstractTreeVo.makeHierarchy((List<? extends ITreeVo>) corpService.selectOrgList(param));
    }

    public static EzMap getOrgGrid(EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<CrmOrgBaseVo> list = corpService.selectOrgList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    public static List<CrmOrgBaseVo> selectOrgList(Map<String, Object> param){
        return corpService.selectOrgList(param);
    }

    public static Map<String, Object> formSerializeArrayToMap(List<Map<String, Object>> list){
        Map<String ,Object> returnMap = new HashMap<>();

        for(Map<String ,Object> map : list){
            log.debug("map = {}", map);
            String key = (String) map.get("name");
            Object value = map.get("value");
            returnMap.put(key, value);
        }

        log.debug("returnMap = {}", returnMap);

        return returnMap;
    }

    public static Object selectComnCdList(Map<String, Object> param){
        return corpService.selectComnCdList(param);
    }
}
