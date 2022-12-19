package com.kspo.voc.kspo.common.util;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.kspo.common.service.VocComnService;
import com.kspo.voc.sys.model.ComnCdBaseVo;
import com.kspo.voc.sys.model.EmpBaseVo;
import com.kspo.voc.sys.model.OrgBaseVo;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unchecked")
@Component
@Slf4j
public class VocUtils extends Utilities {

    private static VocComnService comnService;

    @Autowired
    VocComnService cmService;



    @PostConstruct
    public void init(){
        try {
			super.init();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
		}
        comnService = this.cmService;
    }

    public static int parseIntObject(Object obj){
        return Integer.parseInt(String.valueOf(obj));
    }

//    public static void setPagination(EzMap param, EzPaginationInfo page){
//        int currentPage = page.getCurrentPageNo();
//        int limit = page.getRecordCountPerPage();
//        int pageStart = (currentPage - 1) * limit + 1;
//        int pageEnd = currentPage * limit;
//
//        param.put("pageStart", pageStart);
//        param.put("pageEnd", pageEnd);
//    }

    public static void setOrgInfoToMap(EzMap org) {
        String orgId = (String) org.get("orgId");
        OrgBaseVo orgVo = comnService.selectOrg(orgId);

        EzMap orgMap = (EzMap) Utilities.beanToMap(orgVo);
        org.putAll(orgMap);
    }

    public static OrgBaseVo selectOrg(String orgId){
        return comnService.selectOrg(orgId);
    }

    public static String getOrgNm(String orgId) {
        OrgBaseVo org = comnService.selectOrg(orgId);
        if(org == null){
            return "-";
        } else {
            return org.getOrgNm();
        }
    }

    public static String getEmpNm(String empId) {
        EmpBaseVo emp = comnService.selectEmp(empId);
        if(emp == null){
            return "-";
        } else {
            return emp.getEmpNm();
        }
    }

    public static EzMap getEmpGrid(EzMap param) {
        EzPaginationInfo page = param.getPaginationInfo();
        List<EmpBaseVo> list = comnService.selectEmpList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    public static Object getOrgTree(Map<String, Object> param){
        return AbstractTreeVo.makeHierarchy(comnService.selectOrgList(param));
    }

    public static EzMap getOrgGrid(EzMap param){
        EzPaginationInfo page = param.getPaginationInfo();
        List<OrgBaseVo> list = comnService.selectOrgList(param);
        page.setTotalRecordCount(list.size());
        return Utilities.getGridData(list, page);
    }

    public static List<OrgBaseVo> selectOrgList(Map<String, Object> param){
        return comnService.selectOrgList(param);
    }

    public static Object selectComnCdList(Map<String, Object> param){
        return comnService.selectComnCdList(param);
    }

    public static Object selectComnCdOptionList(Map<String, Object> param){
        StringBuilder sb = new StringBuilder();
        List<ComnCdBaseVo> comnCdList = comnService.selectComnCdList(param);
        for(ComnCdBaseVo comnCd : comnCdList){
            String opt = "<option value='" + comnCd.getComnCd() + "' data-top-comn-cd='" + comnCd.getTopComnCd()  + "'>" + comnCd.getComnCdNm() + "</option>";
            sb.append(opt);
        }
        return sb;
    };

    public static Map<String, Object> setCodeSettingParam(Object obj){
        Map<String, Object> param = (Map<String, Object>) obj;
        List<Map<String, Object>> formArr = (List<Map<String, Object>>) param.get("formArr");
        param = VocUtils.formSerializeArrayToMap(formArr);
        param = VocUtils.sumUpDeadline(param);

        return param;
    }

    public static Map<String, Object> formSerializeArrayToMap(List<Map<String, Object>> list){
        Map<String ,Object> returnMap = new HashMap<>();

        for(Map<String ,Object> map : list){
            log.debug("map = {}", map);
            String key = (String) map.get("name");
            Object value = map.get("value");
            returnMap.put(key, value);
        }

        log.debug("returnMap = {}", returnMap);

        return returnMap;
    }

    public static Map<String, Object> sumUpDeadline(Map<String, Object> param){
        int date = VocUtils.parseIntObject(param.get("deadlineDate"));
        int hour = VocUtils.parseIntObject(param.get("deadlineHour"));
        int minute = VocUtils.parseIntObject(param.get("deadlineMinute"));

        int deadline = (date * 24 * 60) + (hour * 60) + minute;
        log.debug("deadline = {}", deadline);
        param.put("deadline", deadline);

        return param;
    }

    public static String convertDeadline(int deadline){
        if(deadline == 0){
            return "-";
        }
        int date = deadline / 24 / 60;
        int hour = (deadline - (date * 24 * 60)) / 60;
        int minute = (deadline - (date * 24 * 60) - (hour * 60));

        StringBuilder sb = new StringBuilder();
        if(date > 0)
            sb.append(date).append("일").append(" ");
        if(hour > 0)
            sb.append(hour).append("시간").append(" ");
        if(minute > 0){
            sb.append(minute).append("분");
        }
        return sb.toString();
    }

    public static Date setDefaultDeadlineDate(int minute){
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, minute);

        return cal.getTime();
    }

}