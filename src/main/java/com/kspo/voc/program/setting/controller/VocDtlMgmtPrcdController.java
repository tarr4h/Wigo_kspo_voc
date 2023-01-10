package com.kspo.voc.program.setting.controller;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.program.common.util.VocUtils;
import com.kspo.voc.program.setting.model.*;
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
        List<VocPrcdBasVo> list = service.selectAvailablePrcdBasList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @PostMapping(value = "insertMgmtPrcd")
    public @ResponseBody Object insertMgmtPrcd(@RequestBody EzMap param) throws EgovBizException {
        return service.insertMgmtPrcd(param);
    }

    @PostMapping(value = "selectMgmtPrcdGrid")
    public @ResponseBody Object selectMgmtPrcdGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocMgmtPrcdVo> list = service.selectMgmtPrcdList(param);
        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(convertList, page);
    }

    @PostMapping(value = "selectMgmtTaskGrid")
    public @ResponseBody Object selectMgmtTaskGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocMgmtTaskVo> list = service.selectMgmtTaskList(param);
        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(convertList, page);
    }

    @PostMapping(value = "selectTaskBasListGrid")
    public @ResponseBody Object selectTaskBasListGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocTaskBasVo> list = service.selectAvailableTaskBasList(param);
        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(convertList, page);
    }

    @PostMapping(value = "selectActvBasListGrid")
    public @ResponseBody Object selectActvBasListGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocActvBasVo> list = service.selectAvailableActvBasList(param);
        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(convertList, page);
    }

    @PostMapping(value = "insertMgmtActv")
    public @ResponseBody Object insertMgmtActv(@RequestBody EzMap param){
        return service.insertMgmtActv(param);
    }

    @PostMapping(value = "selectMgmtActvGrid")
    public @ResponseBody Object selectMgmtActvGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocMgmtTaskVo> list = service.selectMgmtActvList(param);
        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(convertList, page);
    }

    @PostMapping(value = "insertMgmtTask")
    public @ResponseBody Object insertMgmtTask(@RequestBody EzMap param){
        return service.insertMgmtTask(param);
    }

    @PostMapping(value = "deleteMgmtActvList")
    public @ResponseBody Object deleteMgmtActvList(@RequestBody EzMap param){
        return service.deleteMgmtActvList(param);
    }

    @PostMapping(value = "deleteMgmtTaskList")
    public @ResponseBody Object deleteMgmtTaskList(@RequestBody EzMap param){
        return service.deleteMgmtTaskList(param);
    }

    @PostMapping(value = "deleteMgmtPrcdList")
    public @ResponseBody Object deleteMgmtPrcdList(@RequestBody EzMap param){
        return service.deleteMgmtPrcdList(param);
    }

    @PostMapping(value = "updateMgmtPrcd")
    public @ResponseBody Object updateMgmtPrcd(@RequestBody EzMap param){
        return service.updateMgmtList(param, VocMgmtPrcdVo.class);
    }

    @PostMapping(value = "updateMgmtTask")
    public @ResponseBody Object updateMgmtTask(@RequestBody EzMap param){
        return service.updateMgmtList(param, VocMgmtTaskVo.class);
    }

    @PostMapping(value = "updateMgmtActv")
    public @ResponseBody Object updateMgmtActv(@RequestBody EzMap param){
        return service.updateMgmtList(param, VocMgmtActvVo.class);
    }

    @GetMapping(value = { "openAddModal/{pageNm}"})
    public String openAddModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws EgovBizException {
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
}
