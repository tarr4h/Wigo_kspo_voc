package com.kspo.voc.program.setting.controller;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
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
import com.kspo.voc.program.common.util.VocUtils;
import com.kspo.voc.program.setting.model.VocMgmtCdVo;
import com.kspo.voc.program.setting.service.VocMgmtCdService;

@Controller
@RequestMapping({"vocMgmtCd", "{menuId}/vocMgmtCd"})
public class VocMgmtCdController {

    @Autowired
    VocMgmtCdService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.voc") + "program/setting/mgmt_cd_bas/vocMgmtCdSetting";
    }

    @PostMapping(value = {"vocMgmtCdTree"})
    public @ResponseBody Object vocMgmtCdTree(@RequestBody EzMap param) throws EgovBizException{
        return service.vocMgmtCdTree(param);
    }

    @PostMapping(value = {"vocMgmtCdGrid"})
    public @ResponseBody Object vocMcMappingRows(@RequestBody EzMap param) throws EgovBizException {
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocMgmtCdVo> list = service.vocMgmtCdList(param);
        List<Map<String, Object>> convertList = Utilities.beanToMap(list);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(convertList, page);
    }

    @GetMapping(value = "vocMgmtCd")
    public @ResponseBody Object vocMgmtCd(@RequestParam Map<String, Object> param) {
        EzMap map = new EzMap(param);
        return service.vocMgmtCd(map);
    }

    @GetMapping(value = "selectComnCdList", produces = "application/text;charset=utf-8")
    public @ResponseBody Object selectComnCdList(@RequestParam Map<String, Object> param) throws EgovBizException {
        List<VocMgmtCdVo> existCdList = service.vocMgmtCdList(param);
        param.put("existCd", existCdList);
        return VocUtils.selectComnCdOptionList(param);
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
    public @ResponseBody Object delete(@RequestBody List<VocMgmtCdVo> list){
        return service.delete(list);
    }

    @GetMapping(value = { "openModal/{pageNm}"})
    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws EgovBizException {
        model.addAttribute("param", param);

        return Utilities.getProperty("tiles.voc.blank") + "program/setting/mgmt_cd_bas/" + pageNm;
    }
}
