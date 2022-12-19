package com.kspo.voc.kspo.setting.controller;

import java.util.Arrays;
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
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.kspo.common.stnd.ManageCodeCategory;
import com.kspo.voc.kspo.setting.service.VocProcedureMappingService;

@Controller
@RequestMapping({"vocProcedureMapping", "{menuId}/vocProcedureMapping"})
public class VocProcedureMappingController {

    @Autowired
    VocProcedureMappingService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);
        return Utilities.getProperty("tiles.voc") + "voc/setting/procedure_mapping/vocProcedureMapping";
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
        return Utilities.getProperty("tiles.voc.blank") + "voc/setting/procedure_mapping/" + pageNm;
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
