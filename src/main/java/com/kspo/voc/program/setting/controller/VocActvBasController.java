package com.kspo.voc.program.setting.controller;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.program.common.util.VocUtils;
import com.kspo.voc.program.setting.model.VocActvBasVo;
import com.kspo.voc.program.setting.service.VocActvBasService;
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
 * com.kspo.voc.program.setting.controller.VocActvBasController
 *  - VocActvBasController.java
 * </pre>
 *
 * @ClassName     : VocActvBasController
 * @description   :
 * @author        : tarr4h
 * @date          : 2023-01-03
 *
*/

@SuppressWarnings("unchecked")
@Controller
@Slf4j
@RequestMapping({"vocActvBas", "{menuId}/vocActvBas"})
public class VocActvBasController {

    @Autowired
    VocActvBasService service;

    @GetMapping(value = {"", "index"})
    public String init(@RequestParam Map<String, Object> param, Model model) {
        model.addAllAttributes(param);

        return Utilities.getProperty("tiles.voc") + "program/setting/actv_bas/vocActvBasSetting";
    }

    @PostMapping(value = "selectActvBasList")
    public @ResponseBody Object selectActvBasList(@RequestBody EzMap param) throws EgovBizException {
        EzPaginationInfo page = param.getPaginationInfo();
        List<VocActvBasVo> list = service.getList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    };

    @PostMapping(value = "insert")
    public @ResponseBody Object insert(@RequestBody EzMap param) throws EgovBizException {
        return service.insert(param);
    }

    @PostMapping(value = "update")
    public @ResponseBody Object update(@RequestBody EzMap param) throws EgovBizException {
        return service.update(param);
    }

    @PostMapping(value = "delete")
    public @ResponseBody Object delete(@RequestBody EzMap param) throws EgovBizException {
        return service.delete(param);
    }

    @GetMapping(value = "getFuncTp", produces = "application/text;charset=utf-8")
    public @ResponseBody Object getFuncTp(@RequestParam Map<String, Object> param) throws EgovBizException{
        return VocUtils.selectComnCdOptionList(param);
    }

    @GetMapping(value = { "openModal/{pageNm}"})
    public String openModal(@PathVariable String pageNm, @RequestParam Map<String, Object> param, Model model) throws EgovBizException {
        model.addAttribute("param", param);

        return Utilities.getProperty("tiles.voc.blank") + "program/setting/actv_bas/" + pageNm;
    }
}


