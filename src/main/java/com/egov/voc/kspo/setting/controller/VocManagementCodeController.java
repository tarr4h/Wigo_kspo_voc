package com.egov.voc.kspo.setting.controller;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.kspo.common.util.VocUtils;
import com.egov.voc.kspo.setting.model.VocManagementCodeVo;
import com.egov.voc.kspo.setting.service.VocManagementCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping({"vocManagementCode", "{menuCd}/vocManagementCode"})
public class VocManagementCodeController {

    @Autowired
    VocManagementCodeService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.crm") + "voc/setting/management_code/vocManagementCodeSetting";
    }

    @PostMapping(value = {"vocManagementCodeTree"})
    public @ResponseBody Object vocManagementCodeTree(@RequestBody EzMap param) throws Exception{
        return service.vocManagementCodeTree(param);
    }

    @PostMapping(value = {"vocManagementCodeGrid"})
    public @ResponseBody Object vocMcMappingRows(@RequestBody EzMap param) throws Exception {
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocManagementCodeVo> list = service.vocManagementCodeList(param);
        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(convertList, page);
    }

    @GetMapping(value = "vocManagementCode")
    public @ResponseBody Object vocManagementCode(@RequestParam Map<String, Object> param) {
        EzMap map = new EzMap(param);
        return service.vocManagementCode(map);
    }

    @GetMapping(value = "selectComnCdList", produces = "application/text;charset=utf-8")
    public @ResponseBody Object selectComnCdList(@RequestParam Map<String, Object> param){
        List<VocManagementCodeVo> existCdList = service.vocManagementCodeList(param);
        param.put("existCd", existCdList);
        return VocUtils.selectComnCdList(param);
    }

    @PostMapping(value = "insert")
    public @ResponseBody Object insert(@RequestBody EzMap param){
        return service.insert(param);
    }

    @PostMapping(value = "update")
    public @ResponseBody Object update(@RequestBody EzMap param){
        return service.update(param);
    }

    @PostMapping(value = "delete")
    public @ResponseBody Object delete(@RequestBody List<VocManagementCodeVo> list){
        return service.delete(list);
    }

    @GetMapping(value = { "openModal/{pageNm}"})
    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws Exception {
        model.addAttribute("param", param);

        return Utilities.getProperty("tiles.crm.blank") + "voc/setting/management_code/" + pageNm;
    }
}
