package com.egov.voc.sys.controller;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.CrmMenuVo;
import com.egov.voc.sys.service.CrmMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
* <pre>
* com.wigo.crm.common.CrmMenuController
*	- CrmMenuController.java
* </pre>
*
* @ClassName	: CrmMenuController 
* @Description	: CrmMenuController 컨트롤러 
* @author 		: 김성태
* @date 		: 2022. 1. 8.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

@Controller
@RequestMapping(value = { "menu", "{menuCd}/menu" })
public class CrmMenuController {
	@Autowired
	CrmMenuService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm") + "sys/menuList";
	}

	@GetMapping(value = { "add" })
	public String add(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		//model.addAllAttributes(param);
		CrmMenuVo menu = service.getNewMenu(new CrmMenuVo(param));
		model.addAllAttributes(Utilities.beanToMap(menu));
		return Utilities.getProperty("tiles.crm.blank") + "sys/menuAdd";
	}

	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
//	public @ResponseBody Object getList(@RequestParam Map<String, Object> rparam) throws Exception {

		EzPaginationInfo page = param.getPaginationInfo();
		List<CrmMenuVo> list = service.getList(param);
		page.setTotalRecordCount(service.getListCount(param));
		return Utilities.getGridData(list, page);
	}
	@PostMapping(value = { "getMenuTree" })
	public @ResponseBody Object getMenuTree(@RequestBody EzMap param) throws Exception {
		return service.getTreeList(param);
	}

	
	@PostMapping(value = { "addMenu" })
	public @ResponseBody Object addMenu(@RequestBody CrmMenuVo param) throws Exception {
		return service.save(param);
		
	}

	@PostMapping(value = { "update" })
	public @ResponseBody Object editMenu(@RequestBody CrmMenuVo param) throws Exception {
		return service.update(param);
	}

	@PostMapping(value = { "deleteList" })
	public @ResponseBody Object removeMenuList(@RequestBody List<CrmMenuVo> param) throws Exception {
		return service.deleteList(param);
	}

	@PostMapping(value = { "delete" })
	public @ResponseBody Object removeMenu(@RequestBody CrmMenuVo param) throws Exception {
		return service.delete(param);
	}
	@PostMapping(value = { "saveList" })
	public @ResponseBody Object save(@RequestBody List<CrmMenuVo> list) throws Exception {
		return service.saveList(list);
	}
	@PostMapping(value = { "save" })
	public @ResponseBody Object saveMenu(@RequestBody CrmMenuVo param) throws Exception {
		return service.save(param);
	}
	@PostMapping(value = { "saveOrgd" })
	public @ResponseBody Object saveMenuSeq(@RequestBody CrmMenuVo param) throws Exception {
		return service.saveSeq(param);
	}
	
	@PostMapping(value = { "savemenuOdrgList" })
	public @ResponseBody Object saveMenuSeq(@RequestBody List<CrmMenuVo> list) throws Exception {
		return service.saveSeq(list);
	}

	
	
	
}
