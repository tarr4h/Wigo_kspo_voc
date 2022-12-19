package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.GrpMenuHstDao;
import com.kspo.voc.sys.dao.GrpMenuRelDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.model.GrpMenuRelVo;

import java.util.List;

@Service
public class GrpMenuRelService extends AbstractVocService {
	@Autowired
	GrpMenuRelDao dao;
	
	@Autowired
	GrpMenuHstDao hstDao;
	@Override
	public IVocDao getDao() {
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
	public Object saveMenuGroup(String menuId, List<GrpMenuRelVo> list) throws Exception {
		EzMap param = new EzMap();
		param.setString("menuId", menuId);
		dao.deleteMenuId(param);
		return insertList(list);
	}
	public Object saveGroupMenu(String grpId, List<GrpMenuRelVo> list) throws Exception {
		EzMap param = new EzMap();
		param.setString("grpId", grpId);
		dao.deleteGrpId(param);
		return insertList(list);
	}
	
}
