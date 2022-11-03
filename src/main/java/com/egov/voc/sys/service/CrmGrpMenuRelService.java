package com.egov.voc.sys.service;


import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.sys.dao.CrmGrpMenuHstDao;
import com.egov.voc.sys.dao.CrmGrpMenuRelDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmGrpMenuRelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Object saveMenuGroup(String menuCd, List<CrmGrpMenuRelVo> list) throws Exception {
		EzMap param = new EzMap();
		param.setString("menuCd", menuCd);
		dao.deleteMenuCd(param);
		return insertList(list);
	}
	public Object saveGroupMenu(String grpCd, List<CrmGrpMenuRelVo> list) throws Exception {
		EzMap param = new EzMap();
		param.setString("grpCd", grpCd);
		dao.deleteGrpCd(param);
		return insertList(list);
	}
	
}
