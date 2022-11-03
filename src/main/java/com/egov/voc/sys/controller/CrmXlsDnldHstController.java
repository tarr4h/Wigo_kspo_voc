package com.egov.voc.sys.controller;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.CrmXlsDnldHstVo;
import com.egov.voc.sys.service.CrmXlsDnldHstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = { "excelLog", "{menuCd}/excelLog" })
public class CrmXlsDnldHstController{

@Autowired
CrmXlsDnldHstService service;

@GetMapping(value = { "", "index" })
public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
    model.addAllAttributes(param);
    return Utilities.getProperty("tiles.crm") + "sys/excelHstList";
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
public @ResponseBody Object save(@RequestBody CrmXlsDnldHstVo vo) throws Exception {
    return service.save(vo);
}

@PostMapping(value = {"addLog" })
public @ResponseBody Object addLog(@RequestBody CrmXlsDnldHstVo vo) throws Exception {
    service.addLog(vo);
    return true;
}


@PostMapping(value = { "saveList" })
public @ResponseBody Object saveList(@RequestBody List<CrmXlsDnldHstVo> list) throws Exception {
    return service.saveList(list);
}
@PostMapping(value = { "deleteList" })
public @ResponseBody Object deleteList(@RequestBody List<CrmXlsDnldHstVo> list) throws Exception {
    return service.deleteList(list);
}
}
