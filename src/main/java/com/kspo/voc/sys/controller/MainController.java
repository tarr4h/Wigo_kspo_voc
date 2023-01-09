package com.kspo.voc.sys.controller;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kspo.voc.comn.util.SessionUtil;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.service.MenuService;

@Controller
@RequestMapping(value = { "" })
public class MainController {
	@Autowired
	MenuService menuService;
	@Value("${server.servlet.session.timeout:1800}")
	private int sessionTime;

//	@Autowired
//	EzJwtProvider jwtProvider;
	@GetMapping(value = { "", "index" })
	public String init(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> param, ModelMap map) throws EgovBizException {
//		param.put("userId", SessionUtil.getLoginUserId());
//
//		map.addAttribute("wdgtList", menuService.getWdgtList(param));
		return "main";
	}

//	@PostMapping(value = { "addWdgt" })
//	public @ResponseBody Object addWdgt(@RequestBody UserWdgtVo vo) throws EgovBizException {
//		return menuService.insertWdgt(vo);
//	}
//
//	@PostMapping(value = { "removeWdgt" })
//	public @ResponseBody Object removeWdgt(@RequestBody UserWdgtVo vo) throws EgovBizException {
//		return menuService.deleteWdgt(vo);
//	}

	@GetMapping(value = { "main" })
	public String main(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> param, ModelMap map) throws EgovBizException {
		return Utilities.getProperty("tiles.voc") + "main/index";
	}

	@GetMapping(value = { "sort" })
	public String sort(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> param, ModelMap map) throws EgovBizException {
		return Utilities.getProperty("tiles.voc.blank") + "main/sort";
	}

	@GetMapping(value = { "util/audioPlayer" })
	public String audioPlayer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> param, ModelMap map) throws EgovBizException {
		map.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "main/audioPlayer";
	}

	@GetMapping(value = { "sort/textDialog" })
	public String textDialog(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> param, ModelMap map) throws EgovBizException {
		map.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "main/textDialog";
	}

	@GetMapping(value = { "util/getTime" })
	public @ResponseBody Object getTime() throws EgovBizException {
		EgovMap map = new EgovMap();
		map.put("currentTime", Calendar.getInstance().getTimeInMillis());
		map.put("sessionTime", sessionTime);
		return map;
	}

	@GetMapping(value = { "extendSession" })
	public @ResponseBody Object extendSession(@RequestParam Map<String, Object> param, ModelMap model,
			HttpServletResponse res) throws EgovBizException {
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
//		String userId = Utilities.getLoginUserId();
//		EzMap ret = new EzMap();
//		ret.setString("token", jwtProvider.createToken(userId, systemCd));
//		return ret;
//	}

}
