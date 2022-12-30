package com.kspo.voc.kspo.setting.controller;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.kspo.common.stnd.ManageCodeCategory;
import com.kspo.voc.kspo.setting.service.VocManagementCdMappingService;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping({ "vocManagementCdMapping", "{menuId}/vocManagementCdMapping" })
public class VocManagementCdMappingController {

	@Autowired
	VocManagementCdMappingService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, Model model) {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "voc/setting/management_code_mapping/vocManagementCdMapping";
	}

	@PostMapping(value = { "vocManagementCodeTree" })
	public @ResponseBody Object vocManagementCdTree(@RequestBody EzMap param) throws EgovBizException {
		ManageCodeCategory.setComnCdListTreeMap(param,
				Arrays.asList(ManageCodeCategory.CHANNEL, ManageCodeCategory.TYPE, ManageCodeCategory.CAUSE,
						ManageCodeCategory.LOCATION, ManageCodeCategory.TARGET));
		return service.vocManagementCodeTree(param);
	}

	@PostMapping(value = "vocManagementCdMappingTree")
	public @ResponseBody Object vocManagementCdMappingTree(@RequestBody EzMap param) throws EgovBizException {
		return service.vocManagementCdMappingTree(param);
	}

	@GetMapping(value = { "openModal/{pageNm}" })
	public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model)
			throws EgovBizException {
		model.addAttribute("param", param);
		return Utilities.getProperty("tiles.voc.blank") + "voc/setting/management_code_mapping/" + pageNm;
	}

	@PostMapping(value = "insert")
	public @ResponseBody Object insert(@RequestBody EzMap param) {
		return service.insert(param);
	}

	@PostMapping(value = "delete")
	public @ResponseBody Object delete(@RequestBody EzMap param) throws EgovBizException {
		return service.delete(param);
	}

}
