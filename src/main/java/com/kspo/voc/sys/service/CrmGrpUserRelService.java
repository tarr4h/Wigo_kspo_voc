package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.CrmGrpUserRelDao;
import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.model.CrmGrpUserRelVo;

import java.util.List;

@Service
public class CrmGrpUserRelService extends AbstractCrmService {
	@Autowired
	CrmGrpUserRelDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	public Object saveUserGroup(String userCd, List<CrmGrpUserRelVo> list) throws Exception {
		EzMap param = new EzMap();
		param.setString("userCd", userCd);
		dao.deleteUserCd(param);
		return insertList(list);
	}
	public Object saveGroupUser(String grpCd, List<CrmGrpUserRelVo> list) throws Exception{
		EzMap param = new EzMap();
		param.setString("grpCd", grpCd);
		dao.deleteGrpCd(param);
		return insertList(list);
	}
	
}
