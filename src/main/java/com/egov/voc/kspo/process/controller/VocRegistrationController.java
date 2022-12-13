package com.egov.voc.kspo.process.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.egov.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.stnd.ManageCodeCategory;
import com.egov.voc.kspo.common.stnd.PrcdStatus;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.process.model.VocRegistrationVo;
import com.egov.voc.kspo.process.service.VocRegistrationService;
import com.egov.voc.sys.model.CrmEmpBaseVo;

@Controller
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
//            List<Map<String, Object>> selectUpperChannel = 
            		service.selectUpperChannel(channel);
        }

        return Utilities.getProperty("tiles.crm") + "voc/process/enroll/vocRegistration";
    }

    @GetMapping(value = "selectChannel")
    public @ResponseBody Object selectChannel(@RequestParam Map<String, Object> param){
        return service.getManagementCodeSelect(param, ManageCodeCategory.CHANNEL);
    }

    @GetMapping(value = "selectType")
    public @ResponseBody Object selectType(@RequestParam Map<String, Object> param){
        return service.getManagementCodeSelect(param, ManageCodeCategory.TYPE);
    }

    @GetMapping(value = "selectLocation")
    public @ResponseBody Object selectLocation(@RequestParam Map<String, Object> param){
        // ???
        return service.getManagementCodeSelect(param, ManageCodeCategory.LOCATION);
    }

    @GetMapping(value = "getLoginUserInfo")
    public @ResponseBody Object getLoginUserInfo(@RequestParam Map<String, Object> param){
        CrmEmpBaseVo user = Utilities.getLoginUser();
        Map<String, Object> returnMap = Utilities.beanToMap(user);
        returnMap.put("empNm", VocUtils.getEmpNm(user.getEmpId()));
        returnMap.put("orgNm", VocUtils.getOrgNm(user.getOrgId()));
        return returnMap;
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
