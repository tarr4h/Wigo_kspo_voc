package com.egov.voc.sys.service;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.sys.dao.*;
import com.egov.voc.sys.model.CrmGrpBaseVo;
import com.egov.voc.sys.model.CrmGrpMenuRelVo;
import com.egov.voc.sys.model.CrmGrpUserRelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmGrpBaseService extends AbstractCrmService {
	@Autowired
	CrmGrpBaseDao dao;
	@Autowired
	CrmGrpUserRelDao grpUserDao;
	@Autowired
	CrmGrpMenuRelDao grpMenuDao;
	@Autowired
	CrmGrpOrgRelDao grpOrgDao;
	@Autowired
	CrmGrpEmpRelDao grpEmpDao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	public List<CrmGrpUserRelVo> getGroupUserList(Object param) throws Exception {
		return dao.selectGroupUserList(param);
	}

	public int getGroupUserListCount(Object param) throws Exception {
		return dao.selectGroupUserListCount(param);
	}

	public List<CrmGrpMenuRelVo> getGroupMenuList(Object param) throws Exception {
		return dao.selectGroupMenuList(param);
	}

	public int getGroupMenuListCount(Object param) throws Exception {
		return dao.selectGroupMenuListCount(param);
	}

	public List<CrmGrpBaseVo> getGroupCheckList(EzMap param) throws Exception {
		return dao.selectGroupCheckList(param);
	}

	@Override
	public int delete(Object param) throws Exception {
		grpUserDao.deleteGrpCd(param);
		grpMenuDao.deleteGrpCd(param);
		grpOrgDao.deleteGrpCd(param);
		grpEmpDao.deleteGrpCd(param);
		return super.delete(param);
	}

	public List<CrmGrpMenuRelVo> getGroupOrgList(EzMap param) {
		return grpOrgDao.selectList(param);
	}

	public int getGroupOrgListCount(Object param) throws Exception {
		return grpOrgDao.selectListCount(param);
	}

	public List<CrmGrpMenuRelVo> getGroupEmpList(EzMap param) {
		return grpEmpDao.selectList(param);
	}

	public int getGroupEmpListCount(Object param) throws Exception {
		return grpEmpDao.selectListCount(param);
	}
}
