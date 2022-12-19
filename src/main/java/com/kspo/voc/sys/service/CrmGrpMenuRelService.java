package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.CrmGrpMenuHstDao;
import com.kspo.voc.sys.dao.CrmGrpMenuRelDao;
import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.model.CrmGrpMenuRelVo;

import java.util.List;

@Service
public class CrmGrpMenuRelService extends AbstractCrmService {
	@Autowired
	CrmGrpMenuRelDao dao;
	
	@Autowired
	CrmGrpMenuHstDao hstDao;
	@Override
	public ICrmDao getDao() {
		return dao;
	}
	@Override
	public int insert(Object param) throws Exception {
		hstDao.insert(param);
		return super.insert(param);
	}
	@Override
	public int delete(Object param) throws Exception {
		hstDao.insertDelete(param);
		return super.delete(param);
	}
	public Object saveMenuGroup(String menuId, List<CrmGrpMenuRelVo> list) throws Exception {
		EzMap param = new EzMap();
		param.setString("menuId", menuId);
		dao.deleteMenuId(param);
		return insertList(list);
	}
	public Object saveGroupMenu(String grpId, List<CrmGrpMenuRelVo> list) throws Exception {
		EzMap param = new EzMap();
		param.setString("grpId", grpId);
		dao.deleteGrpId(param);
		return insertList(list);
	}
	
}
