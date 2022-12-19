package com.kspo.voc.sys.service;


import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.GrpOrgHstDao;
import com.kspo.voc.sys.dao.GrpOrgRelDao;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class GrpOrgRelService extends AbstractVocService {
	@Autowired
	GrpOrgRelDao dao;
	@Autowired
	GrpOrgHstDao hstDao;
	@Override
	public IVocDao getDao() {
		return dao;
	}
	
	@Override
	public int delete(Object param) throws EgovBizException {
		hstDao.insertDelete(param);
		return super.delete(param);
	}
	@Override
	public int insert(Object param) throws EgovBizException {
		if(get(param)==null)
		{
			hstDao.insert(param);
			return super.insert(param);
		}
		return 0;
	}
}
