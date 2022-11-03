package com.egov.voc.kspo.setting.controller;


import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.setting.service.VocProcedureSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping({"vocProcedureSetting", "{menuCd}/vocProcedureSetting"})
public class VocProcedureSettingController {

    @Autowired
    VocProcedureSettingService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.crm") + "voc/setting/procedure_setting/vocProcedureSetting";
    }
}
