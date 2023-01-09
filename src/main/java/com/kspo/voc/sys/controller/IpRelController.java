package com.kspo.voc.sys.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.kspo.voc.comn.util.Utilities;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.sys.model.IpRelVo;
import com.kspo.voc.sys.service.IpRelService;

/**
 * 
 * @ClassName    IpRelController
 * @author    김성태
 * @date    2023. 1. 9.
 * @Version    1.0
 * @description    아이피관계 Controller
 * @Company    Copyright ⓒ wigo.ai. All Right Reserved
 */

@Controller
@RequestMapping(value = { "ipRel", "{menuCd}/ipRel" })
public class IpRelController{

@Autowired
IpRelService service;

/**
 * 
 * @author 김성태
 * @date 2023. 1. 9. * @param param
 * @param model
 * @return
 * @throws Exception
 * @description 아이피관계 목록페이지
 *
 */
@GetMapping(value = { "", "index" })
public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
    model.addAllAttributes(param);
    return Utilities.getProperty("tiles.voc") + "sys/ipRelList";
}

/**
 * 
 * @author 김성태
 * @date 2023. 1. 9. * @param param
 * @return
 * @throws Exception
 * @description 아이피관계 목록검색
 *
 */
@PostMapping(value = { "getList" })
public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {
    EzPaginationInfo page = param.getPaginationInfo();
    List<IpRelVo> list = service.getList(param);
    page.setTotalRecordCount(service.getListCount(param));
    return Utilities.getGridData(list,page);
}

/**
 * 
 * @author 김성태
 * @date 2023. 1. 9. * @param rparam
 * @return
 * @throws Exception
 * @description 아이피관계 1건검색
 *
 */
@GetMapping(value = { "get" })
public @ResponseBody Object get(@RequestParam Map<String, Object> rparam) throws Exception {
    EzMap param = new EzMap(rparam);
    return service.get(param);
}
/**
 * 
 * @author 김성태
 * @date 2023. 1. 9. * @param vo
 * @return
 * @throws Exception
 * @description 아이피관계 저장
 * */@PostMapping(value = {"save" })
public @ResponseBody Object save(@RequestBody IpRelVo vo) throws Exception {
    return service.save(vo);
}

/**
 * 
 * @author 김성태
 * @date 2023. 1. 9. * @param list
 * @return
 * @throws Exception
 * @description 아이피관계 리스트 저장
 *
 */
@PostMapping(value = { "saveList" })
public @ResponseBody Object saveList(@RequestBody List<IpRelVo> list) throws Exception {
    return service.saveList(list);
}
/**
 * 
 * @author 김성태
 * @date 2023. 1. 9.
 * @param list
 * @return
 * @throws Exception
 * @description 아이피관계 리스트 삭제
 *
 */
@PostMapping(value = { "deleteList" })
public @ResponseBody Object deleteList(@RequestBody List<IpRelVo> list) throws Exception {
    return service.deleteList(list);
}
}
