package com.egov.voc.kspo.process.controller;

import com.egov.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.stnd.PrcdStatus;
import com.egov.voc.kspo.process.model.VocRegistrationVo;
import com.egov.voc.kspo.process.service.VocRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

        String regSeq = (String) param.get("regSeq");
        if(regSeq != null){
            VocRegistrationVo registration = service.select(param);
            model.addAttribute("registration", registration);

            String channel = registration.getChannel();
            List<Map<String, Object>> selectUpperChannel = service.selectUpperChannel(channel);
        }

        return Utilities.getProperty("tiles.crm") + "voc/process/enroll/vocRegistration";
    }

    @GetMapping(value = "selectChannel")
    public @ResponseBody Object selectChannel(@RequestParam Map<String, Object> param){
        return service.selectChannel(param);
    }

    @PostMapping(value = "registerComplete")
    public @ResponseBody Object registerComplete(@RequestBody EzMap param) throws Exception{
        return service.register(param, PrcdStatus.COMPLETE);
    }

    @PostMapping(value = "registerTemporary")
    public @ResponseBody Object registerTemporary(@RequestBody EzMap param) throws Exception{
        return service.register(param, PrcdStatus.ONGOING);
    }

}
