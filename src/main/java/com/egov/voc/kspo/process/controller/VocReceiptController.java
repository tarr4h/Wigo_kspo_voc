package com.egov.voc.kspo.process.controller;

import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.process.model.VocRegistrationVo;
import com.egov.voc.kspo.process.service.VocReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <pre>
 * com.egov.voc.kspo.process.controller.VocReceiptController
 *  - VocReceiptController.java
 * </pre>
 *
 * @ClassName     : VocReceiptController
 * @description   :
 * @author        : tarr4h
 * @date          : 2022-12-05
 *
*/

@Controller
@RequestMapping({"vocReceipt", "{menuCd}/vocReceipt"})
@Slf4j
public class VocReceiptController {

    @Autowired
    VocReceiptService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model){
        log.debug("param = {}", param);
        model.addAllAttributes(param);

        VocRegistrationVo registration = service.selectRegstration(param);
        model.addAttribute("registration", registration);
        return Utilities.getProperty("tiles.crm") + "voc/process/enroll/vocReceipt";
    }
}