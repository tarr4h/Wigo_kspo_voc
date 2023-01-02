package com.kspo.voc.kspo.setting.controller;


import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.kspo.common.util.VocUtils;
import com.kspo.voc.kspo.setting.model.VocPrcdBasVo;
import com.kspo.voc.kspo.setting.service.VocPrcdCdBasService;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"vocPrcdCdBas", "{menuId}/vocPrcdCdBas"})
public class VocPrcdCdBasController {

    @Autowired
    VocPrcdCdBasService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.voc") + "voc/setting/prcd_bas/vocPrcdCdBasSetting";
    }

    @PostMapping(value = "selectPrcdCdGrid")
    public @ResponseBody Object selectPrcdCdGrid(@RequestBody EzMap param){
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
        List<VocPrcdBasVo> list = service.selectProcedureCodeList(param);
        param.put("existCd", list);
        return VocUtils.selectComnCdOptionList(param);
    }

    @PostMapping(value = "insert")
    public @ResponseBody Object insert(@RequestBody Map<String, Object> param) throws EgovBizException {
        return service.insert(param);
    }

    @PostMapping(value = "update")
    public @ResponseBody Object update(@RequestBody Map<String, Object> param) throws EgovBizException {
        return service.update(param);
    }

    @PostMapping(value = "delete")
    public @ResponseBody Object delete(@RequestBody Map<String, Object> param) throws EgovBizException {
        return service.delete(param);
    }

    @PostMapping(value = "chngPrcdDuty")
    public @ResponseBody Object chngPrcdDuty(@RequestBody Map<String, Object> param){
        return service.chngPrcdDuty(param);
    }

    @PostMapping(value = "updateDeadline")
    public @ResponseBody Object updateDeadline(@RequestBody EzMap param){
        return service.updateDeadline(param);
    }

    @GetMapping(value = { "openModal/{pageNm}"})
    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws EgovBizException {
        model.addAttribute("param", param);
        return Utilities.getProperty("tiles.voc.blank") + "voc/setting/prcd_bas/" + pageNm;
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
}
