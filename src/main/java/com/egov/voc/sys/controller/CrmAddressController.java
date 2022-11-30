package com.egov.voc.sys.controller;


import com.egov.base.common.model.EzMap;
import com.egov.base.common.model.EzPaginationInfo;
import com.egov.base.common.model.address.EzAddressSo;
import com.egov.base.common.model.address.EzAdressResultVo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.service.CrmAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = { "address","{menuCd}/address" })
public class CrmAddressController {
	@Autowired
	CrmAddressService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm") + "sys/addressList";
	}
	@GetMapping(value = { "addressPop" })
	public String addressPop(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/addressPop";
	}
	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
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
