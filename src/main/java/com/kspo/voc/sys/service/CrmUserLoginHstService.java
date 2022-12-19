package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.CrmUserLoginHstDao;
import com.kspo.voc.sys.dao.ICrmDao;

@Service
public class CrmUserLoginHstService extends AbstractCrmService {
	@Autowired
	CrmUserLoginHstDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}
	
	
}
