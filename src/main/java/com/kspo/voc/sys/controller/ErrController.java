package com.kspo.voc.sys.controller;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kspo.base.common.model.EzAjaxException;
import com.kspo.base.common.model.EzException;
import com.kspo.base.common.model.EzLoginAjaxException;
import com.kspo.base.common.model.EzLoginException;
import com.kspo.voc.comn.util.Constants;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.service.ErrHstService;
import com.kspo.voc.sys.service.LoginService;

/**
 * <pre>
 * com.wigo.crm.common.controller - ErrorController.java
 * </pre>
 *
 * @ClassName : ErrorController
 * @Description : 에러
 * @author : MKH
 * @date : 2021. 1. 21.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

@Controller
@ControllerAdvice
@RequestMapping(value = { "/error" })
public class ErrController implements ErrorController {
//	private Logger logger = LoggerFactory.getLogger(EzErrorController.class);
	private static final String ERROR_PATH = "/error";
	@Autowired
	LoginService loginService;

	@Autowired
	ErrHstService hstService;

//	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	@GetMapping(value = { "", "index" })
	public String init(HttpServletRequest request, @RequestParam Map<String, Object> param, ModelMap model)
			throws Exception {
//		Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");
//		if (ex != null) {
//
//			if (ex instanceof CannotRenderException) {
//				return Utilities.getProperty("tiles.kland") + "error/error";
//			}
//			if(ex.getCause() != null && ex.getCause() instanceof CannotRenderException)
//				return Utilities.getProperty("tiles.kland") + "error/error";
//		}
		return "error";

	}

	@GetMapping(value = { "render" })
	public String render(HttpServletRequest request, @RequestParam Map<String, Object> param, ModelMap model)
			throws Exception {
		return Utilities.getProperty("tiles.voc.black") + "/error/error";
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public @ResponseBody void keyError(SQLIntegrityConstraintViolationException ex, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (ex.getErrorCode() == 1)
			response.sendError(486, "중복된 값이 존재합니다.");
		else
			response.sendError(404);

	}

	@ExceptionHandler(EzLoginException.class)
	public String ezLoginError(EzLoginException ex, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return loginService.goSso(response);
	}

	@ExceptionHandler(EzLoginAjaxException.class)
	public @ResponseBody Object ezAjaxError(EzLoginAjaxException ex, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setStatus(Constants._ERROR_NEED_LOGIN);
		return ex.toMap();
	}

	@ExceptionHandler(EzAjaxException.class)
	public @ResponseBody Object ezAjaxError(EzAjaxException ex, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setStatus(487);
		hstService.addErrorLog(ex, request, response);
		return ex.toMap();
	}

	@ExceptionHandler(EzException.class)
	public @ResponseBody Object ezError(EzException ex, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setStatus(487);
		hstService.addErrorLog(ex, request, response);
		return ex.toMap();
	}
	@ExceptionHandler(EgovBizException.class)
	public @ResponseBody Object error(EgovBizException ex, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		hstService.addErrorLog(ex, request, response);
		response.setStatus(488);
		EzException e = new EzException(Constants._ERROR_UNKNOWN, "", ex);
		return e.toMap();
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody Object error(Exception ex, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		hstService.addErrorLog(ex, request, response);
		response.setStatus(488);
		EzException e = new EzException(Constants._ERROR_UNKNOWN, "", ex);
		return e.toMap();
	}

}
