package com.egov.voc.sys.controller;


import com.egov.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.CrmUserBaseVo;
import com.egov.voc.sys.service.CrmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
@RequestMapping(value = {"myInfo","{menuCd}/myInfo"})
public class CrmMyInfoController {
	@Autowired
	CrmUserService userService;
	
	/* 내정보관리 메인 연결 */
	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm") + "sys/myInfo";
	}
	
	/* 비밀번호 변경 모달 연결 */
	@GetMapping(value = { "myInfoChgPsw" })
	public String userPopup(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/myInfoChgPwd";
	}
	
	/* 유저정보 조회 */
	@PostMapping(value = { "getUser" })
	public @ResponseBody Object getUser(@RequestBody EzMap param) throws Exception {
		CrmUserBaseVo user = userService.get(param);
		return user;
	} 
	
	/* Email, MobileNo 업데이트*/
	@PostMapping(value = { "saveMyInfo" })
	public @ResponseBody Object saveMyInfo(@RequestBody EzMap param) throws Exception {
		EzMap user = userService.updateMyInfo(param);
		return user;
	} 
	
	/* Password 업데이트*/
	@PostMapping(value = { "updatePwd" })
	public @ResponseBody Object resetPassword(@RequestBody EzMap param) throws Exception {
		return userService.updatePwd(param);
	}
}
