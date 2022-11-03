package com.egov.voc.sys.controller;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.CrmApiExecHstVo;
import com.egov.voc.sys.model.CrmApiInfoBaseVo;
import com.egov.voc.sys.service.CrmApiManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
* <pre>
* com.wigo.crm.common.ExampleController
*	- ExampleController.java
* </pre>
*
* @ClassName	: ExampleController 
* @Description	: ExampleController 컨트롤러 
* @author 		: 김성태
* @date 		: 2022. 1. 8.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

@Controller
@RequestMapping(value = { "apiManage" })
public class CrmApiManageController {
	@Autowired
	CrmApiManageService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm") + "sys/apiManageList";
	}
	@GetMapping(value = { "detail/{apiCd}" })
	public String detail(CrmApiExecHstVo vo, @PathVariable("apiCd") String apiCd , @RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAttribute("apiCd", apiCd);
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/apiHistPop";
	}
	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
//		EzMap param = new EzMap(rparam);
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
	@PostMapping(value = { "save" })
	public @ResponseBody Object save(@RequestBody CrmApiInfoBaseVo vo) throws Exception {
		return service.save(vo);
	}	
	
	@PostMapping(value = { "saveList" })
	public @ResponseBody Object saveList(@RequestBody List<CrmApiInfoBaseVo> list) throws Exception {
		return service.saveList(list);
	}	
	@PostMapping(value = { "deleteList" })
	public @ResponseBody Object deleteList(@RequestBody List<CrmApiInfoBaseVo> list) throws Exception {
		return service.deleteList(list);
	}
	
	
	
}
