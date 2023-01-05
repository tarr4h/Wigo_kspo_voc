package com.kspo.voc.kspo.setting.controller;

import com.kspo.voc.comn.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.kspo.setting.controller.VocMgmtPrcdController
 *  - VocMgmtPrcdController.java
 * </pre>
 *
 * @ClassName     : VocMgmtPrcdController
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-05
 *
*/
@Controller
@Slf4j
@RequestMapping({"vocMgmtPrcd", "{menuId}/vocMgmtPrcd"})
public class VocMgmtPrcdController {

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        log.debug("tiles property = {}", Utilities.getProperty("tiles.voc"));
        return Utilities.getProperty("tiles.voc") + "voc/setting/mgmt_prcd/vocMgmtPrcdSetting";
    }

}
