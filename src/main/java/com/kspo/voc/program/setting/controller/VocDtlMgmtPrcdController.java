package com.kspo.voc.program.setting.controller;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.program.setting.model.VocPrcdBasVo;
import com.kspo.voc.program.setting.service.VocDtlMgmtPrcdService;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.program.setting.controller.VocDtlMgmtPrcdController
 *  - VocDtlMgmtPrcdController.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : VocDtlMgmtPrcdController
 * @description :
 * @date : 2023-01-09
 */

@Controller
@Slf4j
@RequestMapping({"vocDtlMgmtPrcd", "{menuId}/vocDtlMgmtPrcd"})
public class VocDtlMgmtPrcdController {

    @Autowired
    VocDtlMgmtPrcdService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        log.debug("tiles property = {}", Utilities.getProperty("tiles.voc"));
        return Utilities.getProperty("tiles.voc") + "program/setting/voc_mgmt_prcd/vocDtlMgmtPrcdSetting";
    }

    @PostMapping(value = {"vocMgmtChCdTree"})
    public @ResponseBody Object vocMgmtChCdTree(@RequestBody EzMap param) throws EgovBizException {
        return service.vocMgmtChCdTree(param);
    }

    @PostMapping(value = {"vocMgmtTpCdTree"})
    public @ResponseBody Object vocMgmtTpCdTree(@RequestBody EzMap param) throws EgovBizException {
        return service.vocMgmtTpCdTree(param);
    }

    @GetMapping(value = "selectSingleDirCd")
    public @ResponseBody Object selectSingleDirCd(@RequestParam Map<String, Object> param){
        return service.selectChannelDirCd(param);
    }

    @GetMapping(value = "selectComboDirCd")
    public @ResponseBody Object selectComboDirCd(@RequestParam Map<String, Object> param){
        return service.selectComboDirCd(param);
    }

    @PostMapping(value = "selectPrcdBasListGrid")
    public @ResponseBody Object selectPrcdBasListGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocPrcdBasVo> list = service.selectPrcdBasList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @GetMapping(value = { "openAddModal/{pageNm}"})
    public String openAddModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws EgovBizException {
        model.addAttribute("param", param);
        return Utilities.getProperty("tiles.voc.blank") + "program/setting/voc_mgmt_prcd/" + pageNm;
    }
}
