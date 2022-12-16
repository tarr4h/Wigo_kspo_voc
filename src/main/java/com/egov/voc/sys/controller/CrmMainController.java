package com.egov.voc.sys.controller;

import com.egov.voc.comn.util.SessionUtil;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.CrmUserWdgtVo;
import com.egov.voc.sys.service.CrmMenuService;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Calendar;
import java.util.Map;

@Controller
@RequestMapping(value = { "" })
public class CrmMainController {
	@Autowired
	CrmMenuService menuService;
	@Value("${server.servlet.session.timeout:1800}")
	private int sessionTime;

//	@Autowired
//	EzJwtProvider jwtProvider;
	@GetMapping(value = { "", "index" })
	public String init(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> param, ModelMap map) throws Exception {
		param.put("userCd", SessionUtil.getLoginUserCd());

		map.addAttribute("wdgtList", menuService.getWdgtList(param));
		return "main";
	}

	@PostMapping(value = { "addWdgt" })
	public @ResponseBody Object addWdgt(@RequestBody CrmUserWdgtVo vo) throws Exception {
		return menuService.insertWdgt(vo);
	}

	@PostMapping(value = { "removeWdgt" })
	public @ResponseBody Object removeWdgt(@RequestBody CrmUserWdgtVo vo) throws Exception {
		return menuService.deleteWdgt(vo);
	}

	@GetMapping(value = { "main" })
	public String main(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> param, ModelMap map) throws Exception {
		return Utilities.getProperty("tiles.crm") + "main/index";
	}

	@GetMapping(value = { "sort" })
	public String sort(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> param, ModelMap map) throws Exception {
		return Utilities.getProperty("tiles.crm.blank") + "main/sort";
	}

	@GetMapping(value = { "util/audioPlayer" })
	public String audioPlayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> param, ModelMap map) throws Exception {
		map.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "main/audioPlayer";
	}

	@GetMapping(value = { "sort/textDialog" })
	public String textDialog(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> param, ModelMap map) throws Exception {
		map.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "main/textDialog";
	}

	@GetMapping(value = { "util/getTime" })
	public @ResponseBody Object getTime() throws Exception {
		EgovMap map = new EgovMap();
		map.put("currentTime", Calendar.getInstance().getTimeInMillis());
		map.put("sessionTime", sessionTime);
		return map;
	}

	@GetMapping(value = { "extendSession" })
	public @ResponseBody Object extendSession(@RequestParam Map<String, Object> param, ModelMap model,
			HttpServletResponse res) throws Exception {
		SessionUtil.touch(res);
		EgovMap map = new EgovMap();
		map.put("currentTime", Calendar.getInstance().getTimeInMillis());
		map.put("sessionTime", sessionTime);
		return map;
	}
//	@RequestMapping(value = {"createToken"})
//	public @ResponseBody Object createToken(@RequestBody EzMap param) 
//	{
//		String systemCd = param.getString("systemCd");
//		String userCd = Utilities.getLoginUserCd();
//		EzMap ret = new EzMap();
//		ret.setString("token", jwtProvider.createToken(userCd, systemCd));
//		return ret;
//	}

}
