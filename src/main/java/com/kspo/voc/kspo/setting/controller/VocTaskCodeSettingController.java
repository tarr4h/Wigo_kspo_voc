package com.kspo.voc.kspo.setting.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.kspo.common.util.VocUtils;
import com.kspo.voc.kspo.setting.service.VocTaskCodeSettingService;

@Controller
@RequestMapping({"vocTaskCodeSetting", "{menuId}/vocTaskCodeSetting"})
public class VocTaskCodeSettingController {

    @Autowired
    VocTaskCodeSettingService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.crm") + "voc/setting/task_code/vocTaskCodeSetting";
    }

    @PostMapping(value = "selectTaskCodeList")
    public @ResponseBody Object selectTaskCodeList(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<EzMap> list = service.selectTaskCodeList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @PostMapping(value = "insertCode")
    public @ResponseBody Object insertCode(@RequestBody Map<String, Object> param) throws Exception {
        return service.insertCode(param);
    }

    @PostMapping(value = "saveRows")
    public @ResponseBody Object saveRows(@RequestBody Map<String, Object> param) throws Exception {
        return service.update(param);
    }

    @PostMapping(value = "deleteRows")
    public @ResponseBody Object deleteRows(@RequestBody Map<String, Object> param) throws Exception {
        return service.delete(param);
    }

    @PostMapping(value = "chngTaskDuty")
    public @ResponseBody Object chngTaskDuty(@RequestBody Map<String, Object> param){
        return service.chngTaskDuty(param);
    }

    @GetMapping(value = { "openModal/{pageNm}"})
    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws Exception {
        model.addAttribute("param", param);

        return Utilities.getProperty("tiles.crm.blank") + "voc/setting/task_code/" + pageNm;
    }

    @GetMapping(value = { "openComnModal/{pageNm}"})
    public String openComnModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws Exception {
        model.addAttribute("param", param);

        return Utilities.getProperty("tiles.crm.blank") + "voc/common/" + pageNm;
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

    @PostMapping(value = "appliedPrcdGrid")
    public @ResponseBody Object appliedPrcdGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<EzMap> list = service.selectAppliedPrcd(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @PostMapping(value = "availablePrcdGrid")
    public @ResponseBody Object availablePrcdGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<EzMap> list = service.selectAvailablePrcdList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @PostMapping(value = "updateAutoApplyPrcd")
    public @ResponseBody Object updateAutoApplyPrcd(@RequestBody EzMap param){
        return service.updateAutoApplyPrcd(param);
    }

}
