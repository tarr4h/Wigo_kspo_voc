package com.egov.voc.sys.service;


import com.egov.voc.base.common.model.AbstractTreeVo;
import com.egov.voc.base.common.model.EzAjaxException;
import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.base.common.model.ITreeVo;
import com.egov.voc.comn.util.SessionUtil;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.dao.CrmGrpMenuRelDao;
import com.egov.voc.sys.dao.CrmMenuBaseDao;
import com.egov.voc.sys.dao.CrmUserWdgtDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmMenuVo;
import com.egov.voc.sys.model.CrmUserWdgtVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("menuService")
public class CrmMenuService extends AbstractCrmService {
	@Autowired
	CrmMenuBaseDao dao;

	@Autowired
	CrmGrpMenuRelDao grpMenuDao;

	@Autowired
	CrmUserWdgtDao wdgtDao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	@Override
	public int delete(Object param) throws Exception {
		int cnt = dao.selectChildrenCount(param);
		if (cnt > 0)
			throw new EzAjaxException("하부메뉴가 존재합니다.");
		grpMenuDao.deleteMenuCd(param);
		wdgtDao.deleteMenuCd(param);
		return super.delete(param);
	}

	public CrmMenuVo getNewMenu(CrmMenuVo param) throws Exception {

		String parentCd = param.getPrntsMenuCd();
		CrmMenuVo parent = null;
		CrmMenuVo maxInfo = null;
		if (Utilities.isNotEmpty(parentCd)) {
			EzMap so = new EzMap();
			so.setString("menuCd", parentCd);
			parent = get(so);
			maxInfo = dao.selectMaxInfo(so);
		} else {
			EzMap so = new EzMap();
			so.setString("menuCd", "0000000000");
			maxInfo = dao.selectMaxInfo(so);
		}
		if (maxInfo == null)
			maxInfo = new CrmMenuVo();
		String menuCd = maxInfo.getMenuCd();
		int seq = maxInfo.getMenuOdrg() + 1;
		if (Utilities.isEmpty(menuCd)) {
			if (parent == null)
				menuCd = "0000000000";
			else
				menuCd = parent.getMenuCd();
		}
		if (parent == null) {
			parent = new CrmMenuVo();

		}
		int parentLevel = parent.getMenuLvlNo();
		int offset = parentLevel * 2;
//		int menuSeq = Utilities.parseInt(menuCd.substring(offset, offset + 2)) + 1;
		menuCd = menuCd.substring(0, offset) + Utilities.padLeft(seq+"", 2, '0') + menuCd.substring(offset + 2);
		CrmMenuVo menu = new CrmMenuVo();
		menu.setMenuCd(menuCd);
		menu.setMenuLvlNo(parentLevel + 1);
		menu.setMenuOdrg(seq);

		if (parentLevel == 0)
			menu.setTopMenuCd(menuCd);
		else {
			menu.setTopMenuCd(parent.getTopMenuCd());
			menu.setPrntsMenuCd(parent.getMenuCd());
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
//		return  Utilities.convertTreeJson(getList(param),"menuCd","menuNm","uprMenCId");
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
			CrmMenuVo menu = (CrmMenuVo) itemMap.get(key);
			if (!menu.hasLinkedMenu()) {
				removeList.add(menu.getId());
			}
		}
		for (int i = 0; i < removeList.size(); i++) {
			CrmMenuVo menu = (CrmMenuVo) itemMap.remove(removeList.get(i));
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

	public List<CrmMenuVo> getUserMenuList(Object param) throws Exception {
		return dao.selectUserMenuList(param);
	}

	public EzMap saveSeq(CrmMenuVo param) throws Exception {
		return Utilities.getUpdateResult(dao.updateSeq(param));

	}

	public EzMap saveSeq(List<CrmMenuVo> list) throws Exception {
		List<EzMap> result = new ArrayList<EzMap>();
		for (int i = 0; i < list.size(); i++) {
			result.add(saveSeq(list.get(i)));
		}
		return Utilities.getSaveResult(result);
	}

	public Object insertWdgt(CrmUserWdgtVo vo) {
		vo.setUserCd(Utilities.getLoginUserCd());
		wdgtDao.delete(vo);
		EzMap map = SessionUtil.getUserMenuMap();
		CrmMenuVo menu = (CrmMenuVo) map.get(vo.getMenuCd());
		menu.setWdgtYn("Y");
		return Utilities.getUpdateResult(wdgtDao.insert(vo));
	}

	public Object deleteWdgt(CrmUserWdgtVo vo) {
		vo.setUserCd(Utilities.getLoginUserCd());
		EzMap map = SessionUtil.getUserMenuMap();
		CrmMenuVo menu = (CrmMenuVo) map.get(vo.getMenuCd());
		menu.setWdgtYn("N");
		return Utilities.getDeleteResult(wdgtDao.delete(vo));

	}

	public List<CrmUserWdgtVo> getWdgtList(Object param) {
		return wdgtDao.selectList(param);
	}
}
