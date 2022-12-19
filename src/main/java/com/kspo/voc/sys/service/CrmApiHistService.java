package com.kspo.voc.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.CrmApiExecHstDao;
import com.kspo.voc.sys.dao.ICrmDao;

@Service
public class CrmApiHistService extends AbstractCrmService {
	@Autowired
	CrmApiExecHstDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	
}
