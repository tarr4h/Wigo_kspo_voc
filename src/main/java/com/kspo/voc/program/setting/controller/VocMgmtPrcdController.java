package com.kspo.voc.program.setting.controller;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.program.common.util.VocUtils;
import com.kspo.voc.program.setting.service.VocMgmtPrcdService;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.program.setting.controller.VocMgmtPrcdController
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

    @Autowired
    VocMgmtPrcdService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        log.debug("tiles property = {}", Utilities.getProperty("tiles.voc"));
        return Utilities.getProperty("tiles.voc") + "voc/setting/voc_mgmt_prcd/vocMgmtPrcdSetting";
    }

    @PostMapping(value = {"vocMgmtCdTree"})
    public @ResponseBody Object vocMgmtCdTree(@RequestBody EzMap param) throws EgovBizException{
        return service.vocMgmtCdTree(param);
    }

    @GetMapping(value = "selectDirCd")
    public @ResponseBody Object selectDirCd(@RequestParam Map<String, Object> param) throws EgovBizException {
        return service.selectDirCd(param);
    }

    @PostMapping(value = "insertDirOrg")
    public @ResponseBody Object insertDirOrg(@RequestBody EzMap param) throws EgovBizException{
        return service.insertDirOrg(param);
    }

    @GetMapping(value = { "openComnModal/{pageNm}"})
    public String openComnModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws EgovBizException {
        model.addAttribute("param", param);
        return Utilities.getProperty("tiles.voc.blank") + "voc/common/" + pageNm;
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


//    @GetMapping(value = { "openModal/{pageNm}"})
//    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws EgovBizException {
//        model.addAttribute("param", param);
//
//        return Utilities.getProperty("tiles.voc.vblank") + "voc/setting/actv_bas/" + pageNm;
////        return Utilities.getProperty("tiles.voc.blank") + "voc/setting/actv_bas/" + pageNm;
//    }
}
