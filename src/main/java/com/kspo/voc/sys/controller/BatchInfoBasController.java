package com.kspo.voc.sys.controller;

import java.util.List;
import java.util.Map;

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
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.BatchInfoBasVo;
import com.kspo.voc.sys.service.BatchInfoBasService;

/**
 * 
 * @ClassName BatchInfoBasController
 * @author user
 * @date 2022. 12. 22.
 * @Version 1.0
 * @description 배치정보기본 Controller
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */

@Controller
@RequestMapping(value = { "BatchInfoBas", "{menuId}/BatchInfoBas" })
public class BatchInfoBasController {

	@Autowired
	BatchInfoBasService service;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "model/BatchInfoBasList";
	}

	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
		EzPaginationInfo page = param.getPaginationInfo();
		List<BatchInfoBasVo> list = service.getList(param);
		page.setTotalRecordCount(service.getListCount(param));
		return Utilities.getGridData(list, page);
	}

	@GetMapping(value = { "get" })
	public @ResponseBody Object get(@RequestParam Map<String, Object> rparam) throws Exception {
		EzMap param = new EzMap(rparam);
		return service.get(param);
	}

	@PostMapping(value = { "save" })
	public @ResponseBody Object save(@RequestBody BatchInfoBasVo vo) throws Exception {
		return service.save(vo);
	}

	@PostMapping(value = { "saveList" })
	public @ResponseBody Object saveList(@RequestBody List<BatchInfoBasVo> list) throws Exception {
		return service.saveList(list);
	}

	@PostMapping(value = { "deleteList" })
	public @ResponseBody Object deleteList(@RequestBody List<BatchInfoBasVo> list) throws Exception {
		return service.deleteList(list);
	}
}
