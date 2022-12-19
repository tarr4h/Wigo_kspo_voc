package com.kspo.voc.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.MenuVo;
import com.kspo.voc.sys.service.MenuService;

import java.util.List;
import java.util.Map;

/**
 * 
* <pre>
* com.wigo.crm.common.MenuController
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
@RequestMapping(value = { "menu", "{menuId}/menu" })
public class MenuController {
	@Autowired
	MenuService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "sys/menuList";
	}

	@GetMapping(value = { "add" })
	public String add(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		//model.addAllAttributes(param);
		MenuVo menu = service.getNewMenu(new MenuVo(param));
		model.addAllAttributes(Utilities.beanToMap(menu));
		return Utilities.getProperty("tiles.voc.blank") + "sys/menuAdd";
	}

	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
//	public @ResponseBody Object getList(@RequestParam Map<String, Object> rparam) throws Exception {

		EzPaginationInfo page = param.getPaginationInfo();
		List<MenuVo> list = service.getList(param);
		page.setTotalRecordCount(service.getListCount(param));
		return Utilities.getGridData(list, page);
	}
	@PostMapping(value = { "getMenuTree" })
	public @ResponseBody Object getMenuTree(@RequestBody EzMap param) throws Exception {
		return service.getTreeList(param);
	}

	
	@PostMapping(value = { "addMenu" })
	public @ResponseBody Object addMenu(@RequestBody MenuVo param) throws Exception {
		return service.save(param);
		
	}

	@PostMapping(value = { "update" })
	public @ResponseBody Object editMenu(@RequestBody MenuVo param) throws Exception {
		return service.update(param);
	}

	@PostMapping(value = { "deleteList" })
	public @ResponseBody Object removeMenuList(@RequestBody List<MenuVo> param) throws Exception {
		return service.deleteList(param);
	}

	@PostMapping(value = { "delete" })
	public @ResponseBody Object removeMenu(@RequestBody MenuVo param) throws Exception {
		return service.delete(param);
	}
	@PostMapping(value = { "saveList" })
	public @ResponseBody Object save(@RequestBody List<MenuVo> list) throws Exception {
		return service.saveList(list);
	}
	@PostMapping(value = { "save" })
	public @ResponseBody Object saveMenu(@RequestBody MenuVo param) throws Exception {
		return service.save(param);
	}
	@PostMapping(value = { "saveOrgd" })
	public @ResponseBody Object saveMenuSeq(@RequestBody MenuVo param) throws Exception {
		return service.saveSeq(param);
	}
	
	@PostMapping(value = { "savemenuOdrgList" })
	public @ResponseBody Object saveMenuSeq(@RequestBody List<MenuVo> list) throws Exception {
		return service.saveSeq(list);
	}

	
	
	
}
