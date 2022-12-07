package com.egov.voc.kspo.setting.controller;


import com.egov.base.common.model.EzMap;
import com.egov.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.stnd.ManageCodeCategory;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.setting.model.*;
import com.egov.voc.kspo.setting.service.VocRegProcedureSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping({"vocRegProcedureSetting", "{menuCd}/vocRegProcedureSetting"})
public class VocRegProcedureSettingController {

    @Autowired
    VocRegProcedureSettingService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);
        return Utilities.getProperty("tiles.crm") + "voc/setting/procedure_setting/vocRegProcedureSetting";
    }

    @PostMapping(value = {"vocRegistrationManagementCodeTree"})
    public @ResponseBody Object vocRegistrationManagementCodeTree(@RequestBody EzMap param) throws Exception{
        // single
        ManageCodeCategory.setComnCdTreeMap(param, ManageCodeCategory.CHANNEL);
        // multiple example
//        ManageCodeCategoryEnum.setComnCdListTreeMap(param, Arrays.asList(ManageCodeCategoryEnum.REGISTRATION, ManageCodeCategoryEnum.RECEIPT));
        return service.vocManagementCodeTree(param);
    }

    @GetMapping(value = { "openModal/{pageNm}"})
    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws Exception {
        model.addAttribute("param", param);

        return Utilities.getProperty("tiles.crm.blank") + "voc/setting/procedure_setting/" + pageNm;
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

    /**
     * voc_procedure_bas에서 등록 가능한 목록 조회
     * @param param
     * @return
     */
    @GetMapping(value = "selectPrcdBasList")
    public @ResponseBody Object selectAvailablePrcdBasList(@RequestParam Map<String, Object> param){
        List<VocProcedureCodeVo> list = service.selectAvailablePrcdBasList(param);
        list.removeIf(value -> value.getRegUseYn().equals("N"));
        param.put("list", list);
        param.put("target", "reg");
        return param;
    }

    @PostMapping(value = "insertProcedure")
    public @ResponseBody Object insertProcedure(@RequestBody EzMap param){
        return service.insertProcedure(param);
    }

    @PostMapping(value = "deleteProcedure")
    public @ResponseBody Object deleteProcedure(@RequestBody EzMap param){
        return service.deleteProcedure(param);
    }

    @PostMapping(value = "insertTask")
    public @ResponseBody Object insertTask(@RequestBody EzMap param){
        return service.insertTask(param);
    }

    @PostMapping(value = "deleteTask")
    public @ResponseBody Object deleteTask(@RequestBody EzMap param){
        return service.deleteTask(param);
    }

    @PostMapping(value = {"selectProcedureGrid"})
    public @ResponseBody Object selectProcedureGrid(@RequestBody EzMap param) throws Exception {
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocProcedureVo> list = service.selectProcedureList(param);
        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(convertList, page);
    }

    @PostMapping(value = "selectTaskGrid")
    public @ResponseBody Object selectTaskGrid(@RequestBody EzMap param) throws Exception {
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocTaskVo> list = service.selectTaskList(param);
        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(convertList, page);
    }

    @PostMapping(value = "selectActivityGrid")
    public @ResponseBody Object selectActivityGrid(@RequestBody EzMap param) throws Exception {
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocActivityVo> list = service.selectActivityList(param);
        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(convertList, page);
    }

    @PostMapping(value = "selectActivityFuncBasGrid")
    public @ResponseBody Object selectActivityFuncBasGrid(@RequestBody EzMap param) throws Exception{
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocActivityVo> list = service.selectActivityFuncBasList(param);
//        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @PostMapping(value = "selctDutyOrgGrid")
    public @ResponseBody Object selectDutyOrgGrid(@RequestBody EzMap param) throws Exception{
        EzPaginationInfo page = param.getPaginationInfo();
        List<EzMap> list = service.selectRegDutyOrgList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @PostMapping(value = "selectTaskBasSearchGrid")
    public @ResponseBody Object selectTaskBasSearchGrid(@RequestBody EzMap param) throws Exception{
        EzPaginationInfo page = param.getPaginationInfo();
        List<EzMap> list = service.selectTaskBasList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    @PostMapping(value = "insertDirOrg")
    public @ResponseBody Object insertDirOrg(@RequestBody EzMap param) {
        return service.insertDirOrg(param);
    }

    @GetMapping(value = "selectDirCd")
    public @ResponseBody Object selectDirCd(@RequestParam Map<String, Object> param){
        return service.selectDirCd(param);
    }

    @GetMapping(value = "selectDutyOrgList")
    public @ResponseBody Object selectRegDutyOrgList(@RequestParam Map<String, Object> param){
        return service.selectRegDutyOrgList(param);
    }

    @PostMapping(value = "deleteDirOrg")
    public @ResponseBody Object deleteDirOrg(@RequestBody EzMap param){
        return service.deleteDirOrg(param);
    }

    @PostMapping(value = "updateDirOrg")
    public @ResponseBody Object updateDirOrg(@RequestBody EzMap param){
        return service.updateDirOrg(param);
    }

    @GetMapping(value = "selectPrcdBas")
    public @ResponseBody Object selectPrcdBas(@RequestParam Map<String, Object> param){
        return service.selectPrcdBas(param);
    }

    @PostMapping(value = "insertActivity")
    public @ResponseBody Object insertActivity(@RequestBody EzMap param) {
        return service.insertActivity(param);
    }

    @PostMapping(value = "deleteActivity")
    public @ResponseBody Object deleteActivity(@RequestBody EzMap param){
        return service.deleteActivity(param);
    }

    @GetMapping(value = "validateRequiredPrcd")
    public @ResponseBody Object validateRequiredPrcd(@RequestParam Map<String, Object> param){
        param.put("target", "reg");
        return service.validateRequiredPrcd(param);
    }
}
