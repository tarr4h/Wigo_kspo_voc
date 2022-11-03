package com.egov.voc.sys.service;


import com.egov.voc.sys.dao.CrmUserLoginHstDao;
import com.egov.voc.sys.dao.ICrmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmUserLoginHstService extends AbstractCrmService {
	@Autowired
	CrmUserLoginHstDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}
	
	
}
