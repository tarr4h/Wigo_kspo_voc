package com.egov.voc.kspo.setting.controller;


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

import com.egov.base.common.model.EzMap;
import com.egov.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.setting.model.VocProcedureCodeVo;
import com.egov.voc.kspo.setting.service.VocProcedureCodeSettingService;

@Controller
@RequestMapping({"vocProcedureCodeSetting", "{menuCd}/vocProcedureCodeSetting"})
public class VocProcedureCodeSettingController {

    @Autowired
    VocProcedureCodeSettingService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.crm") + "voc/setting/procedure_code/vocProcedureCodeSetting";
    }

    @PostMapping(value = "selectProcedureCodeGrid")
    public @ResponseBody Object selectProcedureCodeGrid(@RequestBody EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<EzMap> list = service.selectProcedureCodeList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @GetMapping(value = "selectProcedureCodeList")
    public @ResponseBody Object selectProcedureCodeList(@RequestParam Map<String, Object> param){
        return service.selectProcedureCodeList(param);
    }

    @GetMapping(value = "selectComnCdList", produces = "application/text;charset=utf-8")
    public @ResponseBody Object selectComnCdList(@RequestParam Map<String, Object> param){
        List<VocProcedureCodeVo> list = service.selectProcedureCodeList(param);
        param.put("existCd", list);
        return VocUtils.selectComnCdOptionList(param);
    }

    @PostMapping(value = "insert")
    public @ResponseBody Object insert(@RequestBody Map<String, Object> param) throws Exception {
        return service.insert(param);
    }

    @PostMapping(value = "update")
    public @ResponseBody Object update(@RequestBody Map<String, Object> param) throws Exception {
        return service.update(param);
    }

    @PostMapping(value = "delete")
    public @ResponseBody Object delete(@RequestBody Map<String, Object> param) throws Exception {
        return service.delete(param);
    }

    @PostMapping(value = "chngProcedureDuty")
    public @ResponseBody Object chngProcedureDuty(@RequestBody Map<String, Object> param){
        return service.chngProcedureDuty(param);
    }

    @PostMapping(value = "updateDeadline")
    public @ResponseBody Object updateDeadline(@RequestBody EzMap param){
        return service.updateDeadline(param);
    }

    @GetMapping(value = { "openModal/{pageNm}"})
    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws Exception {
        model.addAttribute("param", param);
        return Utilities.getProperty("tiles.crm.blank") + "voc/setting/procedure_code/" + pageNm;
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
}
