package com.egov.voc.kspo.process.controller;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.process.service.VocRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping({"vocRegistration", "{menuCd}/vocRegistration"})
public class VocRegistrationController {

    @Autowired
    VocRegistrationService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.crm") + "voc/process/enroll/vocRegistration";
    }

    @GetMapping(value = "selectChannel")
    public @ResponseBody Object selectChannel(@RequestParam Map<String, Object> param){
        return service.selectChannel(param);
    }

    @PostMapping(value = "register")
    public @ResponseBody Object register(@RequestBody EzMap param) throws Exception{
        return service.register(param);
    }

    @PostMapping(value = "temporarySave")
    public @ResponseBody Object temporarySave(@RequestBody EzMap param) throws Exception{
        return service.temporarySave(param);
    }

}