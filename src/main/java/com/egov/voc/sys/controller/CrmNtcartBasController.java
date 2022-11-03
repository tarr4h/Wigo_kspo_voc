package com.egov.voc.sys.controller;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Constants;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.CrmNtcartBasVo;
import com.egov.voc.sys.service.CrmNtcartBasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = { "notice", "{menuCd}/notice" })
public class CrmNtcartBasController {

	@Autowired
	CrmNtcartBasService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm") + "sys/noticeList";
	}

	@GetMapping(value = { "add" })
	public String add(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		CrmNtcartBasVo vo = new CrmNtcartBasVo();
		vo.setBrdId(Constants._NOTICE_BBS_ID);
		vo.setNtcartId(Utilities.getAutoSeq("BBS"));
		vo.setTopNtcartId(vo.getNtcartId());
		vo.setFileCd(vo.getNtcartId());
		model.addAttribute("vo", vo);
		model.addAttribute("addMode", true);

		return Utilities.getProperty("tiles.crm.blank") + "sys/noticePop";
	}

	@GetMapping(value = { "mod" })
	public String mod(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		CrmNtcartBasVo vo = service.get(param);
		model.addAttribute("vo", vo);
		model.addAttribute("addMode", false);

		return Utilities.getProperty("tiles.crm.blank") + "sys/noticePop";
	}

	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
		EzPaginationInfo page = param.getPaginationInfo();
		List<EzMap> list = service.getList(param);
		page.setTotalRecordCount(service.getListCount(param));
		return Utilities.getGridData(list, page);
	}

	@GetMapping(value = { "get" })
	public @ResponseBody Object get(@RequestParam Map<String, Object> rparam) throws Exception {
		EzMap param = new EzMap(rparam);
		return service.get(param);
	}

	@PostMapping(value = { "save" })
	public @ResponseBody Object save(@RequestBody CrmNtcartBasVo vo) throws Exception {
		return service.save(vo);
	}

	@PostMapping(value = { "saveList" })
	public @ResponseBody Object saveList(@RequestBody List<CrmNtcartBasVo> list) throws Exception {
		return service.saveList(list);
	}

	@PostMapping(value = { "deleteList" })
	public @ResponseBody Object deleteList(@RequestBody List<CrmNtcartBasVo> list) throws Exception {
		return service.deleteList(list);
	}
	@PostMapping(value = { "remove" })
	public @ResponseBody Object remove(@RequestBody CrmNtcartBasVo vo) throws Exception {
		return service.delete(vo);
	}
}
