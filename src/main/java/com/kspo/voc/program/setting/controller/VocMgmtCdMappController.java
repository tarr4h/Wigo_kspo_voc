package com.kspo.voc.program.setting.controller;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.program.common.stnd.MgmtCodeCategory;
import com.kspo.voc.program.setting.service.VocMgmtCdMappService;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping({ "vocMgmtCdMapp", "{menuId}/vocMgmtCdMapp" })
public class VocMgmtCdMappController {

	@Autowired
	VocMgmtCdMappService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, Model model) {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "program/setting/mgmt_cd_mapp/vocMgmtCdMapp";
	}

	@PostMapping(value = { "vocMgmtCdTree" })
	public @ResponseBody Object vocMgmtCdTree(@RequestBody EzMap param) throws EgovBizException {
		MgmtCodeCategory.setMgmtCdListTreeMap(param,
				Arrays.asList(MgmtCodeCategory.CHANNEL, MgmtCodeCategory.TYPE,
						MgmtCodeCategory.TARGET));
		return service.vocMgmtCdTree(param);
	}

	@PostMapping(value = "vocMgmtCdMappTree")
	public @ResponseBody Object vocMgmtCdMappTree(@RequestBody EzMap param) throws EgovBizException {
		return service.vocMgmtCdMappTree(param);
	}

	@GetMapping(value = { "openModal/{pageNm}" })
	public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model)
			throws EgovBizException {
		model.addAttribute("param", param);
		return Utilities.getProperty("tiles.voc.blank") + "program/setting/mgmt_cd_mapp/" + pageNm;
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
