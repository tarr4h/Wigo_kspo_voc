package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.CrmChngHstDao;
import com.kspo.voc.sys.dao.ICrmDao;

@Service
public class CrmChngHstService extends AbstractCrmService {
	@Autowired
	CrmChngHstDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}
}
