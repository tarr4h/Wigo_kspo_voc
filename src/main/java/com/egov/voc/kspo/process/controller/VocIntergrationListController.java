package com.egov.voc.kspo.process.controller;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.process.model.VocRegistrationVo;
import com.egov.voc.kspo.process.service.VocIntergrationListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"vocIntergrationList", "{menuCd}/vocIntergrationList"})
@Slf4j
public class VocIntergrationListController {

    @Autowired
    VocIntergrationListService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.crm") + "voc/process/list/vocIntergrationList";
    }

    @PostMapping(value = "selectList")
    public @ResponseBody Object selectList(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocRegistrationVo> list = service.selectProcedureCodeList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }
}
