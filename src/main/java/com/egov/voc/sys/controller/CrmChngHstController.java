package com.egov.voc.sys.controller;


import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.CrmChngHstVo;
import com.egov.voc.sys.service.CrmChngHstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
* <pre>
* com.wigo.crm.common.CrmChngHstController
*	- CrmChngHstController.java
* </pre>
*
* @ClassName	: CrmChngHstService 
* @Description	: CrmChngHstService 컨트롤러 
* @author 		: 김성태
* @date 		: 2022. 1. 8.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

@Controller
@RequestMapping(value = {"chngHst"})
public class CrmChngHstController {
	@Autowired
	CrmChngHstService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm") + "sys/changeHistList";
	}
	
	@GetMapping(value = { "detail/{hstCd}" })
	public String detail(CrmChngHstVo vo, @PathVariable("hstCd") String hstCd , @RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		vo.setChngHstCd(hstCd);
		model.addAttribute("hist", service.get(vo));
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/changeHistDetail";
	}
	
	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
		EzPaginationInfo page = param.getPaginationInfo();
		List<EzMap> list = service.getList(param);
		page.setTotalRecordCount(service.getListCount(param));
		return Utilities.getGridData(list,page);
	} 
}
