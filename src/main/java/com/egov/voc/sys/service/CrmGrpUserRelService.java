package com.egov.voc.sys.service;


import com.egov.base.common.model.EzMap;
import com.egov.voc.sys.dao.CrmGrpUserRelDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmGrpUserRelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
