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
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.base.common.model.address.EzAddressSo;
import com.kspo.base.common.model.address.EzAdressResultVo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.service.AddressService;

/**
 * 
* <pre>
* com.kspo.base.common.ExampleController
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
@RequestMapping(value = { "address","{menuId}/address" })
public class AddressController {
	@Autowired
	AddressService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "sys/addressList";
	}
	@GetMapping(value = { "addressPop" })
	public String addressPop(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/addressPop";
	}
	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws EgovBizException {
//		EzMap param = new EzMap(rparam);
		EzPaginationInfo page = param.getPaginationInfo();
		EzAddressSo so = new EzAddressSo(page);
		so.setKeyword(  param.getString("keyword"));
		EzAdressResultVo result= service.getList(so);
		if(result == null)
			return null;
		page.setTotalRecordCount(Utilities.parseInt(result.getCommon().getTotalCount()));
		return Utilities.getGridData(result.getJuso(),page);
	}
	
	
	
}
