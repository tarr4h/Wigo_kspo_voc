package com.kspo.voc.sys.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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

import com.kspo.voc.sys.model.LoginUserVo;
import com.kspo.voc.sys.service.LoginService;

@Controller
@RequestMapping(value = { "login", "logout", "{menuId}/login", "{menuId}/logout" })
public class LoginController {

	@Autowired
	LoginService service;
//	@Value("${spring.sso.logout-url}")
//	private String logoutUrl;

	//
	@GetMapping(value = { "index", "" })
	public String loginForm(@RequestParam Map<String, Object> param, ModelMap model, HttpServletResponse res)
			throws EgovBizException {
		try {
			return service.goSso(res);
		} catch (IOException e) {
			throw new EgovBizException(e.getMessage(),e);
		}
//		String saveId = Utilities.getCookieValue("saveId");
//		if (Utilities.isNotEmpty(saveId))
//			model.addAttribute("loginId", Utilities.decrypt(saveId));
//		return "login";
	}

	//
	@PostMapping(value = { "/login" })
	public @ResponseBody Object login(@RequestBody LoginUserVo param) throws EgovBizException {
		return service.updatelogin(param);
	}

	@PostMapping(value = { "/logout" })
	public @ResponseBody Object logout() throws EgovBizException {

		return service.updatelogout();
	}

//	@GetMapping(value = { "/logout" })
//	public String logout(HttpServletRequest request, HttpServletResponse response) throws EgovBizException {
//		service.updatelogout();
//		response.sendRedirect(logoutUrl);
//		return "logout";
//	}

//	@GetMapping(value = { "/oauth" })
//	public void oauth(@RequestParam Map<String, Object> param, HttpServletRequest request, HttpServletResponse response,
//			ModelMap model) throws EgovBizException {
//		service.processSso(param, request, response);
//	}

}
