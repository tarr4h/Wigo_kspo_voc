package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzAjaxException;
import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.ITreeVo;
import com.kspo.voc.comn.util.SessionUtil;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.GrpMenuRelDao;
import com.kspo.voc.sys.dao.MenuBaseDao;
import com.kspo.voc.sys.dao.UserWdgtDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.model.MenuVo;
import com.kspo.voc.sys.model.UserWdgtVo;

import java.util.ArrayList;
import java.util.List;

@Service("menuService")
public class MenuService extends AbstractVocService {
	@Autowired
	MenuBaseDao dao;

	@Autowired
	GrpMenuRelDao grpMenuDao;

	@Autowired
	UserWdgtDao wdgtDao;

	@Override
	public IVocDao getDao() {
		return dao;
	}

	@Override
	public int delete(Object param) throws Exception {
		int cnt = dao.selectChildrenCount(param);
		if (cnt > 0)
			throw new EzAjaxException("하부메뉴가 존재합니다.");
		grpMenuDao.deleteMenuId(param);
		wdgtDao.deleteMenuId(param);
		return super.delete(param);
	}

	public MenuVo getNewMenu(MenuVo param) throws Exception {

		String parentCd = param.getPrntsMenuId();
		MenuVo parent = null;
		MenuVo maxInfo = null;
		if (Utilities.isNotEmpty(parentCd)) {
			EzMap so = new EzMap();
			so.setString("menuId", parentCd);
			parent = get(so);
			maxInfo = dao.selectMaxInfo(so);
		} else {
			EzMap so = new EzMap();
			so.setString("menuId", "0000000000");
			maxInfo = dao.selectMaxInfo(so);
		}
		if (maxInfo == null)
			maxInfo = new MenuVo();
		String menuId = maxInfo.getMenuId();
		int seq = maxInfo.getMenuOdrg() + 1;
		if (Utilities.isEmpty(menuId)) {
			if (parent == null)
				menuId = "0000000000";
			else
				menuId = parent.getMenuId();
		}
		if (parent == null) {
			parent = new MenuVo();

		}
		int parentLevel = parent.getMenuLvlNo();
		int offset = parentLevel * 2;
//		int menuSeq = Utilities.parseInt(menuId.substring(offset, offset + 2)) + 1;
		menuId = menuId.substring(0, offset) + Utilities.padLeft(seq+"", 2, '0') + menuId.substring(offset + 2);
		MenuVo menu = new MenuVo();
		menu.setMenuId(menuId);
		menu.setMenuLvlNo(parentLevel + 1);
		menu.setMenuOdrg(seq);

		if (parentLevel == 0)
			menu.setTopMenuId(menuId);
		else {
			menu.setTopMenuId(parent.getTopMenuId());
			menu.setPrntsMenuId(parent.getMenuId());
		}
		menu.setUseYn("Y");
		return menu;
	}

	/**
	 * 사용자 메뉴 가져오기
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<ITreeVo> getUserMenuTree(Object param) throws Exception {
		EzMap itemMap = new EzMap();
		return getUserMenuTree(param, itemMap);

	}

	public List<ITreeVo> getTreeList(EzMap param) throws Exception {
		param.setInt("recordCountPerPage", 100000);
		return AbstractTreeVo.makeHierarchy(dao.selectTreeList(param));
	}

//	public List<EzMap> getGridTreeList(EzMap param) throws Exception {
//		return  Utilities.convertTreeJson(getList(param),"menuId","menuNm","uprMenCId");
//	}
//	
	/**
	 * 사용자 메뉴 가져오기
	 * 
	 * @param param
	 * @return
	 */
	public List<ITreeVo> getUserMenuTree(Object param, EzMap itemMap) throws Exception {
		return getUserMenuTree(param, itemMap, false);

	}

	/**
	 * 사용자 메뉴 가져오기
	 * 
	 * @param param
	 * @return
	 */
	public List<ITreeVo> getUserMenuTree(Object param, EzMap itemMap, boolean force) throws Exception {
		if (itemMap == null)
			itemMap = new EzMap();
		EzMap map = force ? null : SessionUtil.getUserMenuMap();
		List<ITreeVo> tree = force ? null : SessionUtil.getUserMenuList();
		if (tree != null && map != null) {
			itemMap.putAll(map);
			return tree;
		}
		List<ITreeVo> list = Utilities.makeHierarchy(getUserMenuList(param), itemMap);
		List<String> removeList = new ArrayList<String>();
		for (String key : itemMap.keySet()) {
			MenuVo menu = (MenuVo) itemMap.get(key);
			if (!menu.hasLinkedMenu()) {
				removeList.add(menu.getId());
			}
		}
		for (int i = 0; i < removeList.size(); i++) {
			MenuVo menu = (MenuVo) itemMap.remove(removeList.get(i));
			ITreeVo parant = menu.parent();
			if (parant != null)
				parant.getChildren().remove(menu);
		}
		if (!force) {
			SessionUtil.setUserMenuMap(itemMap);
			SessionUtil.setUserMenuList(list);
		}

		return list;
	}

	public List<MenuVo> getUserMenuList(Object param) throws Exception {
		return dao.selectUserMenuList(param);
	}

	public EzMap saveSeq(MenuVo param) throws Exception {
		return Utilities.getUpdateResult(dao.updateSeq(param));

	}

	public EzMap saveSeq(List<MenuVo> list) throws Exception {
		List<EzMap> result = new ArrayList<EzMap>();
		for (int i = 0; i < list.size(); i++) {
			result.add(saveSeq(list.get(i)));
		}
		return Utilities.getSaveResult(result);
	}

	public Object insertWdgt(UserWdgtVo vo) {
		vo.setUserId(Utilities.getLoginId());
		wdgtDao.delete(vo);
		EzMap map = SessionUtil.getUserMenuMap();
		MenuVo menu = (MenuVo) map.get(vo.getMenuId());
		menu.setWdgtYn("Y");
		return Utilities.getUpdateResult(wdgtDao.insert(vo));
	}

	public Object deleteWdgt(UserWdgtVo vo) {
		vo.setUserId(Utilities.getLoginId());
		EzMap map = SessionUtil.getUserMenuMap();
		MenuVo menu = (MenuVo) map.get(vo.getMenuId());
		menu.setWdgtYn("N");
		return Utilities.getDeleteResult(wdgtDao.delete(vo));

	}

	public List<UserWdgtVo> getWdgtList(Object param) {
		return wdgtDao.selectList(param);
	}
}
