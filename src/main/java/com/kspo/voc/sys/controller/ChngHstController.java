package com.kspo.voc.sys.controller;


import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.ChngHstVo;
import com.kspo.voc.sys.service.ChngHstService;

/**
 * 
* <pre>
* com.wigo.crm.common.ChngHstController
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
public class ChngHstController {
	@Autowired
	ChngHstService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "sys/changeHistList";
	}
	
	@GetMapping(value = { "detail/{hstCd}" })
	public String detail(ChngHstVo vo, @PathVariable("hstCd") String hstCd , @RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		vo.setChngHstId(hstCd);
		model.addAttribute("hist", service.get(vo));
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/changeHistDetail";
	}
	
	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws EgovBizException {
		EzPaginationInfo page = param.getPaginationInfo();
		List<EzMap> list = service.getList(param);
		page.setTotalRecordCount(service.getListCount(param));
		return Utilities.getGridData(list,page);
	} 
}
