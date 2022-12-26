package com.kspo.voc.sys.controller;


import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.service.GenGridService;

@Controller
@RequestMapping(value = { "genGrid","{menuId}/genGrid" })
public class GenGridController {
	@Autowired
	GenGridService service;
	
	@GetMapping(value = { "" })
	public  Object index(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException{
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "sys/genGrid";
	}
	
	@GetMapping(value = { "text" })
	public  Object text(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException{
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/genGridPop";
	}
	
	@PostMapping(value = { "selectColInfo" })
	public @ResponseBody Object colInfo(@RequestBody EzMap param) throws EgovBizException {
		return service.getList(param);
	} 
	@PostMapping(value = { "getCodeList" })
	public @ResponseBody Object getCodeList(@RequestBody EzMap param) throws Exception {
		return service.getCodeList(param);
	}
}
