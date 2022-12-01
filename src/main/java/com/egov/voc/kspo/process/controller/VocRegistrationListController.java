package com.egov.voc.kspo.process.controller;

import com.egov.base.common.model.EzMap;
import com.egov.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.stnd.ManageCodeCategory;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.process.model.VocRegistrationVo;
import com.egov.voc.kspo.process.service.VocRegistrationListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping(value = "selectList")
    public @ResponseBody Object selectList(@RequestBody EzMap param) throws Exception{
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocRegistrationVo> list = service.selectList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @GetMapping(value = "selectChannel")
    public @ResponseBody Object selectChannel(@RequestParam Map<String, Object> param){
        return service.getManagementCodeSelect(param, ManageCodeCategory.REGISTRATION);
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
