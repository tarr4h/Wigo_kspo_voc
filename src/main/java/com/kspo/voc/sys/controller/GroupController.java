package com.kspo.voc.sys.controller;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.GrpBaseVo;
import com.kspo.voc.sys.model.GrpEmpRelVo;
import com.kspo.voc.sys.model.GrpMenuRelVo;
import com.kspo.voc.sys.model.GrpOrgRelVo;
import com.kspo.voc.sys.model.GrpUserRelVo;
import com.kspo.voc.sys.model.IpRelVo;
import com.kspo.voc.sys.service.GrpBaseService;
import com.kspo.voc.sys.service.GrpEmpRelService;
import com.kspo.voc.sys.service.GrpMenuRelService;
import com.kspo.voc.sys.service.GrpOrgRelService;
import com.kspo.voc.sys.service.GrpUserRelService;
import com.kspo.voc.sys.service.IpRelService;

/**
 * 
 * <pre>
 * com.kspo.base.common.controller - GroupController.java
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
@RequestMapping(value = { "group", "{menuId}/group" })
public class GroupController {
	@Autowired
	GrpBaseService groupService;

	@Autowired
	GrpUserRelService groupUserService;

	@Autowired
	GrpMenuRelService groupMenuService;

	@Autowired
	GrpOrgRelService groupOrgService;

	@Autowired
	GrpEmpRelService groupEmpService;

	@Autowired
	IpRelService ipService;

	@GetMapping(value = { "", "index" })
	public String init(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc") + "sys/groupList";
	}

	@GetMapping(value = { "groupPopup" })
	public String groupPopup(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/groupPopup";
	}

	@GetMapping(value = { "add" })
	public String add(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/groupAdd";
	}

	@GetMapping(value = { "groupMenuPopup" })
	public String groupMenuPopup(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/groupMenuList";
	}

	@GetMapping(value = { "groupUserPopup" })
	public String groupUserPopup(@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/groupUserList";
	}

	@GetMapping(value = { "groupIpPopup/{refId}/{refTpCd}" })
	public String groupIpPopup(@PathVariable("refId") String refId, @PathVariable("refTpCd") String refTpCd,
		@ModelAttribute	@RequestParam Map<String, Object> param, ModelMap model) throws EgovBizException {
		
		param.put("refId", refId);
		param.put("refTpCd", refTpCd);
		model.addAllAttributes(groupService.getGroupIpInfo(param));
		model.addAllAttributes(param);
		return Utilities.getProperty("tiles.voc.blank") + "sys/groupIpList";
	}

	@PostMapping(value = { "getList" })
	public @ResponseBody Object getList(@RequestBody EzMap param) throws EgovBizException {

		EzPaginationInfo page = param.getPaginationInfo();
		List<EzMap> list = groupService.getList(param);
		page.setTotalRecordCount(groupService.getListCount(param));
		return Utilities.getGridData(list, page);
	}

	@PostMapping(value = { "getGroupUserList" })
	public @ResponseBody Object getGroupUserList(@RequestBody EzMap param) throws EgovBizException {

		EzPaginationInfo page = param.getPaginationInfo();
		List<GrpUserRelVo> list = groupService.getGroupUserList(param);
		page.setTotalRecordCount(groupService.getGroupUserListCount(param));
		return Utilities.getGridData(list, page);
	}

	@PostMapping(value = { "getGroupMenuList" })
	public @ResponseBody Object getGroupMenuList(@RequestBody EzMap param) throws EgovBizException {

		EzPaginationInfo page = param.getPaginationInfo();
		List<GrpMenuRelVo> list = groupService.getGroupMenuList(param);
		page.setTotalRecordCount(groupService.getGroupMenuListCount(param));
		return Utilities.getGridData(list, page);
	}

	@PostMapping(value = { "getGroupOrgList" })
	public @ResponseBody Object getGroupOrgList(@RequestBody EzMap param) throws EgovBizException {

		EzPaginationInfo page = param.getPaginationInfo();
		List<GrpMenuRelVo> list = groupService.getGroupOrgList(param);
		page.setTotalRecordCount(groupService.getGroupOrgListCount(param));
		return Utilities.getGridData(list, page);
	}

	@PostMapping(value = { "getGroupEmpList" })
	public @ResponseBody Object getGroupEmpList(@RequestBody EzMap param) throws EgovBizException {

		EzPaginationInfo page = param.getPaginationInfo();
		List<GrpMenuRelVo> list = groupService.getGroupEmpList(param);
		page.setTotalRecordCount(groupService.getGroupEmpListCount(param));
		return Utilities.getGridData(list, page);
	}

	@PostMapping(value = { "getGroupIpList" })
	public @ResponseBody Object getGroupIpList(@RequestBody EzMap param) throws EgovBizException {
		param.setInt("recordCountPerPage", 10000);
		List<GrpMenuRelVo> list = ipService.getList(param);
		return Utilities.getGridData(list);
	}

	@PostMapping(value = { "getGroup" })
	public @ResponseBody Object getGroup(@RequestBody EzMap param) throws EgovBizException {
		EzMap group = groupService.get(param);
		return group;
	}

	@PostMapping(value = { "getGroupCheckList" })
	public @ResponseBody Object getGroupCheckList(@RequestBody EzMap param) throws EgovBizException {
//	public @ResponseBody Object getGroupCheckList(@RequestParam Map<String, Object> rparam) throws EgovBizException {
		return Utilities.getGridData(groupService.getGroupCheckList(param));
	}

	@PostMapping(value = { "addGroup" })
	public @ResponseBody Object addGroup(@RequestBody GrpBaseVo param) throws EgovBizException {
		param.setStat("c");
		return groupService.save(param);
	}

	@PostMapping(value = { "editGroup" })
	public @ResponseBody Object editGroup(@RequestBody GrpBaseVo param) throws EgovBizException {
		param.setStat("u");
		return groupService.save(param);
	}

	@PostMapping(value = { "removeGroupList" })
	public @ResponseBody Object removeGroupList(@RequestBody List<GrpBaseVo> param) throws EgovBizException {
		return groupService.deleteList(param);
	}

	@PostMapping(value = { "removeGroup" })
	public @ResponseBody Object removeGroup(@RequestBody GrpBaseVo param) throws EgovBizException {
		return groupService.delete(param);
	}

	@PostMapping(value = { "saveGroup" })
	public @ResponseBody Object saveGroup(@RequestBody GrpBaseVo param) throws EgovBizException {
		return groupService.save(param);
	}

	@PostMapping(value = { "saveGroupIpList" })
	public @ResponseBody Object saveGroupIpList(@RequestBody List<IpRelVo> list) throws EgovBizException {
		return ipService.saveList(list);
	}

	@PostMapping(value = { "save" })
	public @ResponseBody Object save(@RequestBody List<GrpBaseVo> param) throws EgovBizException {
		return groupService.saveList(param);
	}

	@PostMapping(value = { "addGroupMenu" })
	public @ResponseBody Object addGroupMenu(@RequestBody GrpMenuRelVo param) throws EgovBizException {
		param.setStat("c");
		return groupMenuService.save(param);
	}

	@PostMapping(value = { "addGroupMenuList" })
	public @ResponseBody Object addGroupMenuList(@RequestBody List<GrpMenuRelVo> list) throws EgovBizException {
		return groupMenuService.insertList(list);
	}

	@PostMapping(value = { "saveGroupOrgList" })
	public @ResponseBody Object saveGroupOrgList(@RequestBody List<GrpOrgRelVo> list) throws EgovBizException {
		return groupOrgService.insertList(list);
	}

	@PostMapping(value = { "saveGroupEmpList" })
	public @ResponseBody Object saveGroupEmpList(@RequestBody List<GrpEmpRelVo> list) throws EgovBizException {
		return groupEmpService.insertList(list);
	}

	@PostMapping(value = { "removeGroupMenu" })
	public @ResponseBody Object removeGroupMenu(@RequestBody GrpMenuRelVo param) throws EgovBizException {
		return groupMenuService.delete(param);
	}

	@PostMapping(value = { "removeGroupMenuList" })
	public @ResponseBody Object removeGroupMenuList(@RequestBody List<GrpMenuRelVo> list) throws EgovBizException {
		return groupMenuService.deleteList(list);
	}

	@PostMapping(value = { "removeGroupOrgList" })
	public @ResponseBody Object removeGroupOrgList(@RequestBody List<GrpOrgRelVo> list) throws EgovBizException {
		return groupOrgService.deleteList(list);
	}

	@PostMapping(value = { "removeGroupIpList" })
	public @ResponseBody Object removeGroupIpList(@RequestBody List<IpRelVo> list) throws EgovBizException {
		return ipService.deleteList(list);
	}

	@PostMapping(value = { "removeGroupEmpList" })
	public @ResponseBody Object removeGroupEmpList(@RequestBody List<GrpEmpRelVo> list) throws EgovBizException {
		return groupEmpService.deleteList(list);
	}

	@PostMapping(value = { "setMenuGroup" })
	public @ResponseBody Object setMenuGroup(@RequestBody List<GrpMenuRelVo> list,
			@RequestParam("menuId") String menuId) throws EgovBizException {
		return groupMenuService.saveMenuGroup(menuId, list);
	}

	@PostMapping(value = { "saveGroupMenu" })
	public @ResponseBody Object saveGroupMenu(@RequestBody GrpMenuRelVo param) throws EgovBizException {
		return groupMenuService.save(param);
	}

	@PostMapping(value = { "saveGroupMenuList" })
	public @ResponseBody Object saveGroupMenuList(@RequestBody List<GrpMenuRelVo> param) throws EgovBizException {
		return groupMenuService.saveList(param);
	}

	@PostMapping(value = { "addGroupUser" })
	public @ResponseBody Object addGroupUser(@RequestBody GrpUserRelVo param) throws EgovBizException {
		param.setStat("c");
		return groupUserService.save(param);
	}

	@PostMapping(value = { "addGroupUserList" })
	public @ResponseBody Object addGroupUserList(@RequestBody List<GrpUserRelVo> list) throws EgovBizException {
		return groupUserService.insertList(list);
	}

	@PostMapping(value = { "removeGroupUser" })
	public @ResponseBody Object removeGroupUser(@RequestBody GrpUserRelVo param) throws EgovBizException {
		return groupUserService.delete(param);
	}

	@PostMapping(value = { "removeGroupUserList" })
	public @ResponseBody Object removeGroupUserList(@RequestBody List<GrpUserRelVo> list) throws EgovBizException {
		return groupUserService.deleteList(list);
	}

	@PostMapping(value = { "setUserGroup" })
	public @ResponseBody Object setUserGroup(@RequestBody List<GrpUserRelVo> list,
			@RequestParam("userId") String userId) throws EgovBizException {
		return groupUserService.saveUserGroup(userId, list);
	}

	@PostMapping(value = { "setGroupUser" })
	public @ResponseBody Object setGroupUser(@RequestBody List<GrpUserRelVo> list,
			@RequestParam("userId") String userId) throws EgovBizException {
		return groupUserService.saveGroupUser(userId, list);
	}

}
