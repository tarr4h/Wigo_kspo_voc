package com.kspo.voc.program.setting.controller;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.program.common.util.VocUtils;
import com.kspo.voc.program.setting.model.VocDirOrgVo;
import com.kspo.voc.program.setting.model.VocMgmtPrcdVo;
import com.kspo.voc.program.setting.model.VocPrcdBasVo;
import com.kspo.voc.program.setting.service.VocMgmtPrcdService;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return Utilities.getProperty("tiles.voc") + "program/setting/voc_mgmt_prcd/vocMgmtPrcdSetting";
    }

    @PostMapping(value = {"vocMgmtCdTree"})
    public @ResponseBody Object vocMgmtCdTree(@RequestBody EzMap param) throws EgovBizException{
        return service.vocMgmtCdTree(param);
    }

    @GetMapping(value = "selectDirCd")
    public @ResponseBody Object selectSingleDirCd(@RequestParam Map<String, Object> param) throws EgovBizException {
        return service.selectDirCd(param);
    }

    @PostMapping(value = "insertDirOrg")
    public @ResponseBody Object insertDirOrg(@RequestBody EzMap param) throws EgovBizException{
        return service.insertDirOrg(param);
    }

    @PostMapping(value = "selectDirOrgGrid")
    public @ResponseBody Object selectDirOrgGrid(@RequestBody EzMap param) {
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocDirOrgVo> list = service.selectDirOrg(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @PostMapping(value = "updateDirOrg")
    public @ResponseBody Object updateDirOrg(@RequestBody EzMap param){
        return service.updateDirOrg(param);
    }

    @PostMapping(value = "deleteDirOrg")
    public @ResponseBody Object deleteDirOrg(@RequestBody EzMap param){
        return service.deleteDirOrg(param);
    }

    @PostMapping(value = "selectPrcdBasListGrid")
    public @ResponseBody Object selectPrcdBasListGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocPrcdBasVo> list = service.selectPrcdBasList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @PostMapping(value = "insertDirPrcd")
    public @ResponseBody Object insertDirPrcd(@RequestBody EzMap param){
        return service.insertDirPrcd(param);
    }

    @PostMapping(value = "selectMgmtPrcdGrid")
    public @ResponseBody Object selectMgmtPrcdGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocMgmtPrcdVo> list = service.selectMgmtPrcdList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @PostMapping(value = "deleteMgmtPrcd")
    public @ResponseBody Object deleteMgmtPrcd(@RequestBody EzMap param){
        return service.deleteMgmtPrcd(param);
    }

    @GetMapping(value = { "openModal/{pageNm}"})
    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws EgovBizException {
        model.addAttribute("param", param);
        return Utilities.getProperty("tiles.voc.blank") + "program/setting/voc_mgmt_prcd/" + pageNm;
    }

    @GetMapping(value = { "openComnModal/{pageNm}"})
    public String openComnModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws EgovBizException {
        model.addAttribute("param", param);
        return Utilities.getProperty("tiles.voc.blank") + "program/common/" + pageNm;
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
