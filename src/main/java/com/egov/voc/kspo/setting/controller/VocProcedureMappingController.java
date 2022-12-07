package com.egov.voc.kspo.setting.controller;

import com.egov.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.stnd.ManageCodeCategory;
import com.egov.voc.kspo.setting.service.VocProcedureMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping({"vocProcedureMapping", "{menuCd}/vocProcedureMapping"})
public class VocProcedureMappingController {

    @Autowired
    VocProcedureMappingService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);
        return Utilities.getProperty("tiles.crm") + "voc/setting/procedure_mapping/vocProcedureMapping";
    }

    @PostMapping(value = {"vocManagementCodeTree"})
    public @ResponseBody Object vocProcedureMappingTree(@RequestBody EzMap param) throws Exception{
        ManageCodeCategory.setComnCdListTreeMap(param, Arrays.asList(ManageCodeCategory.CHANNEL, ManageCodeCategory.TYPE, ManageCodeCategory.CAUSE, ManageCodeCategory.LOCATION, ManageCodeCategory.TARGET));
        return service.vocManagementCodeTree(param);
    }

    @PostMapping(value = "vocProcedureMappingTree")
    public @ResponseBody Object v(@RequestBody EzMap param) throws Exception{
        return service.vocProcedureMappingTree(param);
    }

    @GetMapping(value = { "openModal/{pageNm}"})
    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws Exception {
        model.addAttribute("param", param);
        return Utilities.getProperty("tiles.crm.blank") + "voc/setting/procedure_mapping/" + pageNm;
    }

    @PostMapping(value = "insert")
    public @ResponseBody Object insert(@RequestBody EzMap param){
        return service.insert(param);
    }

    @PostMapping(value = "delete")
    public @ResponseBody Object delete(@RequestBody EzMap param) throws Exception {
        return service.delete(param);
    }

}
