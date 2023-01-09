package com.kspo.voc.sys.controller;


import java.util.ArrayList;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.ComnCdBaseVo;
import com.kspo.voc.sys.service.ComnCdService;

/**
 * 
* <pre>
* com.kspo.base.common.ExampleController
*	- ExampleController.java
* </pre>
*
* @ClassName	: ExampleController 
* @Description	: ComnCdController 컨트롤러 
* @author 		: 김성태
* @date 		: 2022. 1. 8.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

@Controller
@RequestMapping( value = { "commCode", "{menuId}/commCode" } )
public class ComnCdController {
	@Autowired
    ComnCdService service;

    @PostMapping( value = { "getComboCode" } )
    public @ResponseBody Object getComboCode( @RequestBody EzMap param ) throws EgovBizException {
    	if(Utilities.isEmpty(param.getString("codeCd")) && Utilities.isEmpty(param.getString("codeType")))
    		return new ArrayList<ComnCdBaseVo>();
        return service.getComboCode( param );
    }
    
    @GetMapping( value = { "getCode" } )
    public @ResponseBody Object getCode(@RequestParam Map<String,Object> rparam ) throws EgovBizException {
    	EzMap param = new EzMap(rparam);
    	return  getComboCode(param);
    }

	
}
