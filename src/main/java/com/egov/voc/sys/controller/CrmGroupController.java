package com.egov.voc.sys.controller;


import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.*;
import com.egov.voc.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * com.wigo.crm.common.controller - GroupController.java
 * </pre>
 *
 * @ClassName : GroupController
 * @Description : 그룹 컨트롤러
 * @author : 김성태
 * @date : 2021. 1. 8.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

@Controller
@RequestMapping(value = { "group", "{menuCd}/group" })
public class CrmGroupController {
	@Autowired
	CrmGrpBaseService groupService;

	@Autowired
	CrmGrpUserRelService groupUserService;

	@Autowired
	CrmGrpMenuRelService groupMenuService;
	
	@Autowired
	CrmGrpOrgRelService groupOrgService;
	
	@Autowired
	CrmGrpEmpRelService groupEmpService;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm") + "sys/groupList";
	}

	@GetMapping(value = { "groupPopup" })
	public String groupPopup(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/groupPopup";
	}

	@GetMapping(value = { "add" })
	public String add(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/groupAdd";
	}

	@GetMapping(value = { "groupMenuPopup" })
	public String groupMenuPopup(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/groupMenuList";
	}

	@GetMapping(value = { "groupUserPopup" })
	public String groupUserPopup(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.crm.blank") + "sys/groupUserList";
	}

	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {

		EzPaginationInfo page = param.getPaginationInfo();
		List<EzMap> list = groupService.getList(param);
		page.setTotalRecordCount(groupService.getListCount(param));
		return Utilities.getGridData(list, page);
	}

	@PostMapping(value = { "getGroupUserList" })
	public @ResponseBody Object getGroupUserList(@RequestBody EzMap param) throws Exception {

		EzPaginationInfo page = param.getPaginationInfo();
		List<CrmGrpUserRelVo> list = groupService.getGroupUserList(param);
		page.setTotalRecordCount(groupService.getGroupUserListCount(param));
		return Utilities.getGridData(list, page);
	}

	@PostMapping(value = { "getGroupMenuList" })
	public @ResponseBody Object getGroupMenuList(@RequestBody EzMap param) throws Exception {

		EzPaginationInfo page = param.getPaginationInfo();
		List<CrmGrpMenuRelVo> list = groupService.getGroupMenuList(param);
		page.setTotalRecordCount(groupService.getGroupMenuListCount(param));
		return Utilities.getGridData(list, page);
	}
	
	@PostMapping(value = { "getGroupOrgList" })
	public @ResponseBody Object getGroupOrgList(@RequestBody EzMap param) throws Exception {

		EzPaginationInfo page = param.getPaginationInfo();
		List<CrmGrpMenuRelVo> list = groupService.getGroupOrgList(param);
		page.setTotalRecordCount(groupService.getGroupOrgListCount(param));
		return Utilities.getGridData(list, page);
	}
	
	@PostMapping(value = { "getGroupEmpList" })
	public @ResponseBody Object getGroupEmpList(@RequestBody EzMap param) throws Exception {

		EzPaginationInfo page = param.getPaginationInfo();
		List<CrmGrpMenuRelVo> list = groupService.getGroupEmpList(param);
		page.setTotalRecordCount(groupService.getGroupEmpListCount(param));
		return Utilities.getGridData(list, page);
	}
	

	@PostMapping(value = { "getGroup" })
	public @ResponseBody Object getGroup(@RequestBody EzMap param) throws Exception {
		EzMap group = groupService.get(param);
		return group;
	}

	@PostMapping(value = { "getGroupCheckList" })
	public @ResponseBody Object getGroupCheckList(@RequestBody EzMap param) throws Exception {
//	public @ResponseBody Object getGroupCheckList(@RequestParam Map<String, Object> rparam) throws Exception {
		return Utilities.getGridData(groupService.getGroupCheckList(param));
	}

	@PostMapping(value = { "addGroup" })
	public @ResponseBody Object addGroup(@RequestBody CrmGrpBaseVo param) throws Exception {
		param.setStat("c");
		return groupService.save(param);
	}

	@PostMapping(value = { "editGroup" })
	public @ResponseBody Object editGroup(@RequestBody CrmGrpBaseVo param) throws Exception {
		param.setStat("u");
		return groupService.save(param);
	}

	@PostMapping(value = { "removeGroupList" })
	public @ResponseBody Object removeGroupList(@RequestBody List<CrmGrpBaseVo> param) throws Exception {
		return groupService.deleteList(param);
	}

	@PostMapping(value = { "removeGroup" })
	public @ResponseBody Object removeGroup(@RequestBody CrmGrpBaseVo param) throws Exception {
		return groupService.delete(param);
	}

	@PostMapping(value = { "saveGroup" })
	public @ResponseBody Object saveGroup(@RequestBody CrmGrpBaseVo param) throws Exception {
		return groupService.save(param);
	}

	@PostMapping(value = { "save" })
	public @ResponseBody Object save(@RequestBody List<CrmGrpBaseVo> param) throws Exception {
		return groupService.saveList(param);
	}

	@PostMapping(value = { "addGroupMenu" })
	public @ResponseBody Object addGroupMenu(@RequestBody CrmGrpMenuRelVo param) throws Exception {
		param.setStat("c");
		return groupMenuService.save(param);
	}

	@PostMapping(value = { "addGroupMenuList" })
	public @ResponseBody Object addGroupMenuList(@RequestBody List<CrmGrpMenuRelVo> list) throws Exception {
		return groupMenuService.insertList(list);
	}
	@PostMapping(value = { "saveGroupOrgList" })
	public @ResponseBody Object saveGroupOrgList(@RequestBody List<CrmGrpOrgRelVo> list) throws Exception {
		return groupOrgService.insertList(list);
	}
	@PostMapping(value = { "saveGroupEmpList" })
	public @ResponseBody Object saveGroupEmpList(@RequestBody List<CrmGrpEmpRelVo> list) throws Exception {
		return groupEmpService.insertList(list);
	}

	@PostMapping(value = { "removeGroupMenu" })
	public @ResponseBody Object removeGroupMenu(@RequestBody CrmGrpMenuRelVo param) throws Exception {
		return groupMenuService.delete(param);
	}

	@PostMapping(value = { "removeGroupMenuList" })
	public @ResponseBody Object removeGroupMenuList(@RequestBody List<CrmGrpMenuRelVo> list) throws Exception {
		return groupMenuService.deleteList(list);
	}
	@PostMapping(value = { "removeGroupOrgList" })
	public @ResponseBody Object removeGroupOrgList(@RequestBody List<CrmGrpOrgRelVo> list) throws Exception {
		return groupOrgService.deleteList(list);
	}
	
	@PostMapping(value = { "removeGroupEmpList" })
	public @ResponseBody Object removeGroupEmpList(@RequestBody List<CrmGrpEmpRelVo> list) throws Exception {
		return groupEmpService.deleteList(list);
	}

	@PostMapping(value = { "setMenuGroup" })
	public @ResponseBody Object setMenuGroup(@RequestBody List<CrmGrpMenuRelVo> list,
			@RequestParam("menuCd") String menuCd) throws Exception {
		return groupMenuService.saveMenuGroup(menuCd, list);
	}

	@PostMapping(value = { "saveGroupMenu" })
	public @ResponseBody Object saveGroupMenu(@RequestBody CrmGrpMenuRelVo param) throws Exception {
		return groupMenuService.save(param);
	}

	@PostMapping(value = { "saveGroupMenuList" })
	public @ResponseBody Object saveGroupMenuList(@RequestBody List<CrmGrpMenuRelVo> param) throws Exception {
		return groupMenuService.saveList(param);
	}

	@PostMapping(value = { "addGroupUser" })
	public @ResponseBody Object addGroupUser(@RequestBody CrmGrpUserRelVo param) throws Exception {
		param.setStat("c");
		return groupUserService.save(param);
	}

	@PostMapping(value = { "addGroupUserList" })
	public @ResponseBody Object addGroupUserList(@RequestBody List<CrmGrpUserRelVo> list) throws Exception {
		return groupUserService.insertList(list);
	}

	@PostMapping(value = { "removeGroupUser" })
	public @ResponseBody Object removeGroupUser(@RequestBody CrmGrpUserRelVo param) throws Exception {
		return groupUserService.delete(param);
	}

	@PostMapping(value = { "removeGroupUserList" })
	public @ResponseBody Object removeGroupUserList(@RequestBody List<CrmGrpUserRelVo> list) throws Exception {
		return groupUserService.deleteList(list);
	}

	@PostMapping(value = { "setUserGroup" })
	public @ResponseBody Object setUserGroup(@RequestBody List<CrmGrpUserRelVo> list,
			@RequestParam("userCd") String userCd) throws Exception {
		return groupUserService.saveUserGroup(userCd, list);
	}

	@PostMapping(value = { "setGroupUser" })
	public @ResponseBody Object setGroupUser(@RequestBody List<CrmGrpUserRelVo> list,
			@RequestParam("userCd") String userCd) throws Exception {
		return groupUserService.saveGroupUser(userCd, list);
	}

}
