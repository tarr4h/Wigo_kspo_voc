package com.kspo.voc.sys.controller;


import java.util.List;
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
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.ComnCdBaseVo;
import com.kspo.voc.sys.service.ComnCdService;

/**
 * 
* <pre>
* com.wigo.crm.common.ExampleController
*	- ExampleController.java
* </pre>
*
* @ClassName	: ExampleController 
* @Description	: CrmComnCdController 컨트롤러 
* @author 		: 김성태
* @date 		: 2022. 1. 8.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

@Controller
@RequestMapping(value = { "code", "{menuId}/code" })
public class ComnCdBaseController {
	@Autowired
	ComnCdService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "sys/codeList";
	}
	
	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws EgovBizException {
//		EzMap param = new EzMap(rparam);
		EzPaginationInfo page = param.getPaginationInfo();
		List<EzMap> list = service.getList(param);
		page.setTotalRecordCount(service.getListCount(param));
		return Utilities.getGridData(list,page);
	}
	
	@GetMapping(value = { "get" })
	public @ResponseBody Object get(@RequestParam Map<String, Object> rparam) throws EgovBizException {
		EzMap param = new EzMap(rparam);
		return service.get(param);
	}
	@PostMapping(value = { "save" })
	public @ResponseBody Object save(@RequestBody ComnCdBaseVo vo) throws EgovBizException {
		return service.save(vo);
	}	
	
	@PostMapping(value = { "saveList" })
	public @ResponseBody Object saveList(@RequestBody List<ComnCdBaseVo> list) throws EgovBizException {
		return service.saveList(list);
	}	
	@PostMapping(value = { "deleteList" })
	public @ResponseBody Object deleteList(@RequestBody List<ComnCdBaseVo> list) throws EgovBizException {
		return service.deleteList(list);
	}
	

	
}
