package com.kspo.voc.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.CrmOrgBaseVo;
import com.kspo.voc.sys.service.CrmOrgBaseService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = { "organization", "{menuId}/organization" })
public class CrmOrgBaseController{

@Autowired
CrmOrgBaseService service;

@GetMapping(value = { "", "index" })
public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
    model.addAllAttributes(param);
    return Utilities.getProperty("tiles.crm") + "sys/orgList";
}

@GetMapping(value = { "popup" })
public String popup(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
    model.addAllAttributes(param);
    return Utilities.getProperty("tiles.crm.blank") + "sys/orgPopup";
}

@PostMapping(value = { "getList" })
public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
    EzPaginationInfo page = param.getPaginationInfo();
    List<EzMap> list = service.getList(param);
    page.setTotalRecordCount(service.getListCount(param));
    return Utilities.getGridData(list,page);
}

@PostMapping(value = { "getTreeList" })
public @ResponseBody Object getTreeList(@RequestBody EzMap param) throws Exception {
	return service.getTreeList(param);
}

@GetMapping(value = { "sync" })
public @ResponseBody Object sync(@RequestParam Map<String, Object> rparam) throws Exception {
//    service.saveSyncOrg();
    return true;
}


@GetMapping(value = { "get" })
public @ResponseBody Object get(@RequestParam Map<String, Object> rparam) throws Exception {
    EzMap param = new EzMap(rparam);
    return service.get(param);
}
@PostMapping(value = {"save" })
public @ResponseBody Object save(@RequestBody CrmOrgBaseVo vo) throws Exception {
    return service.save(vo);
}

@PostMapping(value = { "saveList" })
public @ResponseBody Object saveList(@RequestBody List<CrmOrgBaseVo> list) throws Exception {
    return service.saveList(list);
}
@PostMapping(value = { "deleteList" })
public @ResponseBody Object deleteList(@RequestBody List<CrmOrgBaseVo> list) throws Exception {
    return service.deleteList(list);
}
}
