package com.kspo.voc.program.process.controller;

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
 * com.kspo.voc.program.process.controller.VocRegistrationController
 *  - VocRegistrationController.java
 * </pre>
 *
 * @ClassName     : VocRegistrationController
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-05
 *
*/

@Controller
@Slf4j
@RequestMapping({"vocRegistration", "{menuId}/vocRegistration"})
public class VocRegistrationController {

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.voc") + "voc/process/enroll/vocRegistration";
    }
}
