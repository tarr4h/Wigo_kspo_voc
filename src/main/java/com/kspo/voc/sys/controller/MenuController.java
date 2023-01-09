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
import com.kspo.voc.sys.model.MenuVo;
import com.kspo.voc.sys.service.MenuService;

/**
 * 
* <pre>
* com.kspo.base.common.MenuController
*	- MenuController.java
* </pre>
*
* @ClassName	: MenuController 
* @Description	: MenuController 컨트롤러 
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
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "sys/menuList";
	}

	@GetMapping(value = { "add" })
	public String add(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		//model.addAllAttributes(param);
		MenuVo menu = service.getNewMenu(new MenuVo(param));
		model.addAllAttributes(Utilities.beanToMap(menu));
		return Utilities.getProperty("tiles.voc.blank") + "sys/menuAdd";
	}

	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws EgovBizException {
//	public @ResponseBody Object getList(@RequestParam Map<String, Object> rparam) throws EgovBizException {

		EzPaginationInfo page = param.getPaginationInfo();
		List<MenuVo> list = service.getList(param);
		page.setTotalRecordCount(service.getListCount(param));
		return Utilities.getGridData(list, page);
	}
	@PostMapping(value = { "getMenuTree" })
	public @ResponseBody Object getMenuTree(@RequestBody EzMap param) throws EgovBizException {
		return service.getTreeList(param);
	}

	
	@PostMapping(value = { "addMenu" })
	public @ResponseBody Object addMenu(@RequestBody MenuVo param) throws EgovBizException {
		return service.save(param);
		
	}

	@PostMapping(value = { "update" })
	public @ResponseBody Object editMenu(@RequestBody MenuVo param) throws EgovBizException {
		return service.update(param);
	}

	@PostMapping(value = { "deleteList" })
	public @ResponseBody Object removeMenuList(@RequestBody List<MenuVo> param) throws EgovBizException {
		return service.deleteList(param);
	}

	@PostMapping(value = { "delete" })
	public @ResponseBody Object removeMenu(@RequestBody MenuVo param) throws EgovBizException {
		return service.delete(param);
	}
	@PostMapping(value = { "saveList" })
	public @ResponseBody Object save(@RequestBody List<MenuVo> list) throws EgovBizException {
		return service.saveList(list);
	}
	@PostMapping(value = { "save" })
	public @ResponseBody Object saveMenu(@RequestBody MenuVo param) throws EgovBizException {
		return service.save(param);
	}
	@PostMapping(value = { "saveOrgd" })
	public @ResponseBody Object saveMenuSeq(@RequestBody MenuVo param) throws EgovBizException {
		return service.saveSeq(param);
	}
	
	@PostMapping(value = { "savemenuOdrgList" })
	public @ResponseBody Object saveMenuSeq(@RequestBody List<MenuVo> list) throws EgovBizException {
		return service.saveSeq(list);
	}

	
	
	
}
