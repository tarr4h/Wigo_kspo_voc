package com.egov.voc.sys.controller;


import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.service.CrmGenGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = { "genGrid","{menuCd}/genGrid" })
public class CrmGenGridController {
	@Autowired
	CrmGenGridService service;
	
	@GetMapping(value = { "" })
	public  Object index(@RequestParam Map<String, Object> param, ModelMap model) throws Exception{
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm") + "sys/genGrid";
	}
	
	@GetMapping(value = { "text" })
	public  Object text(@RequestParam Map<String, Object> param, ModelMap model) throws Exception{
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/genGridPop";
	}
	
	@PostMapping(value = { "selectColInfo" })
	public @ResponseBody Object colInfo(@RequestBody EzMap param) throws Exception {
		return service.getList(param);
	} 
}
