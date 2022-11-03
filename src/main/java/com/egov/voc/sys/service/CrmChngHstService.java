package com.egov.voc.sys.service;


import com.egov.voc.sys.dao.CrmChngHstDao;
import com.egov.voc.sys.dao.ICrmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmChngHstService extends AbstractCrmService {
	@Autowired
	CrmChngHstDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}
}
