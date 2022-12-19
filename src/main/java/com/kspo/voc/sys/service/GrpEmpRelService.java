package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.GrpEmpHstDao;
import com.kspo.voc.sys.dao.GrpEmpRelDao;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class GrpEmpRelService extends AbstractVocService {
	@Autowired
	GrpEmpRelDao dao;
	@Autowired
	GrpEmpHstDao hstDao;

	@Override
	public IVocDao getDao() {
		return dao;
	}

	@Override
	public int delete(Object param) throws Exception {
		hstDao.insertDelete(param);
		return super.delete(param);
	}

	@Override
	public int insert(Object param) throws Exception {
		if (get(param) == null) {
			hstDao.insert(param);
			return super.insert(param);
		}
		return 0;
	}
}
