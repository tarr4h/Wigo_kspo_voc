package com.kspo.voc.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.CrmUserBaseVo;
import com.kspo.voc.sys.service.CrmUserService;

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
@RequestMapping(value = {"user","{menuCd}/user"})
public class CrmUserController {
	@Autowired
	CrmUserService userService;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm") + "sys/userList";
	}
	@GetMapping(value = { "userPopup" })
	public String userPopup(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/userPop";
	}
	
	
	@GetMapping(value = { "add" })
	public String add(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/userAdd";
	}
	
	@PostMapping(value = { "reset" })
	public String resetPopup(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/userReset";
	}
	
	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
		EzPaginationInfo page = param.getPaginationInfo();
		List<EzMap> list = userService.getList(param);
		page.setTotalRecordCount(userService.getListCount(param));
		return Utilities.getGridData(list,page);
	} 
	@PostMapping(value = { "getUser" })
	public @ResponseBody Object getUser(@RequestBody CrmUserBaseVo param) throws Exception {
		CrmUserBaseVo user = userService.get(param);
		return user;
	} 
	@PostMapping(value = { "addUser" })
	public @ResponseBody Object addUser(@RequestBody CrmUserBaseVo param) throws Exception {
		param.setStat("c");
		return userService.save(param);
	}
	@PostMapping(value = { "editUser" })
	public @ResponseBody Object editUser(@RequestBody CrmUserBaseVo param) throws Exception {
		param.setStat("u");
		return userService.save(param);
	}
	@PostMapping(value = { "removeUserList" })
	public @ResponseBody Object removeUserList(@RequestBody List<CrmUserBaseVo> param) throws Exception {
		return userService.deleteList(param);
	}
	@PostMapping(value = { "removeUser" })
	public @ResponseBody Object removeUser(@RequestBody CrmUserBaseVo param) throws Exception {
		return userService.delete(param);
	}
	@PostMapping(value = { "saveUser" })
	public @ResponseBody Object saveUser(@RequestBody CrmUserBaseVo param) throws Exception {
		return userService.save(param);
	}
	@PostMapping(value = { "save" })
	public @ResponseBody Object save(@RequestBody List<CrmUserBaseVo> param) throws Exception {
		return userService.saveList(param);
	}
	@PostMapping(value = { "resetPassword" })
	public @ResponseBody Object resetPassword(@RequestBody CrmUserBaseVo param) throws Exception {
		return userService.savePassword(param);
	}
	
}
