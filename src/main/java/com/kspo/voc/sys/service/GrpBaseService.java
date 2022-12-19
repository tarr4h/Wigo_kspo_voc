package com.kspo.voc.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.*;
import com.kspo.voc.sys.model.GrpBaseVo;
import com.kspo.voc.sys.model.GrpMenuRelVo;
import com.kspo.voc.sys.model.GrpUserRelVo;

import java.util.List;

@Service
public class GrpBaseService extends AbstractVocService {
	@Autowired
	GrpBaseDao dao;
	@Autowired
	GrpUserRelDao grpUserDao;
	@Autowired
	GrpMenuRelDao grpMenuDao;
	@Autowired
	GrpOrgRelDao grpOrgDao;
	@Autowired
	GrpEmpRelDao grpEmpDao;

	@Override
	public IVocDao getDao() {
		return dao;
	}

	public List<GrpUserRelVo> getGroupUserList(Object param) throws Exception {
		return dao.selectGroupUserList(param);
	}

	public int getGroupUserListCount(Object param) throws Exception {
		return dao.selectGroupUserListCount(param);
	}

	public List<GrpMenuRelVo> getGroupMenuList(Object param) throws Exception {
		return dao.selectGroupMenuList(param);
	}

	public int getGroupMenuListCount(Object param) throws Exception {
		return dao.selectGroupMenuListCount(param);
	}

	public List<GrpBaseVo> getGroupCheckList(EzMap param) throws Exception {
		return dao.selectGroupCheckList(param);
	}

	@Override
	public int delete(Object param) throws Exception {
		grpUserDao.deleteGrpId(param);
		grpMenuDao.deleteGrpId(param);
		grpOrgDao.deleteGrpId(param);
		grpEmpDao.deleteGrpId(param);
		return super.delete(param);
	}

	public List<GrpMenuRelVo> getGroupOrgList(EzMap param) {
		return grpOrgDao.selectList(param);
	}

	public int getGroupOrgListCount(Object param) throws Exception {
		return grpOrgDao.selectListCount(param);
	}

	public List<GrpMenuRelVo> getGroupEmpList(EzMap param) {
		return grpEmpDao.selectList(param);
	}

	public int getGroupEmpListCount(Object param) throws Exception {
		return grpEmpDao.selectListCount(param);
	}
}
