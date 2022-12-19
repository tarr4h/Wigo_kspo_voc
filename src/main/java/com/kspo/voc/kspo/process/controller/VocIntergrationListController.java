package com.kspo.voc.kspo.process.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.kspo.common.stnd.ManageCodeCategory;
import com.kspo.voc.kspo.common.util.VocUtils;
import com.kspo.voc.kspo.process.model.VocRegistrationVo;
import com.kspo.voc.kspo.process.service.VocIntergrationListService;

/**
 * <pre>
 * com.kspo.voc.kspo.process.controller.VocIntergrationListController
 *  - VocIntergrationListController.java
 * </pre>
 *
 * @ClassName     : VocIntergrationListController
 * @description   : 통합목록 controller
 * @author        : tarr4h
 * @date          : 2022-11-30
 *
 */


@Controller
@RequestMapping({"vocIntergrationList", "{menuCd}/vocIntergrationList"})
public class VocIntergrationListController {

    @Autowired
    VocIntergrationListService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.crm") + "voc/process/list/vocIntergrationList";
    }

    @PostMapping(value = "selectList")
    public @ResponseBody Object selectList(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocRegistrationVo> list = service.selectList(param);
        page.setTotalRecordCount(service.selectListCount(param));
        return Utilities.getGridData(list, page);
    }

    @GetMapping(value = "selectChannel")
    public @ResponseBody Object selectChannel(@RequestParam Map<String, Object> param){
        return service.getManagementCodeSelect(param, ManageCodeCategory.CHANNEL);
    }

    @GetMapping(value = "selectType")
    public @ResponseBody Object selectType(@RequestParam Map<String, Object> param){
        return service.getManagementCodeSelect(param, ManageCodeCategory.TYPE);
    }

    @GetMapping(value = "selectStatusList")
    public @ResponseBody Object selectStatusList(@RequestParam Map<String, Object> param){
        return service.selectStatusList(param);
    }

    @PostMapping(value = "getOrgTree")
    public @ResponseBody Object getOrgTree(@RequestBody Map<String, Object> param){
        return VocUtils.getOrgTree(param);
    }

    @GetMapping(value = "selectOrgList")
    public @ResponseBody Object selectOrgList(@RequestParam Map<String, Object> param){
        return VocUtils.selectOrgList(param);
    }

    @PostMapping(value = "getEmpGrid")
    public @ResponseBody Object getEmpGrid(@RequestBody EzMap param){
        return VocUtils.getEmpGrid(param);
    }

    @PostMapping(value = "getOrgGrid")
    public @ResponseBody Object getOrgGrid(@RequestBody EzMap param){
        return VocUtils.getOrgGrid(param);
    }

    @GetMapping(value = { "openComnModal/{pageNm}"})
    public String openComnModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws Exception {
        model.addAttribute("param", param);
        return Utilities.getProperty("tiles.crm.blank") + "voc/common/" + pageNm;
    }
}
