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
import com.kspo.voc.sys.model.UserBaseVo;
import com.kspo.voc.sys.service.UserService;

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
@RequestMapping(value = {"user","{menuId}/user"})
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "sys/userList";
	}
	@GetMapping(value = { "userPopup" })
	public String userPopup(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/userPop";
	}
	
	
	@GetMapping(value = { "add" })
	public String add(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/userAdd";
	}
	
	@PostMapping(value = { "reset" })
	public String resetPopup(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/userReset";
	}
	
	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws EgovBizException {
		EzPaginationInfo page = param.getPaginationInfo();
		List<EzMap> list = userService.getList(param);
		page.setTotalRecordCount(userService.getListCount(param));
		return Utilities.getGridData(list,page);
	} 
	@PostMapping(value = { "getUser" })
	public @ResponseBody Object getUser(@RequestBody UserBaseVo param) throws EgovBizException {
		UserBaseVo user = userService.get(param);
		return user;
	} 
	@PostMapping(value = { "addUser" })
	public @ResponseBody Object addUser(@RequestBody UserBaseVo param) throws EgovBizException {
		param.setStat("c");
		return userService.save(param);
	}
	@PostMapping(value = { "editUser" })
	public @ResponseBody Object editUser(@RequestBody UserBaseVo param) throws EgovBizException {
		param.setStat("u");
		return userService.save(param);
	}
	@PostMapping(value = { "removeUserList" })
	public @ResponseBody Object removeUserList(@RequestBody List<UserBaseVo> param) throws EgovBizException {
		return userService.deleteList(param);
	}
	@PostMapping(value = { "removeUser" })
	public @ResponseBody Object removeUser(@RequestBody UserBaseVo param) throws EgovBizException {
		return userService.delete(param);
	}
	@PostMapping(value = { "saveUser" })
	public @ResponseBody Object saveUser(@RequestBody UserBaseVo param) throws EgovBizException {
		return userService.save(param);
	}
	@PostMapping(value = { "save" })
	public @ResponseBody Object save(@RequestBody List<UserBaseVo> param) throws EgovBizException {
		return userService.saveList(param);
	}
	@PostMapping(value = { "resetPassword" })
	public @ResponseBody Object resetPassword(@RequestBody UserBaseVo param) throws EgovBizException {
		return userService.savePassword(param);
	}
	
}
