package com.egov.voc.kspo.process.controller;

import com.egov.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.stnd.ManageCodeCategoryEnum;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.process.service.VocRegistrationListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * <pre>
 * com.egov.voc.kspo.process.controller.VocRegistrationListController
 *  - VocRegistrationListController.java
 * </pre>
 *
 * @ClassName     : VocRegistrationListController
 * @description   : 등록목록 controller
 * @author        : tarr4h
 * @date          : 2022-11-30
 *
 */


@Controller
@RequestMapping({"vocRegistrationList", "{menuCd}/vocRegistrationList"})
public class VocRegistrationListController {

    @Autowired
    VocRegistrationListService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.crm") + "voc/process/list/vocRegistrationList";
    }

    @GetMapping(value = "selectChannel")
    public @ResponseBody Object selectChannel(@RequestParam Map<String, Object> param){
        return service.getManagementCodeSelect(param, ManageCodeCategoryEnum.REGISTRATION);
    }

    @GetMapping(value = "selectStatus")
    public @ResponseBody Object selectStatus(@RequestParam Map<String, Object> param){
        return service.selectStatus(param);
    }

    @GetMapping(value = { "openComnModal/{pageNm}"})
    public String openComnModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws Exception {
        model.addAttribute("param", param);
        return Utilities.getProperty("tiles.crm.blank") + "voc/common/" + pageNm;
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
}
