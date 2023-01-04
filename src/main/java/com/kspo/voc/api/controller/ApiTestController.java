package com.kspo.voc.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kspo.base.api.controller.BaseRestController;
import com.kspo.base.api.model.ApiResultVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * @ClassName MenuBasController
 * @author 김성태
 * @date 2023. 1. 4.
 * @Version 1.0
 * @description 메뉴기본 Controller
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */

@Tag(name = "메뉴기본", description = "메뉴기본 API")
@RestController
@RequestMapping("/api/test")
public class ApiTestController extends BaseRestController {

	/**
	 *
	 * @author 김성태
	 * @date 2023. 1. 4.
	 * @param so
	 * @param param
	 * @return
	 * @throws Exception
	 * @description 메뉴기본 검색
	 *
	 */
	@GetMapping("")
	@Operation(summary = "메뉴기본 검색", description = "메뉴기본 검색")
	public ResponseEntity<ApiResultVo<List<Object>>> getMenuBasList() throws Exception {
		List<Object> list = new ArrayList<Object>();

		return successResponse(list);
	}

}
