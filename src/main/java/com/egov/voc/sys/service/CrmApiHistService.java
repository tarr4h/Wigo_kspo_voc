package com.egov.voc.sys.service;

import com.egov.voc.sys.dao.CrmApiExecHstDao;
import com.egov.voc.sys.dao.ICrmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmApiHistService extends AbstractCrmService {
	@Autowired
	CrmApiExecHstDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	
}
