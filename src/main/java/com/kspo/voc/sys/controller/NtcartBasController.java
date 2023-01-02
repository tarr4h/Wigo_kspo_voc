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
import com.kspo.voc.comn.util.Constants;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.NtcartBasVo;
import com.kspo.voc.sys.service.NtcartBasService;

@Controller
@RequestMapping(value = { "notice", "{menuId}/notice" })
public class NtcartBasController {

	@Autowired
	NtcartBasService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "sys/noticeList";
	}

	@GetMapping(value = { "add" })
	public String add(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		NtcartBasVo vo = new NtcartBasVo();
		vo.setBltId(Constants._NOTICE_BBS_ID);
		vo.setNtcartId(Utilities.getAutoSeq("BBS"));
		vo.setTopNtcartId(vo.getNtcartId());
		vo.setFileId(vo.getNtcartId());
		model.addAttribute("vo", vo);
		model.addAttribute("addMode", true);

		return Utilities.getProperty("tiles.voc.blank") + "sys/noticePop";
	}

	@GetMapping(value = { "mod" })
	public String mod(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		NtcartBasVo vo = service.get(param);
		model.addAttribute("vo", vo);
		model.addAttribute("addMode", false);

		return Utilities.getProperty("tiles.voc.blank") + "sys/noticePop";
	}

	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws EgovBizException {
		EzPaginationInfo page = param.getPaginationInfo();
		List<EzMap> list = service.getList(param);
		page.setTotalRecordCount(service.getListCount(param));
		return Utilities.getGridData(list, page);
	}

	@GetMapping(value = { "get" })
	public @ResponseBody Object get(@RequestParam Map<String, Object> rparam) throws EgovBizException {
		EzMap param = new EzMap(rparam);
		return service.get(param);
	}

	@PostMapping(value = { "save" })
	public @ResponseBody Object save(@RequestBody NtcartBasVo vo) throws EgovBizException {
		return service.save(vo);
	}

	@PostMapping(value = { "saveList" })
	public @ResponseBody Object saveList(@RequestBody List<NtcartBasVo> list) throws EgovBizException {
		return service.saveList(list);
	}

	@PostMapping(value = { "deleteList" })
	public @ResponseBody Object deleteList(@RequestBody List<NtcartBasVo> list) throws EgovBizException {
		return service.deleteList(list);
	}
	@PostMapping(value = { "remove" })
	public @ResponseBody Object remove(@RequestBody NtcartBasVo vo) throws EgovBizException {
		return service.delete(vo);
	}
}
