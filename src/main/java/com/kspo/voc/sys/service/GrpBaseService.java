package com.kspo.voc.sys.service;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.GrpBaseDao;
import com.kspo.voc.sys.dao.GrpEmpRelDao;
import com.kspo.voc.sys.dao.GrpMenuRelDao;
import com.kspo.voc.sys.dao.GrpOrgRelDao;
import com.kspo.voc.sys.dao.GrpUserRelDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.model.GrpBaseVo;
import com.kspo.voc.sys.model.GrpMenuRelVo;
import com.kspo.voc.sys.model.GrpUserRelVo;

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

	public List<GrpUserRelVo> getGroupUserList(Object param) throws EgovBizException {
		return dao.selectGroupUserList(param);
	}

	public int getGroupUserListCount(Object param) throws EgovBizException {
		return dao.selectGroupUserListCount(param);
	}

	public List<GrpMenuRelVo> getGroupMenuList(Object param) throws EgovBizException {
		return dao.selectGroupMenuList(param);
	}

	public int getGroupMenuListCount(Object param) throws EgovBizException {
		return dao.selectGroupMenuListCount(param);
	}

	public List<GrpBaseVo> getGroupCheckList(EzMap param) throws EgovBizException {
		return dao.selectGroupCheckList(param);
	}

	@Override
	public int delete(Object param) throws EgovBizException {
		grpUserDao.deleteGrpId(param);
		grpMenuDao.deleteGrpId(param);
		grpOrgDao.deleteGrpId(param);
		grpEmpDao.deleteGrpId(param);
		return super.delete(param);
	}

	public List<GrpMenuRelVo> getGroupOrgList(EzMap param) {
		return grpOrgDao.selectList(param);
	}

	public int getGroupOrgListCount(Object param) throws EgovBizException {
		return grpOrgDao.selectListCount(param);
	}

	public List<GrpMenuRelVo> getGroupEmpList(EzMap param) {
		return grpEmpDao.selectList(param);
	}

	public int getGroupEmpListCount(Object param) throws EgovBizException {
		return grpEmpDao.selectListCount(param);
	}

	public EzMap getGroupIpInfo(Object param) throws EgovBizException {
		return dao.selectGroupIpInfo(param);

	}
}
