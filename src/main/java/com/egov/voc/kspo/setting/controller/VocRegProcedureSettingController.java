package com.egov.voc.kspo.setting.controller;


import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.setting.service.VocRegProcedureSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping({"vocRegProcedureSetting", "{menuCd}/vocRegProcedureSetting"})
public class VocRegProcedureSettingController {

    @Autowired
    VocRegProcedureSettingService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);
        return Utilities.getProperty("tiles.crm") + "voc/setting/procedure_setting/vocRegProcedureSetting";
    }

    @PostMapping(value = {"vocRegistrationManagementCodeTree"})
    public @ResponseBody Object vocRegistrationManagementCodeTree(@RequestBody EzMap param) throws Exception{
        return service.vocManagementCodeTree(param);
    }

    @GetMapping(value = { "openModal/{pageNm}"})
    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws Exception {
        model.addAttribute("param", param);

        return Utilities.getProperty("tiles.crm.blank") + "voc/setting/procedure_setting/" + pageNm;
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

    /**
     * voc_procedure_bas에서 등록 가능한 목록 조회
     * @param param
     * @return
     */
    @GetMapping(value = "selectPrcdBasList")
    public @ResponseBody Object selectPrcdBasList(@RequestParam Map<String, Object> param){
        return service.selectPrcdBasList(param);
    }


    @PostMapping(value = "insertProcedure")
    public @ResponseBody Object insertProcedure(@RequestBody EzMap param){
        return service.insertProcedure(param);
    }
}
