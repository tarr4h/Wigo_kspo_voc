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
import com.kspo.voc.sys.model.UserBaseVo;
import com.kspo.voc.sys.service.UserService;

/**
 * 
* <pre>
* com.wigo.crm.common.controller
*	- MyInfoController.java
* </pre>
*
* @ClassName	: MyInfoController 
* @Description	: 내정보관리 
* @author 		: 최승빈
* @date 		: 2021. 1. 18.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

@Controller
@RequestMapping(value = {"myInfo","{menuId}/myInfo"})
public class MyInfoController {
	@Autowired
	UserService userService;
	
	/* 내정보관리 메인 연결 */
	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "sys/myInfo";
	}
	
	/* 비밀번호 변경 모달 연결 */
	@GetMapping(value = { "myInfoChgPsw" })
	public String userPopup(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/myInfoChgPwd";
	}
	
	/* 유저정보 조회 */
	@PostMapping(value = { "getUser" })
	public @ResponseBody Object getUser(@RequestBody EzMap param) throws EgovBizException {
		UserBaseVo user = userService.get(param);
		return user;
	} 
	
	/* Email, MobileNo 업데이트*/
	@PostMapping(value = { "saveMyInfo" })
	public @ResponseBody Object saveMyInfo(@RequestBody EzMap param) throws EgovBizException {
		EzMap user = userService.updateMyInfo(param);
		return user;
	} 
	
	/* Password 업데이트*/
	@PostMapping(value = { "updatePwd" })
	public @ResponseBody Object resetPassword(@RequestBody EzMap param) throws EgovBizException {
		return userService.updatePwd(param);
	}
}
