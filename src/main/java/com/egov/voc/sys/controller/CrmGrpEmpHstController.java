package com.egov.voc.sys.controller;

import com.egov.base.common.model.EzMap;
import com.egov.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.CrmGrpEmpHstVo;
import com.egov.voc.sys.service.CrmGrpEmpHstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = { "grpEmpHst", "{menuCd}/grpEmpHst" })
public class CrmGrpEmpHstController{

@Autowired
CrmGrpEmpHstService service;

@GetMapping(value = { "", "index" })
public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
    model.addAllAttributes(param);
    return Utilities.getProperty("tiles.crm") + "sys/grpEmpHstList";
}

@PostMapping(value = { "getList" })
public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
    EzPaginationInfo page = param.getPaginationInfo();
    List<EzMap> list = service.getList(param);
    page.setTotalRecordCount(service.getListCount(param));
    return Utilities.getGridData(list,page);
}

@GetMapping(value = { "get" })
public @ResponseBody Object get(@RequestParam Map<String, Object> rparam) throws Exception {
    EzMap param = new EzMap(rparam);
    return service.get(param);
}
@PostMapping(value = {"save" })
public @ResponseBody Object save(@RequestBody CrmGrpEmpHstVo vo) throws Exception {
    return service.save(vo);
}

@PostMapping(value = { "saveList" })
public @ResponseBody Object saveList(@RequestBody List<CrmGrpEmpHstVo> list) throws Exception {
    return service.saveList(list);
}
@PostMapping(value = { "deleteList" })
public @ResponseBody Object deleteList(@RequestBody List<CrmGrpEmpHstVo> list) throws Exception {
    return service.deleteList(list);
}
}
