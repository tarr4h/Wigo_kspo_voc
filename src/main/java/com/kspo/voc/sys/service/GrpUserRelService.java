package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.GrpUserRelDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.model.GrpUserRelVo;

import java.util.List;

@Service
public class GrpUserRelService extends AbstractVocService {
	@Autowired
	GrpUserRelDao dao;

	@Override
	public IVocDao getDao() {
		return dao;
	}

	public Object saveUserGroup(String userId, List<GrpUserRelVo> list) throws Exception {
		EzMap param = new EzMap();
		param.setString("userId", userId);
		dao.deleteUserId(param);
		return insertList(list);
	}
	public Object saveGroupUser(String grpId, List<GrpUserRelVo> list) throws Exception{
		EzMap param = new EzMap();
		param.setString("grpId", grpId);
		dao.deleteGrpId(param);
		return insertList(list);
	}
	
}
